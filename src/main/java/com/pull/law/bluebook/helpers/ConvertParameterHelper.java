package com.pull.law.bluebook.helpers;

import com.pull.law.bluebook.misc.BlueConverter;
import lombok.Getter;


@Getter
public class ConvertParameterHelper {
    private final boolean paramRequired;
    private final String paramName;

    public ConvertParameterHelper(final String inParamName) {
        boolean tempParamRequired = false;
        String tempParamName = inParamName;
        if (tempParamName.endsWith(BlueConverter.REQUIRED_CHAR)) {
            tempParamRequired = true;
            tempParamName = tempParamName.substring(0, tempParamName.length() - 1);
        }
        paramRequired = tempParamRequired;
        paramName = tempParamName;
    }
}

