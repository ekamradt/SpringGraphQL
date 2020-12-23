package com.pull.law.pullers;

import com.pull.law.misc.LineInfo;
import com.pull.law.misc.ScanResult;
import org.apache.logging.log4j.util.Strings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class PullerBase {

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

    abstract public ScanResult call();

    public void addIgnoreNameValue(final String nameValue) {
        ignoreNameValues.add(nameValue);
    }

    public void addSubtitle(final String subtitle) {
        subtitleList.add(subtitle);
    }

    protected boolean ignoreNameOrValue(final String phrase) {
        return ignoreNameValues.contains(phrase);
    }

    protected void readInit() {
        currentSubtitle = subtitleList.get(titleIndex);
    }

    public void readThis(final String urlString) {
        out("*** readThis 000");
        readInit();
        try {
            final URL oracle = new URL(urlString);
            final BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            String inputLine;
            out("*** readThis 001");
            while ((inputLine = in.readLine()) != null) {
                out("*** readThis 002");
                if (!this.processLine(inputLine)) {
                    out("*** readThis FALSE 099");
                    break;
                }
            }
            out("*** readThis 099");
            in.close();
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

        out ("*** 000");
        if (!turnOnImport) {
            handleBodyStart(line);
            return true;
        }

        out ("*** 001");
        if (line.contains(BODY_END)) {
            titleIndex++;
            if (titleIndex >= subtitleList.size()) {
                out ("*** Body End 099");
                return false;
            }
            currentSubtitle = subtitleList.get(titleIndex);
            turnOnImport = false;
            return true;
        }

        out ("*** 002");
        if (!trTag) {
            if (line.startsWith(TR_START)) {
                trTag = true;
                return true;
            }
        }

        out ("*** 003");
        if (line.startsWith(TR_END)) {
            trTag = false;
            return true;
        }

        out ("*** 004");
        extractDataFromLine(line);
        out ("*** 099");
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