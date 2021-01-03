package com.pull.law.bluebook.misc;

import com.pull.law.bluebook.helpers.ConvertParameterHelper;
import org.springframework.stereotype.Service;

@Service
public class BlueConverter {

    public static final String REQUIRED_CHAR = "!";
    public static final String PARAMETER_CHAR_BEG = "<";
    public static final String PARAMETER_CHAR_END = ">";
    public static final String SESSION_SYMBOL = "§";

    public static final String US_STATE_HOUSE_BILL_FORMAT =
            "H.R. <SESSION!> § <CHAPTER!>, <SESSION_NAME> <SESSION_TYPE> <(STATE_PLUS_DATE)>";


    public String buildCitation(final BlueParts blueParts) {
        final BlueFormatTypeEnum formatTypeEnum = blueParts.getFormatTypeEnum();
        switch (formatTypeEnum) {
            case US_STATE_HOUSE_BILL:
                return processFormat(US_STATE_HOUSE_BILL_FORMAT, blueParts);
            default:
                throw new IllegalArgumentException(
                        String.format("BlueFormatTypeEnum: NOt implemented '%s'", formatTypeEnum));
        }
    }

    public String processFormat(final String formatString, final BlueParts blueParts) {

        String tempFormatString = formatString;
        StringBuilder resultingString = new StringBuilder();

        final String[] parts = tempFormatString.split(PARAMETER_CHAR_BEG);
        if (parts.length >= 2) {
            for (final String part : parts) {
                final String[] paramAndSuffix = part.split(PARAMETER_CHAR_END);
                if (paramAndSuffix.length == 2) {
                    final String paramName = paramAndSuffix[0];
                    final String suffix = paramAndSuffix[1];
                    final String paramResult = convertParameter(paramName, blueParts);
                    resultingString.append(paramResult).append(suffix);

                } else if (paramAndSuffix.length == 1) {
                    final String splitValue = paramAndSuffix[0];
                    if (splitValue.length() != part.length()) {
                        final String paramName = paramAndSuffix[0];
                        final String paramResult = convertParameter(paramName, blueParts);
                        resultingString.append(paramResult);
                    } else {
                        // No change -- just copy
                        resultingString.append(splitValue);
                    }
                }
            }
        } else {
            resultingString = new StringBuilder(formatString);
        }
        return resultingString.toString();
    }

    private String convertParameter(final String paramName, final BlueParts blueParts) {
        final ConvertParameterHelper paramHelper = new ConvertParameterHelper(paramName);
        return paramResultFromBlue(paramHelper, blueParts);
    }

    public String paramResultFromBlue(final ConvertParameterHelper paramHelper, final BlueParts blueParts) {

        final String paramName = paramHelper.getParamName();
        String result = null;

        switch (paramName) {
            case "SESSION":
                result = blueParts.getSession();
                break;
            case "CHAPTER":
                result = blueParts.getChapter();
                break;
            case "SESSION_NAME":
                result = blueParts.getSessionName();
                break;
            case "SESSION_TYPE":
                result = blueParts.getSessionType();
                break;
            case "STATE_PLUS_DATE":
                result = blueParts.getStatePlusDate();
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("BlueConverter paramName '%s' is not implemented.", paramName));
        }
        if (paramHelper.isParamRequired() && result == null) {
            throw new IllegalArgumentException(
                    String.format("BlueConverter Required paramName '%s' is null.", paramName));
        }
        return result != null ? result : "";
    }
}
