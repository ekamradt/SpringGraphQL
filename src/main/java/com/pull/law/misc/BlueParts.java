package com.pull.law.misc;

import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BlueParts {

    private String originalBluebook;
    private String normalizedBluebook;
    private String pattern;
    private List<BluePart> bluePartList;

    public static BlueParts of(final String line) {
        if (Strings.isEmpty(line)) {
            return null;
        }
        final BlueParts blueParts = new BlueParts();
        blueParts.originalBluebook = line;
        blueParts.bluePartList = blueParts.concatAllParts(line);
        blueParts.normalizedBluebook = blueParts.buildNormalizeParts();
        blueParts.pattern = blueParts.buildPattern();
        return blueParts;
    }

    private List<BluePart> concatAllParts(final String line) {
        String temp = line.trim();
        temp = temp.replaceAll("[\\s|\\h]+", BluePart.SPACE);
        final String[] parts = temp.split(BluePart.SPACE);

        return Arrays.stream(parts)
                .map(BluePart::of)
                .filter(BluePart::isNotEmpty)
                .collect(Collectors.toList());
    }

    private String buildNormalizeParts() {
        String temp = bluePartList.stream()
                .map(BluePart::getNormalizedPart)
                .collect(Collectors.joining(BluePart.SPACE));
        temp = temp.replaceAll("\\s+", BluePart.SPACE);
        return temp;
    }

    private String buildPattern() {
        return bluePartList.stream()
                .map(BluePart::getTypeCode)
                .collect(Collectors.joining());
    }

    public String dump() {
        String msg = "originalBluebook='" + originalBluebook + "'" +
                " normalizedBluebook='" + normalizedBluebook + "'" +
                " pattern='" + pattern + "'";
        return msg;
    }
}
