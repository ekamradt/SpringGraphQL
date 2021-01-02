package com.pull.law.bluebooksearch.service;

import com.pull.law.bluebooksearch.misc.BlueParts;
import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import com.pull.law.bluebooksearch.misc.LimitType;
import com.pull.law.bluebooksearch.pullers.lawresourceorg.FoundPart;
import com.pull.law.bluebooksearch.pullers.lawresourceorg.FoundParts;
import com.pull.law.bluebooksearch.search.MatchPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BluebookSearchService {

    public static final String LIMIT_TYPE_STATES = "States";

    private final FoundPartService foundPartService;
    private String alphaNormalized;

    public void findBlueParts(final List<BlueParts> bluePartList, List<BlueSearchRecord> infos) {

        final List<FoundParts> foundPartList = new ArrayList<>();

        // Test just one
        // final BlueParts tempParts = BlueParts.of("MSRB Rule G-3 (a-c,f-h) (Includes Supplementary Material .01,.03,.05)");
        // bluePartList.clear();
        // bluePartList.add(tempParts);

        bluePartList.forEach(blueParts -> {
            final FoundParts foundParts = new FoundParts();
            blueParts.setFoundParts(foundParts);
            foundParts.setBlueParts(blueParts);

            searchOneBluebook(infos, blueParts, foundParts);
            foundPartList.add(foundParts);
        });

        final int total = foundPartList.size();
        final long failedCount = foundPartList.stream().filter(p -> !p.isMatchFound()).count();
        final List<FoundParts> failedList = foundPartList.stream().filter(p -> !p.isMatchFound()).collect(Collectors.toList());

        System.out.println(String.format("*** End total: %s  failed: %s", total, failedCount));
        ///////// foundPartService.dumpFoundParts(foundPartList);
    }

    // 1) Start checking from matches using All of the first Alpha portion of
    //      the bluebook, say 'A B C'.
    // 2) If that doesn't work then remove an alpha bit from the end 'C'
    //      and try to match what's left 'A B'.
    // 3) If that doesn't work goto 2) and strip off another alpha bit 'B'
    //      and try to match what's left 'A'
    // 4) If part matches, say 'A', then try to match the next bits 'B C' as a second match part
    // 5) If 'B C' doesn't match, goto 2) and strip off the last alpha part 'C' and try to
    //      match what's left 'B' .... and so on
    //
    // In the end we either have one or more matching parts, thus matching all parts.
    //  Or we didn't match something and we fail.
    //
    private void searchOneBluebook(
            final List<BlueSearchRecord> recordsToSearch, final BlueParts blueParts, final FoundParts foundParts) {

        try {
            final MatchPacket matchPacket = new MatchPacket(blueParts);
            while (matchPacket.getAlphaCountdownSize() > 0) {
                final String alphaNormalized = matchPacket.getNormalizedAlphaBitsToMatch();
                final List<BlueSearchRecord> matchingRecords = findMatchingRecords(recordsToSearch, alphaNormalized);

                final int matchSize = matchingRecords.size();
                if (matchSize > 0) {
                    foundParts.setMatchFound(true);
                    savedMatchedInfoAdjustforNextSearch(foundParts, matchPacket, matchingRecords, alphaNormalized);
                } else {
                    if (foundParts.isMatchFound() && matchPacket.getAlphaCountdownSize() == 1) {
                        // Found first one or more, but others could not be found.
                        //  So the whole bluebook was not found.
                        markPartAsNotFound(foundParts, alphaNormalized);
                        foundParts.setMatchFound(false);
                        break;
                    }
                    matchPacket.decrementAlphaCountdownSize();
                }
            }
        } catch (Exception e) {
            log.error("{}  blue: '{}'  Norm: '{}'",
                    foundParts.isMatchFound(), blueParts.getOriginalBluebook(), blueParts.getNormalizedBluebook());
            // throw new IllegalArgumentException(e);
            // foundParts.setMatchFound(false);
            // foundParts.setThrowable(e);
        }
        if (foundParts.isMatchFound()) {
            log.trace("{}  blue: '{}'  Norm: '{}'",
                    foundParts.isMatchFound(), blueParts.getOriginalBluebook(), blueParts.getNormalizedBluebook());
        } else {
            // No match
            log.info("{}  blue: '{}'  Norm: '{}'",
                    foundParts.isMatchFound(), blueParts.getOriginalBluebook(), blueParts.getNormalizedBluebook());
        }
    }

    private void savedMatchedInfoAdjustforNextSearch(
            final FoundParts foundParts, final MatchPacket matchPacket,
            final List<BlueSearchRecord> foundSearchRecords, final String alphaNormalized) {

        final List<BlueSearchRecord> filteredInfos = checkIfFilteredThenFilter(foundParts, foundSearchRecords);

        // Remove the matched alpha bits, and if any left, we try to match the rest in a subsequent pass.
        matchPacket.adjustAlphaBitsForFurtherMatches();
        flagBluebookIfToBeFiltered(foundParts, filteredInfos);
        storeTheFoundBluebookPart(foundParts, filteredInfos, alphaNormalized);
    }

    // We could not final part of the bluebook. We mark it as such and save what we have.
    private void markPartAsNotFound(final FoundParts foundParts, final String alphaNormalized) {
        final FoundPart foundPart = new FoundPart();
        foundPart.setAlphaNormalized(alphaNormalized);
        foundParts.addFoundPart(foundPart);
    }

    // We matched a section of the bluebook, save the data for this step, and continue.
    private void storeTheFoundBluebookPart(FoundParts foundParts, List<BlueSearchRecord> matchedBlueSearchRecords, String alphaNormalized) {
        final FoundPart foundPart = new FoundPart();
        foundPart.setMatchedBlueSearchRecords(new ArrayList<>(matchedBlueSearchRecords));
        foundPart.setAlphaNormalized(alphaNormalized);
        foundParts.addFoundPart(foundPart);
    }

    private void flagBluebookIfToBeFiltered(final FoundParts foundParts, final List<BlueSearchRecord> matchedBlueSearchRecords) {
        // If this specific blueblue is not marked as filtered, then check if it needs to be filtered.
        if (foundParts.getLimitType() == LimitType.ANY) {
            for (final BlueSearchRecord blueSearchRecord : matchedBlueSearchRecords) {
                final String subsubtitle = blueSearchRecord.getSubsubtitle();

                // Check if this bluebook is to be filtered by a US State.
                if (subsubtitle != null && subsubtitle.equals(LIMIT_TYPE_STATES)) {
                    foundParts.setLimitType(LimitType.STATE);
                    foundParts.setState(blueSearchRecord.getName());
                    matchedBlueSearchRecords.clear();
                    matchedBlueSearchRecords.add(blueSearchRecord);
                    break;
                }
            }
        }
    }

    // If we found a Filtered type (such as US 'State') then attempt to filter the found results
    //  to match that specific type.
    private List<BlueSearchRecord> checkIfFilteredThenFilter(final FoundParts foundParts, final List<BlueSearchRecord> foundSearchRecords) {
        if (foundParts.getLimitType() == LimitType.STATE) {
            final List<BlueSearchRecord> filteredFoundRecords = foundSearchRecords.stream()
                    .filter(lineInfo -> lineInfo.getSubtitle().equals(foundParts.getState()))
                    .collect(Collectors.toList());
            if (filteredFoundRecords.size() > 0) {
                return filteredFoundRecords;
            }
        }
        return foundSearchRecords;
    }

    private List<BlueSearchRecord> findMatchingRecords(
            final List<BlueSearchRecord> tempBlueSearchRecords, final String alphaNormalized) {

        final List<BlueSearchRecord> foundRecords = new ArrayList<>();
        tempBlueSearchRecords.forEach(searchRecord -> {
            final BlueParts blueParts = searchRecord.getBlueParts();
            final String normalizedBluebook = blueParts.getNormalizedBluebook();
            if (normalizedBluebook.equals(alphaNormalized)) {
                foundRecords.add(searchRecord);
            }
        });
        return foundRecords;
    }
}
