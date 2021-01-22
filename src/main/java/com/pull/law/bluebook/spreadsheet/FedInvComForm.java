package com.pull.law.bluebook.spreadsheet;

import com.pull.law.bluebook.misc.BluePieces;
import com.pull.law.bluebook.pullers.lawresourceorg.FoundParts;
import lombok.Getter;
import org.assertj.core.util.Strings;

// https://docs.google.com/spreadsheets/d/1zqCA-JJ8_dDl3pckWMWQRFqYTdCIPAL-/edit#gid=1329909211
@Getter
public class FedInvComForm {
    private String source;
    private String shortName;
    private String issuingBody;
    private String issuingType;
    private String citation;
    private String citationUrl;
    private BluePieces bluePieces;

    public static FedInvComForm fromPipedLine(final String line) {
        final String temp = line + "|\"\"|\"\"|\"\"|\"\"|\"\"|\"\"|\"\"|\"\"|\"\"";
        final String[] split = temp.split("\\|");
        final FedInvComForm fedInvComForm = new FedInvComForm();
        int i = 0; // Skip first entry
        fedInvComForm.source = split[++i];
        fedInvComForm.shortName = split[++i];
        fedInvComForm.issuingBody = split[++i];
        fedInvComForm.issuingType = split[++i];
        fedInvComForm.citation = split[++i];
        fedInvComForm.citationUrl = split[++i];

        if (Strings.isNullOrEmpty(fedInvComForm.citation)) {
            return null;
        }
        fedInvComForm.bluePieces = BluePieces.of(fedInvComForm.citation);
        return fedInvComForm;
    }

    public boolean found() {
        FoundParts foundParts = null;
        if (bluePieces != null) {
            foundParts = bluePieces.getFoundParts();
        }
        return foundParts != null && foundParts.isMatchFound();
    }

    public String toPipe() {
        String normalized = "";
        if (bluePieces != null) {
            normalized = bluePieces.getNormalizedBluebook();
        }
        return String.format("%s|%s|%s|%s|%s|%s|%s",
                found(), citation, normalized, source, shortName, issuingBody, issuingType);
    }

    public static String headerPipe() {
        final String delim = "|";
        return "found|"
                + "bluebook|"
                + "normalized|"
                + "source|"
                + "shortName|"
                + "issuingBody|"
                + "issuingType";
    }
}
