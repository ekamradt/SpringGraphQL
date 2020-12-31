package com.pull.law.bluebooksearch.misc;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IndexPair {
    private int indexStart = -1;
    private int indexEnd = -1;
    private int numberOfAlphas = 0;
}