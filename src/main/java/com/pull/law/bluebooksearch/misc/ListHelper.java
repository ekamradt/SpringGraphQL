package com.pull.law.bluebooksearch.misc;

import java.util.Collection;

public class ListHelper {
    public static <T> boolean isEmptyOrNull(final Collection<T> value) {
        return value == null || value.size() == 0;
    }
}
