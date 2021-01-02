package com.pull.law.bluebooksearch.misc;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum BlueSpecialType {
    ANY(""), MSRB("MSRB");

    @Getter
    private final String key;
    private static Map<String, BlueSpecialType> reverseMap = new HashMap<>();

    BlueSpecialType(final String key) {
        this.key = key;
    }

    public static BlueSpecialType fromKey(final String findKey) {
        if (reverseMap.size() == 0) {
            buildReverseMap();
        }
        final BlueSpecialType blueSpecialType = reverseMap.get(findKey);
        return blueSpecialType != null ? blueSpecialType : ANY;
    }

    private static void buildReverseMap() {
        Arrays.stream(BlueSpecialType.values())
                .forEach(specialType -> reverseMap.put(specialType.key, specialType));
    }
}
