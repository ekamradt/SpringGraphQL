package com.pull.law.bluebooksearch.misc;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BlueSpecial {
    private BlueSpecialType specialType = BlueSpecialType.ANY;

    public void adjustType(final String part) {
        if (specialType == BlueSpecialType.ANY) {
            specialType = BlueSpecialType.fromKey(part);
        }
    }

    public BluePart buildBluePart(final String part) {
        adjustType(part);
        switch (specialType) {
            case ANY:
                return BluePart.of(part);
            case MSRB:
                if (part.startsWith("G-")) {
                    return BluePart.forceNumeric(part);
                } else {
                    return BluePart.of(part);
                }
            default:
                throw new IllegalArgumentException(String.format("BlueSpecialType not implemented '%s'", specialType));
        }
    }
}
