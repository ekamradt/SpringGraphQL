package com.pull.law.bluebooksearch.pullers.lawresourceorg;

import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.util.Strings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public abstract class PullerBase2 {

    public static final String OUTPUT_FILE = "BLUEBOOK_ABBR.txt";
    public static String TD = "td";

    protected List<String> subtitleList = new ArrayList<>();
    protected List<PullGroup> pullGroups = new ArrayList<>();
    protected boolean processGroup = false;
    protected int groupIndex = 0;
    protected String title;
    protected String subtitle;
    protected BlueSearchRecord blueSearchRecord = new BlueSearchRecord();
    private final List<BlueSearchRecord> blueSearchRecords = new ArrayList<>();
    private PullGroup pullGroup;

    public abstract List<BlueSearchRecord> call();

    public List<BlueSearchRecord> readThis(final String urlString) {
        final StringBuilder sb = new StringBuilder();
        pullGroup = pullGroups.get(groupIndex);
        readWriteInit();
        try {
            final URL oracle = new URL(urlString);
            final BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!this.processLine(inputLine)) {
                    break;
                }
            }
            in.close();
            writeLineInfos();
            return blueSearchRecords;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void writeLineInfos() {
        try {
            final String content = BlueSearchRecord.headerPipe() + "\n" +
                    blueSearchRecords.stream()
                            .map(BlueSearchRecord::toPipe)
                            .filter(Objects::nonNull)
                            .collect(Collectors.joining("\n"));
            final Path path = Path.of(OUTPUT_FILE);
            Files.writeString(path, content,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void addSubtitle(final String subtitle) {
        subtitleList.add(subtitle);
    }

    protected void readWriteInit() {
        subtitle = subtitleList.get(groupIndex);
    }

    protected String parseTd(final String line) {
        String tempLine = line.trim();
        while (tempLine.startsWith("<")) {
            tempLine = stripStartAndEndTag(tempLine);
        }
        tempLine = cleanString(tempLine);
        return tempLine;
    }

    protected String cleanString(String tempLine) {
        try {
            tempLine = tempLine.replace("&nbsp;", " ");
            if (tempLine.contains("<")) {
                tempLine = stripTagsByPosition(tempLine);
            }
            tempLine = URLDecoder.decode(tempLine, StandardCharsets.UTF_8.name());
            tempLine = StringEscapeUtils.unescapeHtml4(tempLine);
            tempLine = tempLine.replaceAll("\\s+", " ");
            return tempLine.trim();
        } catch (Exception e) {
            final String msg = "ERROR: '" + tempLine + "'";
            throw new IllegalArgumentException(msg, e);
        }
    }

    protected String stripTagsByPosition(final String line) {
        String tempLine = line;
        int iStart = tempLine.indexOf('<');
        while (iStart >= 0) {
            int iEnd = tempLine.indexOf(">", iStart);
            tempLine = tempLine.substring(0, iStart) + tempLine.substring(iEnd + 1);
            iStart = tempLine.indexOf('<');
        }
        return tempLine;
    }

    protected String stripStartAndEndTag(final String line) {
        String tempLine = line.substring(1);
        String[] split = tempLine.split(">");
        String tag = split[0];
        if (tag.contains(" ")) {
            split = tag.split(" ");
            tag = split[0];
        }
        final String tagBeg = String.format("<%s.*?>", tag);
        final String tagEnd = String.format("</%s>", tag);
        tempLine = line.replaceAll(tagBeg, "");
        tempLine = tempLine.replace(tagEnd, "");
        return tempLine.trim();
    }

    protected void outputLineInfo(final BlueSearchRecord inBlueSearchRecord) {
        if (Strings.isEmpty(inBlueSearchRecord.getName()) || Strings.isEmpty(inBlueSearchRecord.getValue())) {
            return;
        }
        blueSearchRecords.add(this.blueSearchRecord);
        final BlueSearchRecord blueSearchRecord = parseNoteFromValue(inBlueSearchRecord);
        System.out.printf("'%s' : '%s' : '%s' : '%s' : '%s'%n",
                blueSearchRecord.getTitle(), blueSearchRecord.getSubtitle(), blueSearchRecord.getName(), blueSearchRecord.getValue(),
                blueSearchRecord.getNote());
    }

    protected BlueSearchRecord parseNoteFromValue(final BlueSearchRecord inBlueSearchRecord) {
        final BlueSearchRecord blueSearchRecord = BlueSearchRecord.copy(inBlueSearchRecord);
        final String value = inBlueSearchRecord.getValue();
        final int iStart = value.indexOf("(");
        if (iStart >= 0) {
            final int iEnd = value.indexOf(")", iStart);
            if (iEnd >= 0) {
                final String tempValue = value.substring(0, iStart);
                final String tempNote = value.substring(iStart, iEnd);
                blueSearchRecord.setValue(tempValue)
                        .setNote(tempNote);
            }
        }
        return blueSearchRecord;
    }

    protected boolean processLine(final String line) {

        if (!processGroup) {
            if (!line.contains(pullGroup.getBegGroup())) {
                return true;
            }
            processGroup = true;
        } else {
            if (line.contains(pullGroup.getEndGroup())) {
                groupIndex++;
                pullGroup = pullGroups.get(groupIndex);
                processGroup = false;
                // The end of one section and the start of another might be the same line.
                if (!line.contains(pullGroup.getBegGroup())) {
                    return true;
                }
                processGroup = true;
            }
        }
        extractDataFromLine(line);
        return true;
    }

    private void extractDataFromLine(String line) {

        if (extractedTitles(line)) {
            return;
        }

        if (line.contains(pullGroup.getNameValueGroup())) {
            final ProcessType processType = pullGroup.getNameValueProcessType();
            switch (processType) {
                case TD_TWO:
                    fillLineInfoFromTdTwo(line);
                    break;
                case TD_THREE:
                    final String temp = line.replace("<td", "");
                    int count = (line.length() - temp.length()) / 3;
                    if (count == 3) {
                        fillLineInfoFromTdThree(line);
                    } else if (count == 2) {
                        fillLineInfoFromTdTwo(line);
                    }
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Not Implemented '%s'", processType));
            }
        }
    }

    private boolean extractedTitles(final String line) {
        if (lineContains(line, pullGroup.getTitleGroup())) {
            extractTitle(line);
            return true;
        }

        if (lineContains(line, pullGroup.getSubtitleGroup())) {
            extractSubtitle(line);
            return true;
        }

        if (lineContains(line, pullGroup.getSubsubtitleGroup())) {
            extractSubsubtitle(line);
            return true;
        }
        return false;
    }

    private void extractSubsubtitle(final String line) {
        blueSearchRecord = BlueSearchRecord.copyTitles(blueSearchRecord);
        String subsubtitle = stripStartAndEndTag(line);
        subsubtitle = cleanString(subsubtitle);
        blueSearchRecord.setSubsubtitle(subsubtitle);
    }

    private void extractSubtitle(final String line) {
        blueSearchRecord = BlueSearchRecord.copyTitles(blueSearchRecord);
        String subtitle = stripStartAndEndTag(line);
        subtitle = cleanString(subtitle);
        final String removeFromSubtitle = pullGroup.getRemoveFromSubtitle();
        if (removeFromSubtitle != null) {
            subtitle = subtitle.replaceAll(removeFromSubtitle, "");
        }
        subtitle = subtitle.replace(pullGroup.getRemoveIfExists(), "");
        subtitle = subtitle.trim();
        blueSearchRecord.setSubtitle(subtitle);
    }

    private void extractTitle(final String line) {
        blueSearchRecord = BlueSearchRecord.copyTitles(blueSearchRecord);
        title = stripStartAndEndTag(line);
        title = cleanString(title);
        final String removeFromTitle = pullGroup.getRemoveFromTitle();
        if (removeFromTitle != null) {
            title = title.replace(removeFromTitle, "");
        }
        title = title.replace(pullGroup.getRemoveIfExists(), "");
        title = title.trim();
        blueSearchRecord.setTitle(title);
    }

    private boolean lineContains(final String line, final String value) {
        return (value != null && line.contains(value));
    }

    private void fillLineInfoFromTdThree(final String line) {
        final LineParts lineParts = processThreeTd(line);
        blueSearchRecord.setName(lineParts.getValueFirst());
        blueSearchRecord.setDates(lineParts.getValueSecond());
        blueSearchRecord.setValue(lineParts.getValueThird());
        blueSearchRecords.add(blueSearchRecord);
        blueSearchRecord = BlueSearchRecord.copyTitles(blueSearchRecord);
    }

    private void fillLineInfoFromTdTwo(final String line) {
        final LineParts lineParts = processTwoTd(line);
        blueSearchRecord.setName(lineParts.getValueFirst());
        final String valueSecond = lineParts.getValueSecond();
        blueSearchRecord.setValue(valueSecond);
        blueSearchRecords.add(blueSearchRecord);
        blueSearchRecord = BlueSearchRecord.copyTitles(blueSearchRecord);
        // lineInfo = new LineInfo();
    }

    private LineParts processThreeTd(final String line) {
        final LineParts lineParts = new LineParts();
        lineParts.setLine(line);
        stripTagsStoreValue(TD, lineParts);
        stripTagsStoreValue(TD, lineParts);
        stripTagsStoreValue(TD, lineParts);
        return lineParts;
    }

    private LineParts processTwoTd(final String line) {
        final LineParts lineParts = new LineParts();
        lineParts.setLine(line);
        stripTagsStoreValue(TD, lineParts);
        stripTagsStoreValue(TD, lineParts);
        return lineParts;
    }

    private void stripTagsStoreValue(final String tag, final LineParts lineParts) {
        stripTagBeg(tag, lineParts);
        stripTagEndStoreValue(tag, lineParts);
    }

    private void stripTagBeg(final String tag, final LineParts lineParts) {
        final String tagRegex = String.format("<%s.*?>", tag);
        String temp = lineParts.getLine().replaceAll(tagRegex, "");
        lineParts.setLine(temp);
    }

    private void stripTagEndStoreValue(final String tag, final LineParts lineParts) {
        final String endTag = String.format("</%s>", tag);
        final String line = lineParts.getLine();
        final int pos = line.indexOf(endTag);
        String temp = line.substring(0, pos);
        temp = cleanString(temp);
        lineParts.addValue(temp);
        lineParts.setLine(line.substring(pos + endTag.length()));
    }
}
