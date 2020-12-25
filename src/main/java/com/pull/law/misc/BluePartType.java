package com.pull.law.misc;

import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

public enum BluePartType {
    ALPHA("A"), NUMERIC("N"), SYMBOL("S"), MISC("M"), EMPTY("");

    @Getter
    private final String code;

    BluePartType(final String inCode) {
        this.code = inCode;
    }

    public static BluePartType of(final String partString) {
        if (Strings.isEmpty(partString)) {
            return EMPTY;
        }
        final int code = partString.codePointAt(0);
        if (Character.isAlphabetic(code)) {
            return ALPHA;
        }
        if (Character.isDigit(code)) {
            return NUMERIC;
        }
        if (partString.length() == 1) {
            if (partString.equals(BluePart.SECTION_SYMBOL)) {
                return SYMBOL;
            } else {
                return MISC;
            }
        }
        return ALPHA;
        //throw new IllegalArgumentException(String.format("Unable to recognize Blue Part '%s'", partString));
    }
}
