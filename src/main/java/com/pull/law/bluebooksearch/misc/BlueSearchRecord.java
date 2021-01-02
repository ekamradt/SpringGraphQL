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
    private BlueParts blueParts;
    private String dates = "";
    private String note = "";
    private String urlAddress = "";

    public static BlueSearchRecord copy(final BlueSearchRecord inBlueSearchRecord) {
        return new BlueSearchRecord()
                .setTitle(inBlueSearchRecord.getTitle())
                .setSubtitle(inBlueSearchRecord.getSubtitle())
                .setSubsubtitle(inBlueSearchRecord.getSubsubtitle())
                .setName(inBlueSearchRecord.getName())
                .setDates(inBlueSearchRecord.getDates())
                .setBlueParts((inBlueSearchRecord.getBlueParts()))
                .setNote(inBlueSearchRecord.getNote());
    }

    public static BlueSearchRecord copyTitles(final BlueSearchRecord inBlueSearchRecord) {
        return new BlueSearchRecord()
                .setTitle(inBlueSearchRecord.getTitle())
                .setSubtitle(inBlueSearchRecord.getSubtitle())
                .setSubsubtitle(inBlueSearchRecord.getSubsubtitle());
    }

    public BlueSearchRecord setValue(final String value) {
        if (value != null) {
            final BlueParts tempBlueParts = BlueParts.of(value);
            if (tempBlueParts != null) {
                this.blueParts = tempBlueParts;
            }
        }
        return this;
    }

    public String toCsv() {
        return _toWhatever(",");
    }

    public String toPipe() {
        return _toWhatever("|");
    }

    private String _toWhatever(final String delimiter) {
        try {
            if (blueParts == null) {
                return null;
            }
            return wrap(title) + delimiter +
                    wrap(subtitle) + delimiter +
                    wrap(subsubtitle) + delimiter +
                    wrap(name) + delimiter +
                    wrap(blueParts.getOriginalBluebook()) + delimiter +
                    wrap(dates) + delimiter +
                    wrap(blueParts.getNormalizedBluebook()) + delimiter +
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
                "dates" + delimiter +
                "normalizedValue" + delimiter +
                "note";
    }

    private String wrap(final String msg) {
        return Strings.isEmpty(msg) ? "\"\"" : "\"" + msg + "\"";
    }
}