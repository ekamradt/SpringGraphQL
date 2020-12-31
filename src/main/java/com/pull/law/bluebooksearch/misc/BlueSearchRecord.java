package com.pull.law.bluebooksearch.misc;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

@Slf4j
@Data
@Accessors(chain = true)
public class BlueSearchRecord {
    private String title = "";
    private String subtitle = "";
    private String subsubtitle = "";
    private String name = "";
    private String value = "";
    private String dates = "";
    private String normalizedValue = "";
    private String note = "";

    public static BlueSearchRecord copy(final BlueSearchRecord inBlueSearchRecord) {
        return new BlueSearchRecord()
                .setTitle(inBlueSearchRecord.getTitle())
                .setSubtitle(inBlueSearchRecord.getSubtitle())
                .setSubsubtitle(inBlueSearchRecord.getSubsubtitle())
                .setName(inBlueSearchRecord.getName())
                .setDates(inBlueSearchRecord.getDates())
                .setValue(inBlueSearchRecord.getValue())
                .setNormalizedValue(inBlueSearchRecord.getNormalizedValue())
                .setNote(inBlueSearchRecord.getNote());
    }

    public static BlueSearchRecord copyTitles(final BlueSearchRecord inBlueSearchRecord) {
        return new BlueSearchRecord()
                .setTitle(inBlueSearchRecord.getTitle())
                .setSubtitle(inBlueSearchRecord.getSubtitle())
                .setSubsubtitle(inBlueSearchRecord.getSubsubtitle());
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
        return _toWhatever(",");
    }

    public String toPipe() {
        return _toWhatever("|");
    }

    private String _toWhatever(final String delimiter) {
        try {
            final BlueParts blueParts = BlueParts.of(value);
            if (blueParts == null) {
                log.warn("toCsv: Null BlueParts");
                return null;
            }
            this.normalizedValue = blueParts.getNormalizedBluebook();
            return wrap(title) + delimiter +
                    wrap(subtitle) + delimiter +
                    wrap(subsubtitle) + delimiter +
                    wrap(name) + delimiter +
                    wrap(value) + delimiter +
                    wrap(normalizedValue) + delimiter +
                    wrap(note);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String headerCsv() {
        return _header(",");
    }

    public static String headerPipe() {
        return _header("|");
    }

    private static String _header(final String delimiter) {
        return "title" + delimiter +
                "subtitle" + delimiter +
                "subsubtitle" + delimiter +
                "name" + delimiter +
                "value" + delimiter +
                "normalizedValue" + delimiter +
                "note";
    }

    private String wrap(final String msg) {
        return Strings.isEmpty(msg) ? "\"\"" : "\"" + msg + "\"";
    }
}