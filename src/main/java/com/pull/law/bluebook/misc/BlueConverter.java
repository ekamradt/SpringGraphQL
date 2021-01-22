package com.pull.law.bluebook.misc;

import com.pull.law.bluebook.helpers.BlueConstants;
import com.pull.law.bluebook.helpers.ConvertParameterHelper;
import org.assertj.core.util.Strings;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.pull.law.bluebook.misc.BlueTemplate.US_STATE_HOUSE_BILL_FORMAT;

@Service
public class BlueConverter {

    private static final Map<String, Function<BluePieces, String>> blueFunctionMap = new HashMap<>();

    static {
        // Bluebook template value replacement functions.
        blueFunctionMap.put("SESSION", BluePieces::getSession);
        blueFunctionMap.put("CHAPTER", BluePieces::getChapter);
        blueFunctionMap.put("SESSION_NAME", BluePieces::getSessionName);
        blueFunctionMap.put("SESSION_TYPE", BluePieces::getSessionType);
        blueFunctionMap.put("STATE_PLUS_DATE", BluePieces::getStatePlusDate);
    }


    public String buildCitation(final BluePieces bluePieces) {
        final BlueFormatTypeEnum formatTypeEnum = bluePieces.getFormatTypeEnum();
        switch (formatTypeEnum) {
            case US_STATE_HOUSE_BILL:
                return processFormat(US_STATE_HOUSE_BILL_FORMAT, bluePieces);
            default:
                throw new IllegalArgumentException(
                        String.format("BlueFormatTypeEnum: NOt implemented '%s'", formatTypeEnum));
        }
    }

    public String processFormat(final String formatString, final BluePieces bluePieces) {

        final StringBuilder resultingString = new StringBuilder();
        final String[] parts = formatString.split(BlueConstants.PARAMETER_CHAR_BEG);
        if (parts.length >= 2) {
            for (final String part : parts) {
                final String[] paramAndSuffix = part.split(BlueConstants.PARAMETER_CHAR_END);
                if (paramAndSuffix.length == 2) {
                    final String paramName = paramAndSuffix[0];
                    final String suffix = paramAndSuffix[1];
                    final String paramResult = convertParameter(paramName, bluePieces);
                    resultingString.append(paramResult).append(suffix);
                } else if (paramAndSuffix.length == 1) {
                    final String splitValue = paramAndSuffix[0];
                    if (splitValue.length() != part.length()) {
                        final String paramName = paramAndSuffix[0];
                        final String paramResult = convertParameter(paramName, bluePieces);
                        resultingString.append(paramResult);
                    } else {
                        // No change -- just copy
                        resultingString.append(splitValue);
                    }
                }
            }
        } else {
            resultingString.append(formatString);
        }
        return cleanResult(resultingString.toString());
    }

    private String cleanResult(final String value) {
        return value.replaceAll("\\s+", " ");
    }

    private String convertParameter(final String paramName, final BluePieces bluePieces) {
        final ConvertParameterHelper paramHelper = new ConvertParameterHelper(paramName);
        return paramResultFromBlue(paramHelper, bluePieces);
    }

    public String paramResultFromBlue(final ConvertParameterHelper paramHelper, final BluePieces bluePieces) {

        final String paramName = paramHelper.getParamName();
        final Function<BluePieces, String> blueFunction = blueFunctionMap.get(paramName);
        if (blueFunction == null) {
            throw new IllegalArgumentException(
                    String.format("BlueConverter paramName '%s' is not implemented.", paramName));
        }

        final String result = blueFunction.apply(bluePieces);
        if (paramHelper.isParamRequired() && Strings.isNullOrEmpty(result)) {
            throw new IllegalArgumentException(
                    String.format("BlueConverter Required paramName '%s' result is null or empty.", paramName));
        }
        return result != null ? result : "";
    }
}
