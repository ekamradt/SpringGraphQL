package com.pull.law.bluebooksearch.search;

import com.pull.law.bluebooksearch.misc.BluePart;
import com.pull.law.bluebooksearch.misc.BlueParts;
import com.pull.law.bluebooksearch.misc.IndexPair;
import lombok.Getter;
import org.assertj.core.util.Lists;

import java.util.Arrays;
import java.util.List;

@Getter
public class MatchPacket {

    public static final String BLUEBOOK_IGNORE_CODE = "CODE";

    private List<String> alphaBits;
    private String normalizedBluebook;
    private List<String> blueBits;
    private final IndexPair indexPair;
    private int alphaCountdownSize;

    public MatchPacket(final BlueParts blueParts) {
        this.indexPair = calculateFirstAlphaPart(blueParts.getPattern());
        this.alphaCountdownSize = getIndexPair().getNumberOfAlphas();

        this.normalizedBluebook = blueParts.getNormalizedBluebook();
        final List<String> blueBits = Arrays.asList(normalizedBluebook.split(BluePart.SPACE));
        this.alphaBits = blueBits.subList(indexPair.getIndexStart(), indexPair.getIndexEnd());
    }

    public String getNormalizedAlphaBitsToMatch() {
        try {
            final StringBuilder val = new StringBuilder();
            for (int i = 1; i <= alphaCountdownSize; i++) {
                val.append(alphaBits.get(i - 1)).append(" ");
            }
            return val.toString().trim();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    // Remove the matched alpha bits, return what's left, if any.
    public void adjustAlphaBitsForFurtherMatches() {
        alphaBits = alphaBits.subList(alphaCountdownSize, alphaBits.size());

        // Ignore the word 'CODE' -- which followed a found alpha bit.
        if (alphaBits.size() > 0 && alphaBits.get(0).equals(BLUEBOOK_IGNORE_CODE)) {
            alphaBits = alphaBits.size() == 1 ? Lists.emptyList() : alphaBits.subList(1, alphaBits.size());
        }
        alphaCountdownSize = alphaBits.size() + 1;
    }

    public void decrementAlphaCountdownSize() {
        alphaCountdownSize--;
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
        if (indexStart > -1 && indexEnd > -1) {
            iSize = indexEnd - indexStart;
        }
        return IndexPair.builder().indexStart(indexStart).indexEnd(indexEnd).numberOfAlphas(iSize).build();
    }
}
