package com.pull.law.misc;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.util.Strings;

@Getter
@ToString
public class BluePart {
    public static final String SPACE = " ";
    public static final String EMPTY_STRING = "";
    public static final String SECTION_SYMBOL = "$";

    private BluePartType partType;
    private String originalPart;
    private String normalizedPart;

    public static BluePart of(final String partString) {
        if (Strings.isEmpty(partString)) {
            return emptyPart();
        }
        final BluePart bluePart = new BluePart();
        bluePart.partType = BluePartType.of(partString);
        bluePart.originalPart = partString;
        bluePart.normalizedPart = bluePart.normalizeBluePart(partString);
        return bluePart;
    }

    private String normalizeBluePart(final String partString) {
        switch (partType) {
            case ALPHA:
                return normalizeAlpha();
            case NUMERIC:
                return normalizeNumeric();
            case SYMBOL:
                return originalPart;
            case MISC:
                return originalPart;
            default:
                throw new IllegalArgumentException(String.format(
                        "Part Type to normalize is not implmented '%s'", partType));
        }
    }

    private String normalizeNumeric() {
        return originalPart;
    }

    private String normalizeAlpha() {
        String temp = originalPart.trim();
        temp = StringEscapeUtils.unescapeHtml4(temp);
        temp = temp.toUpperCase();
        // Remove all non A-Z chars
        temp = temp.replaceAll("[^A-Z]", "");
        return temp;
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
}
