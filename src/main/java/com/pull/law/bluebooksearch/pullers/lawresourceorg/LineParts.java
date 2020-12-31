package com.pull.law.bluebooksearch.pullers.lawresourceorg;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineParts {
    private String line;
    private List<String> values = new ArrayList<>();

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
