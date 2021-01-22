package com.pull.law.bluebook.misc;

import com.pull.law.bluebook.pullers.lawresourceorg.FoundParts;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.pull.law.bluebook.misc.BluePiece.SESSION_SYMBOL;
import static com.pull.law.bluebook.misc.BluePiece.SPACE;

@Data
@Accessors(chain = true)
public class BluePieces {

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
    private List<BluePiece> bluePieceList;
    private final BlueSpecial blueSpecial = new BlueSpecial();
    @Setter
    private FoundParts foundParts;

    public static BluePieces of(final String line) {
        if (Strings.isEmpty(line)) {
            return null;
        }
        final BluePieces bluePieces = new BluePieces();
        final String originalBluebook = adjustLine(line);
        bluePieces.originalBluebook = originalBluebook;
        bluePieces.bluePieceList = bluePieces.concatAllParts(originalBluebook);
        bluePieces.normalizedBluebook = bluePieces.buildNormalizeParts();
        bluePieces.pattern = bluePieces.buildPattern();
        return bluePieces;
    }

    public static String normalizeValue(final String value) {
        final BluePieces bluePieces = of(value);
        return bluePieces != null ? bluePieces.getNormalizedBluebook() : null;
    }

    private static String adjustLine(final String line) {
        String temp = line.replace(SESSION_SYMBOL, " " + SESSION_SYMBOL + " ");
        temp = temp.replaceAll("\\s+", SPACE);
        return temp;
    }

    private List<BluePiece> concatAllParts(final String line) {
        String temp = line.trim();
        // Replace whitespace w/ a single space
        temp = temp.replaceAll("[\\s|\\h]+", SPACE);

        final String[] parts = temp.split(SPACE);
        return Arrays.stream(parts)
                .map(blueSpecial::buildBluePart)
                .filter(BluePiece::isNotEmpty)
                .collect(Collectors.toList());
    }

    private String buildNormalizeParts() {
        String temp = bluePieceList.stream()
                .map(BluePiece::getNormalizedPart)
                .collect(Collectors.joining(SPACE));
        temp = temp.replaceAll("\\s+", SPACE);
        return temp;
    }

    private String buildPattern() {
        return bluePieceList.stream()
                .map(BluePiece::getTypeCode)
                .collect(Collectors.joining());
    }

    public String dump() {
        String msg = "originalBluebook='" + originalBluebook + "'" +
                " normalizedBluebook='" + normalizedBluebook + "'" +
                " pattern='" + pattern + "'";
        return msg;
    }
}
