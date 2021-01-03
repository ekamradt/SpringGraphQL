package com.pull.law.blue;

import com.pull.law.bluebook.misc.BlueConverter;

public class BlueTestHelper {

    public static String wrapParamNameForTemplate(final String paramName) {
        return BlueConverter.PARAMETER_CHAR_BEG + paramName + BlueConverter.PARAMETER_CHAR_END;
    }

    public static String wrapRequiredParamNameForTemplate(final String paramName) {
        return BlueConverter.PARAMETER_CHAR_BEG + paramName + BlueConverter.REQUIRED_CHAR
                + BlueConverter.PARAMETER_CHAR_END;
    }
}
