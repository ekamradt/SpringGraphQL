package com.pull.law.bluebook.misc;

import com.pull.law.bluebook.pullers.lawresourceorg.FoundParts;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.pull.law.bluebook.misc.BluePart.SESSION_SYMBOL;
import static com.pull.law.bluebook.misc.BluePart.SPACE;

@Data
@Accessors(chain = true)
public class BlueParts {

    private String session;
    private String sessionType;
    private String sessionName;
    private String chapter;
    private String section;
    private String statePlusDate;
    private BlueFormatTypeEnum formatTypeEnum;


    private String originalBluebook;
    private String normalizedBluebook;
    private String pattern;
    private List<BluePart> bluePartList;
    private final BlueSpecial blueSpecial = new BlueSpecial();
    @Setter
    private FoundParts foundParts;

    public static BlueParts of(final String line) {
        if (Strings.isEmpty(line)) {
            return null;
        }
        final BlueParts blueParts = new BlueParts();
        final String originalBluebook = adjustLine(line);
        blueParts.originalBluebook = originalBluebook;
        blueParts.bluePartList = blueParts.concatAllParts(originalBluebook);
        blueParts.normalizedBluebook = blueParts.buildNormalizeParts();
        blueParts.pattern = blueParts.buildPattern();
        return blueParts;
    }

    public static String normalizeValue(final String value) {
        final BlueParts blueParts = of(value);
        return blueParts != null ? blueParts.getNormalizedBluebook() : null;
    }

    private static String adjustLine(final String line) {
        String temp = line.replace(SESSION_SYMBOL, " " + SESSION_SYMBOL + " ");
        temp = temp.replaceAll("\\s+", SPACE);
        return temp;
    }

    private List<BluePart> concatAllParts(final String line) {
        String temp = line.trim();
        // Replace whitespace w/ a single space
        temp = temp.replaceAll("[\\s|\\h]+", SPACE);

        final String[] parts = temp.split(SPACE);
        return Arrays.stream(parts)
                .map(blueSpecial::buildBluePart)
                .filter(BluePart::isNotEmpty)
                .collect(Collectors.toList());

    }

    private String buildNormalizeParts() {
        String temp = bluePartList.stream()
                .map(BluePart::getNormalizedPart)
                .collect(Collectors.joining(SPACE));
        temp = temp.replaceAll("\\s+", SPACE);
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
