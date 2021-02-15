package com.pull.law.bluebook.service;

import com.pull.law.bluebook.misc.BluePieces;
import com.pull.law.bluebook.misc.BlueSearchRecord;
import com.pull.law.bluebook.pullers.lawresourceorg.FoundParts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoundPartService {

    public void dumpFoundParts(final List<FoundParts> foundPartList) {
        foundPartList.forEach(foundParts -> {
            final BluePieces bluePieces = foundParts.getBluePieces();
            out("----------");
            out("WellsFarge bluebook: '%s'  normalized: '%s'  Did it match: %s ",
                    bluePieces.getOriginalBluebook(), bluePieces.getNormalizedBluebook(), foundParts.isMatchFound());
            foundParts.getFoundParts().forEach(foundPart -> {

                out("  Part: '%s' ", foundPart.getAlphaNormalized());
                final List<BlueSearchRecord> matchedBlueSearchRecords = foundPart.getMatchedBlueSearchRecords();
                if (matchedBlueSearchRecords != null) {
                    matchedBlueSearchRecords.forEach(lineInfo -> {
                        out("      : '%s' ", lineInfo.toCsv());
                    });
                } else {
                    out("ERROR: No matchedBlueSearchRecords");
                }
            });
        });
    }

    private void out(final String format, final Object... values) {
        System.out.println(String.format(format, values));
    }

    private void out(final String msg) {
        System.out.println(msg);
    }
}