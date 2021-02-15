package com.pull.law.bluebook.pullers.lawresourceorg;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class LineParts {

    @Setter
    @Getter
    private String line;
    private final List<String> values = new ArrayList<>();

    public void addValue(final String val) {
        values.add(val);
    }

    public String getValueFirst() {
        return _getValue(0, "No First Value.");
    }

    public String getValueSecond() {
        return _getValue(1, "No Second Value.");
    }

    public String getValueThird() {
        return _getValue(2, "No Third Value.");
    }

    private String _getValue(final int index, final String errMsg) {
        if (values.size() >= index) {
            return values.get(index);
        }
        throw new IllegalArgumentException(errMsg);
    }
}
