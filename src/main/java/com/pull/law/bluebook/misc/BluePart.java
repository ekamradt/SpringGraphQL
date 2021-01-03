package com.pull.law.bluebook.misc;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.util.Strings;

@Getter
@ToString
public class BluePart {
    public static final String SPACE = " ";
    public static final String EMPTY_STRING = "";
    public static final String SESSION_SYMBOL = "§";

    private BluePartType partType;
    private String originalPart;
    private String normalizedPart;

    public static BluePart of(final String partString) {
        if (Strings.isEmpty(partString)) {
            return emptyPart();
        }
        return _of(partString, BluePartType.of(partString));
    }

    public static BluePart forceNumeric(final String partString) {
        if (Strings.isEmpty(partString)) {
            return emptyPart();
        }
        return _of(partString, BluePartType.NUMERIC);
    }

    private static BluePart _of(final String partString, final BluePartType bluePartType) {
        final BluePart bluePart = new BluePart();
        bluePart.partType = bluePartType;
        bluePart.originalPart = partString;
        bluePart.normalizedPart = bluePart.normalizeBluePart();
        return bluePart;
    }

    public static BluePart emptyPart() {
        final BluePart bluePart = new BluePart();
        bluePart.partType = BluePartType.EMPTY;
        bluePart.originalPart = EMPTY_STRING;
        bluePart.normalizedPart = EMPTY_STRING;
        return bluePart;
    }

    public String getTypeCode() {
        return partType.getCode();
    }

    public boolean isNotEmpty() {
        return partType != BluePartType.EMPTY;
    }

    private String normalizeBluePart() {
        switch (partType) {
            case ALPHA:
                return normalizeAlpha(originalPart);
            case NUMERIC:
                return normalizeNumeric(originalPart);
            case SYMBOL:
                return originalPart;
            case MISC:
                return originalPart;
            default:
                throw new IllegalArgumentException(String.format(
                        "Part Type to normalize is not implmented '%s'", partType));
        }
    }

    private String normalizeNumeric(final String part) {
        return part;
    }

    private String normalizeAlpha(final String part) {
        String temp = StringEscapeUtils.unescapeHtml4(part);
        temp = temp.toUpperCase();
        // Remove all non A-Z chars
        temp = temp.replaceAll("[^A-Z|^&]", "");
        return temp;
    }

    // protected String cleanString(String tempLine) {
    //     try {
    //         tempLine = tempLine.replace("&nbsp;", " ");
    //         if (tempLine.contains("<")) {
    //             tempLine = stripTagsByPosition(tempLine);
    //         }
    //         tempLine = URLDecoder.decode(tempLine, StandardCharsets.UTF_8.name());
    //         tempLine = StringEscapeUtils.unescapeHtml4(tempLine);
    //         tempLine = tempLine.replaceAll("\\s+", " ");
    //         return tempLine.trim();
    //     } catch (Exception e) {
    //         final String msg = "ERROR: '" + tempLine + "'";
    //         throw new IllegalArgumentException(msg, e);
    //     }
    // }
    //
    // protected String stripTagsByPosition(final String line) {
    //     String tempLine = line;
    //     int iStart = tempLine.indexOf('<');
    //     while (iStart >= 0) {
    //         int iEnd = tempLine.indexOf(">", iStart);
    //         tempLine = tempLine.substring(0, iStart) + tempLine.substring(iEnd + 1);
    //         iStart = tempLine.indexOf('<');
    //     }
    //     return tempLine;
    // }
}
