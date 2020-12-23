package com.pull.law.misc;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LineInfo {
    private String title;
    private String subtitle;
    private String name;
    private String value;
    private String note;

    public static LineInfo copy(final LineInfo inLineInfo) {
        return new LineInfo()
                .setTitle(inLineInfo.getTitle())
                .setSubtitle(inLineInfo.getSubtitle())
                .setName(inLineInfo.getName())
                .setValue(inLineInfo.getValue())
                .setNote(inLineInfo.getNote());
    }

    public void appendName(final String val) {
        if (name != null) {
            name += " " + val;
        } else {
            name = val;
        }
        name = name.trim();
    }

    public void appendValue(final String val) {
        if (value != null) {
            value += " " + val;
        } else {
            value = val;
        }
        value = value.trim();
    }
}