package com.pull.law.bluebook.misc;


import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Builder
@Accessors(chain = true)
public class IndexPair {
    private Integer indexStart = -1;
    private Integer indexEnd = -1;
    private Integer numberOfAlphas = 0;

    public boolean haveIndexPair(){
        return indexStart > -1 && indexEnd > -1;
    }
}