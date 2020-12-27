package com.pull.law.misc;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.util.Strings;

@Data
@Accessors(chain = true)
public class LineInfo {
    private String title = "";
    private String subtitle = "";
    private String name = "";
    private String value = "";
    private String normalizedBluebook = "";
    private String note = "";

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

    public String toCsv() {
        try {
            final BlueParts blueParts = BlueParts.of(value);
            if (blueParts == null) {
                throw new IllegalArgumentException("toCsv: Null BlueParts");
            }
            this.normalizedBluebook = blueParts.getNormalizedBluebook();
            return wrap(title) + "," +
                    wrap(subtitle) + "," +
                    wrap(name) + "," +
                    wrap(value) + "," +
                    wrap(normalizedBluebook) + "," +
                    wrap(note);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String wrap(final String msg) {
        return Strings.isEmpty(msg) ? "" : "\"" + msg + "\"";
    }
}