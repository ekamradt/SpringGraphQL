package com.pull.law.bluebook.search;

import com.pull.law.bluebook.misc.BluePart;
import com.pull.law.bluebook.misc.BlueParts;
import com.pull.law.bluebook.misc.IndexPair;
import lombok.Getter;
import org.assertj.core.util.Lists;

import java.util.Arrays;
import java.util.List;

@Getter
public class MatchPacket {

    public static final List<String> BLUEBOOK_IGNORE_CODES = List.of("CODE", "PART", "CHAPTER", "CHAPTERS", "RULES",
            "SECTION");

    private final String normalizedBluebook;
    private final IndexPair indexPair;

    private List<String> alphaBits;
    private List<String> blueBits;
    private int alphaCountdownSize;

    public MatchPacket(final BlueParts blueParts) {
        this.indexPair = calculateFirstAlphaPart(blueParts.getPattern());
        this.alphaCountdownSize = getIndexPair().getNumberOfAlphas();

        this.normalizedBluebook = blueParts.getNormalizedBluebook();
        final List<String> blueBits = Arrays.asList(normalizedBluebook.split(BluePart.SPACE));
        if (indexPair.haveIndexPair()) {
            this.alphaBits = blueBits.subList(indexPair.getIndexStart(), indexPair.getIndexEnd());
        }
    }

    public String getNormalizedAlphaBitsToMatch() {
        try {
            final StringBuilder val = new StringBuilder();
            final int bitSize = alphaBits.size();
            for (int i = 1; i <= alphaCountdownSize && i <= bitSize; i++) {
                val.append(alphaBits.get(i - 1)).append(" ");
            }
            return val.toString().trim();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    // Remove the matched alpha bits, return what's left, if any.
    public void adjustAlphaBitsForFurtherMatches() {
        try {
            alphaBits = alphaBits.subList(alphaCountdownSize, alphaBits.size());
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("alphaCountdownSize: '%s'  alphaBits.size:'%s'  err: %s",
                            alphaCountdownSize, alphaBits.size(), e.getMessage()));
        }
        // Ignore some words -- which followed a found alpha bit.
        if (alphaBits.size() > 0) {
            for (final String ignoreThis : BLUEBOOK_IGNORE_CODES) {
                if (alphaBits.get(0).equals(ignoreThis)) {
                    alphaBits = alphaBits.size() == 1 ? Lists.emptyList() : alphaBits.subList(1, alphaBits.size());
                    break;
                }
            }
            alphaCountdownSize = alphaBits.size();
        } else {
            alphaCountdownSize = 0;
        }
    }

    public void decrementAlphaCountdownSize() {
        alphaCountdownSize--;
    }

    public void decrementByAlphaBits() {
        alphaCountdownSize -= alphaBits.size();
    }

    private IndexPair calculateFirstAlphaPart(final String pattern) {
        int indexStart = -1;
        int indexEnd = -1;
        for (int i = 0; i < pattern.length(); i++) {
            final char ch = pattern.charAt(i);
            if (indexStart == -1) {
                if (ch == 'A') {
                    indexStart = i;
                }
                continue;
            }
            if (ch != 'A') {
                indexEnd = i;
                break;
            }
        }
        if (indexStart > -1 && indexEnd == -1) {
            indexEnd = pattern.length();
        }
        int iSize = 0;
        if (indexStart > -1) {
            iSize = indexEnd - indexStart;
        }
        return IndexPair.builder()
                .indexStart(indexStart)
                .indexEnd(indexEnd)
                .numberOfAlphas(iSize)
                .build();
    }
}
