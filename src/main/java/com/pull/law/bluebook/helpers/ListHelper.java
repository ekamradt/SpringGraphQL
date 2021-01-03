package com.pull.law.bluebook.helpers;

import java.util.Collection;

public class ListHelper {
    public static <T> boolean isEmptyOrNull(final Collection<T> value) {
        return value == null || value.size() == 0;
    }
}
