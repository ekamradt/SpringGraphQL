package com.pull.law.bluebook.misc;

import lombok.Data;
import lombok.experimental.Accessors;

//
// Initial class holding special cases for parsing/making bluebook citations.
//  This is bound to be expanded later, including custom rules in a database.
//
@Data
@Accessors(chain = true)
public class BlueSpecial {
    private BlueSpecialType specialType = BlueSpecialType.ANY;

    public void adjustType(final String part) {
        if (specialType == BlueSpecialType.ANY) {
            specialType = BlueSpecialType.fromKey(part);
        }
    }

    public BluePiece buildBluePart(final String part) {
        adjustType(part);
        switch (specialType) {
            case ANY:
                return BluePiece.of(part);
            case MSRB:
                if (part.startsWith("G-")) {
                    return BluePiece.forceNumeric(part);
                } else {
                    return BluePiece.of(part);
                }
            default:
                throw new IllegalArgumentException(String.format("BlueSpecialType not implemented '%s'", specialType));
        }
    }
}
