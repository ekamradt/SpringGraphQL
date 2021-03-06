package com.pull.law.bluebook.pullers.lawresourceorg;

import com.pull.law.bluebook.misc.BlueSearchRecord;
import org.apache.commons.text.StringEscapeUtils;
import org.assertj.core.util.Strings;

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
            final String lines = blueSearchRecords.stream()
                    .map(BlueSearchRecord::toPipe)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("\n"));
            final String content = BlueSearchRecord.headerPipe() + "\n" + lines;
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

    protected String cleanString(final String line) {
        String tempLine = line;
        try {
            tempLine = new String(tempLine.getBytes(StandardCharsets.US_ASCII), StandardCharsets.UTF_8).trim();
            tempLine = tempLine.replace("&nbsp;", " ");
            if (tempLine.contains("<")) {
                tempLine = stripTagsByPosition(tempLine);
            }
            tempLine = URLDecoder.decode(tempLine, StandardCharsets.UTF_8.name());
            tempLine = StringEscapeUtils.unescapeHtml4(tempLine);
            tempLine = tempLine.replaceAll("￢", "-");
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
                blueSearchRecord.reset();
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

    private void setManualTitleIfExist() {
        if (pullGroup.getTitle() != null) {
            blueSearchRecord.setCatLevel1(pullGroup.getTitle());
        }
    }

    private void setManualSubtitleIfExist() {
        if (pullGroup.getSubtitle() != null) {
            blueSearchRecord.setCatLevel2(pullGroup.getSubtitle());
        }
    }

    private boolean extractedTitles(final String line) {
        if (Strings.isNullOrEmpty(pullGroup.getTitleGroup())) {
            setManualTitleIfExist();
        } else {
            if (lineContains(line, pullGroup.getTitleGroup())) {
                extractTitle(line);
                return true;
            }
        }

        if (Strings.isNullOrEmpty(pullGroup.getSubtitleGroup())) {
            setManualSubtitleIfExist();
        } else {
            if (lineContains(line, pullGroup.getSubtitleGroup())) {
                extractSubtitle(line);
                if (pullGroup.getSubtitle() != null) {
                    final String subtitle = pullGroup.getSubtitle();
                    if (blueSearchRecord.getCatLevel2().startsWith(subtitle)) {
                        blueSearchRecord.setCatLevel2(subtitle);
                    }
                }
                return true;
            }
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
        blueSearchRecord.setCatLevel3(subsubtitle);
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
        blueSearchRecord.setCatLevel2(subtitle);
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
        blueSearchRecord.setCatLevel1(title);
    }

    private boolean lineContains(final String line, final String value) {
        return (value != null && line.contains(value));
    }

    private void fillLineInfoFromTdThree(final String line) {
        final LineParts lineParts = processThreeTd(line);
        final String value = lineParts.getValueThird();
        if (!Strings.isNullOrEmpty(value)) {
            blueSearchRecord.setValue(value);
            blueSearchRecord.setName(lineParts.getValueFirst());
            //setDates(blueSearchRecord, lineParts.getValueSecond());
            blueSearchRecords.add(blueSearchRecord);
            blueSearchRecord = BlueSearchRecord.copyTitles(blueSearchRecord);
        }
    }

    private boolean isDate(final String value) {
        try {
            final int maybeYear = Integer.parseInt(value);
            return (maybeYear > 1550 && maybeYear < 2050);
        } catch (Exception e) {
            return false;
        }
    }

    private void fillLineInfoFromTdTwo(final String line) {
        final LineParts lineParts = processTwoTd(line);
        final String valueSecond = lineParts.getValueSecond();
        if (!Strings.isNullOrEmpty(valueSecond)) {
            blueSearchRecord.setValue(valueSecond);
            blueSearchRecord.setName(lineParts.getValueFirst());
            blueSearchRecords.add(blueSearchRecord);
            blueSearchRecord = BlueSearchRecord.copyTitles(blueSearchRecord);
        }
    }

    private LineParts processThreeTd(final String line) {
        final LineParts lineParts = new LineParts();
        lineParts.setLine(line);
        stripTagsStoreValue(TD, lineParts);
        stripTagsStoreValue(TD, lineParts, true);
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
        stripTagsStoreValue(tag, lineParts, false);
    }

    private void stripTagsStoreValue(final String tag, final LineParts lineParts, final boolean brToNewline) {
        stripTagBeg(tag, lineParts);
        stripTagEndStoreValue(tag, lineParts, brToNewline);
    }

    private void stripTagBeg(final String tag, final LineParts lineParts) {
        final String tagRegex = String.format("<%s.*?>", tag);
        String temp = lineParts.getLine().replaceAll(tagRegex, "");
        lineParts.setLine(temp);
    }

    private void stripTagEndStoreValue(final String tag, final LineParts lineParts, final boolean brToNewline) {
        final String endTag = String.format("</%s>", tag);
        final String line = lineParts.getLine();
        final int pos = line.indexOf(endTag);
        String temp = line.substring(0, pos);
        if (brToNewline) {
            temp = temp.replace("</br>", "|");
            temp = temp.replace("<br/>", "|");
            temp = temp.replace("<br />", "|");
        }
        temp = cleanString(temp);
        lineParts.addValue(temp);
        lineParts.setLine(line.substring(pos + endTag.length()));
    }
}
