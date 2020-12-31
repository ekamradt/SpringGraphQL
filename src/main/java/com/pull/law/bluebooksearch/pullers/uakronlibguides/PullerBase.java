package com.pull.law.bluebooksearch.pullers.uakronlibguides;

import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import org.apache.logging.log4j.util.Strings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class PullerBase {

    public static final String FILENAME = "/home/ekamradt/git/SpringGraphQL/output.txt";

    public static String TR_START = "<tr>";
    public static String TR_END = "</tr>";
    public static String TD_START1 = "<td>";
    public static String TD_START2 = "<td ";
    public static String TD_END = "</td>";
    public static String BODY_START = "<tbody>";
    public static String BODY_END = "</tbody>";

    protected Set<String> ignoreNameValues = new HashSet<>();
    protected List<String> subtitleList = new ArrayList<>();

    protected boolean turnOnImport = false;
    protected int tdTagIndex = 0;
    protected boolean trTag = false;
    protected BlueSearchRecord blueSearchRecord;
    protected int titleIndex = 0;
    protected String title;
    protected String currentSubtitle;
    private List<BlueSearchRecord> blueSearchRecords = new ArrayList<>();

    public abstract List<BlueSearchRecord> call();

    public void addIgnoreNameValue(final String nameValue) {
        ignoreNameValues.add(nameValue);
    }

    public void addSubtitle(final String subtitle) {
        subtitleList.add(subtitle);
    }

    protected boolean ignoreNameOrValue(final String phrase) {
        return ignoreNameValues.contains(phrase);
    }

    protected void readWriteInit() {
        currentSubtitle = subtitleList.get(titleIndex);
    }

    public List<BlueSearchRecord> readThis(final String urlString) {
        final StringBuilder sb = new StringBuilder();
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
            final String content = blueSearchRecords.stream()
                    .map(BlueSearchRecord::toCsv)
                    .collect(Collectors.joining("\n"));
            final Path path = Path.of(FILENAME);
            Files.writeString(path, content,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void out(final String msg) {
        //System.out.println(msg);
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
            tempLine = java.net.URLDecoder.decode(tempLine, StandardCharsets.UTF_8.name());
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
        System.out.println(String.format("'%s' : '%s' : '%s' : '%s' : '%s'",
                blueSearchRecord.getTitle(), blueSearchRecord.getSubtitle(), blueSearchRecord.getName(), blueSearchRecord.getValue(),
                blueSearchRecord.getNote()));
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

        if (!turnOnImport) {
            handleBodyStart(line);
            return true;
        }

        if (line.contains(BODY_END)) {
            titleIndex++;
            if (titleIndex >= subtitleList.size()) {
                return false;
            }
            currentSubtitle = subtitleList.get(titleIndex);
            turnOnImport = false;
            return true;
        }

        if (!trTag) {
            if (line.startsWith(TR_START)) {
                trTag = true;
                return true;
            }
        }

        if (line.startsWith(TR_END)) {
            trTag = false;
            return true;
        }

        extractDataFromLine(line);
        return true;
    }

    private void handleBodyStart(String line) {
        if (line.contains(BODY_START)) {
            turnOnImport = true;
        }
    }

    private void extractDataFromLine(String line) {
        switch (tdTagIndex) {
            case 0:
                if (!line.contains(TD_START1) && !line.contains(TD_START2)) {
                    return;
                }
                blueSearchRecord = new BlueSearchRecord();
                blueSearchRecord.setTitle(title);
                blueSearchRecord.setSubtitle(currentSubtitle);
                tdTagIndex++;
            case 1:
                final String name = parseTd(line);
                if (!ignoreNameOrValue(name)) {
                    blueSearchRecord.appendName(name);
                }
                if (line.contains(TD_END)) {
                    tdTagIndex++;
                }
                break;
            case 2:
                final String value = parseTd(line);
                if (!ignoreNameOrValue(value)) {
                    blueSearchRecord.appendValue(value);
                }
                if (line.contains(TD_END)) {
                    outputLineInfo(blueSearchRecord);
                    blueSearchRecord = null;
                    tdTagIndex = 0;
                }
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("tdTagIndex out of bounds '%s'", tdTagIndex));
        }
    }
}
