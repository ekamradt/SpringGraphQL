package com.pull.law.pullers.uakronlibguides;

import com.pull.law.misc.LineInfo;
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
    protected LineInfo lineInfo;
    protected int titleIndex = 0;
    protected String title;
    protected String currentSubtitle;
    private List<LineInfo> lineInfos = new ArrayList<>();

    public abstract List<LineInfo> call();

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

    public List<LineInfo> readThis(final String urlString) {
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
            return lineInfos;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void writeLineInfos() {
        try {
            final String content = lineInfos.stream()
                    .map(LineInfo::toCsv)
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

    protected void outputLineInfo(final LineInfo inLineInfo) {
        if (Strings.isEmpty(inLineInfo.getName()) || Strings.isEmpty(inLineInfo.getValue())) {
            return;
        }
        lineInfos.add(lineInfo);
        final LineInfo lineInfo = parseNoteFromValue(inLineInfo);
        System.out.println(String.format("'%s' : '%s' : '%s' : '%s' : '%s'",
                lineInfo.getTitle(), lineInfo.getSubtitle(), lineInfo.getName(), lineInfo.getValue(),
                lineInfo.getNote()));
    }

    protected LineInfo parseNoteFromValue(final LineInfo inLineInfo) {
        final LineInfo lineInfo = LineInfo.copy(inLineInfo);
        final String value = inLineInfo.getValue();
        final int iStart = value.indexOf("(");
        if (iStart >= 0) {
            final int iEnd = value.indexOf(")", iStart);
            if (iEnd >= 0) {
                final String tempValue = value.substring(0, iStart);
                final String tempNote = value.substring(iStart, iEnd);
                lineInfo.setValue(tempValue)
                        .setNote(tempNote);
            }
        }
        return lineInfo;
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
                lineInfo = new LineInfo();
                lineInfo.setTitle(title);
                lineInfo.setSubtitle(currentSubtitle);
                tdTagIndex++;
            case 1:
                final String name = parseTd(line);
                if (!ignoreNameOrValue(name)) {
                    lineInfo.appendName(name);
                }
                if (line.contains(TD_END)) {
                    tdTagIndex++;
                }
                break;
            case 2:
                final String value = parseTd(line);
                if (!ignoreNameOrValue(value)) {
                    lineInfo.appendValue(value);
                }
                if (line.contains(TD_END)) {
                    outputLineInfo(lineInfo);
                    lineInfo = null;
                    tdTagIndex = 0;
                }
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("tdTagIndex out of bounds '%s'", tdTagIndex));
        }
    }
}
