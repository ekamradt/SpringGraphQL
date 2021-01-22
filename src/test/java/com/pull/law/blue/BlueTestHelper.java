package com.pull.law.blue;

import com.pull.law.bluebook.helpers.BlueConstants;

public class BlueTestHelper {

    public static String wrapParamNameForTemplate(final String paramName) {
        return BlueConstants.PARAMETER_CHAR_BEG + paramName + BlueConstants.PARAMETER_CHAR_END;
    }

    public static String wrapRequiredParamNameForTemplate(final String paramName) {
        return BlueConstants.PARAMETER_CHAR_BEG + paramName + BlueConstants.REQUIRED_CHAR
                + BlueConstants.PARAMETER_CHAR_END;
    }
}
