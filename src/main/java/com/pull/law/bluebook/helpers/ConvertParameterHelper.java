package com.pull.law.bluebook.helpers;

import lombok.Getter;


@Getter
public class ConvertParameterHelper {
    private final boolean paramRequired;
    private final String paramName;

    // From a Blue Template: is the input paramName required or not?
    public ConvertParameterHelper(final String inParamName) {
        boolean tempParamRequired = false;
        String tempParamName = inParamName;
        if (tempParamName.endsWith(BlueConstants.REQUIRED_CHAR)) {
            tempParamRequired = true;
            tempParamName = tempParamName.substring(0, tempParamName.length() - 1);
        }
        paramRequired = tempParamRequired;
        paramName = tempParamName;
    }
}

