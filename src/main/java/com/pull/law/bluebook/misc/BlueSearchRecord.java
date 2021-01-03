package com.pull.law.bluebook.misc;

import com.pull.law.bluebook.helpers.ListHelper;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Data
@Accessors(chain = true)
public class BlueSearchRecord {
    private String title = "";
    private String subtitle = "";
    private String subsubtitle = "";
    private String name = "";
    private BlueParts blueParts;
    private String startDate = "";
    private String endDate = "";
    private String note = "";
    private String urlAddress = "";

    public static BlueSearchRecord copy(final BlueSearchRecord inBlueSearchRecord) {
        return new BlueSearchRecord()
                .setTitle(inBlueSearchRecord.getTitle())
                .setSubtitle(inBlueSearchRecord.getSubtitle())
                .setSubsubtitle(inBlueSearchRecord.getSubsubtitle())
                .setName(inBlueSearchRecord.getName())
                .setStartDate(inBlueSearchRecord.getStartDate())
                .setEndDate(inBlueSearchRecord.getEndDate())
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
                    wrap(blueParts.getNormalizedBluebook()) + delimiter +
                    wrap(startDate) + delimiter +
                    wrap(endDate) + delimiter +
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
                "bluebook_original" + delimiter +
                "bluebook_normalized" + delimiter +
                "start_date" + delimiter +
                "end_date" + delimiter +
                "note";
    }

    private String wrap(final String msg) {
        return msg;
        // return Strings.isEmpty(msg) ? "\"\"" : "\"" + msg + "\"";
    }

    public static String creatOracleTable() {
        final StringBuilder sb = new StringBuilder();
        sb
                .append("DROP TABLE law_sync.CITATION_LOOKUP; \n")
                .append("CREATE TABLE law_sync.CITATION_LOOKUP ( \n")
                .append("  id                  INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY \n")
                .append(", title               varchar(4000) NOT NULL \n")
                .append(", subtitle            varchar(4000) \n")
                .append(", subsubtitle         varchar(4000) \n")
                .append(", name                varchar(4000) \n")
                .append(", abbrev_original     varchar(4000) NOT NULL \n")
                .append(", abbrev_normalized   varchar(4000) \n")
                .append(", start_date          varchar(128) \n")
                .append(", end_date            varchar(128) \n")
                .append(", note                varchar(4000) \n")
                .append("); \n\n")
        ;
        return sb.toString();
    }

    private static String wrapSingleQuote(final String msg) {
        if (Strings.isEmpty(msg)) {
            return "''";
        }
        String tempMsg = msg.replace("'", "''");
        tempMsg = tempMsg.replace("&", " and ");
        tempMsg = tempMsg.replaceAll("\\s+", " ");
        return "'" + tempMsg + "'";
    }

    public static String buildOracleInserts(final List<BlueSearchRecord> recs) {
        final StringBuilder sb = new StringBuilder();
        if (ListHelper.isEmptyOrNull(recs)) {
            throw new IllegalArgumentException("Can not build Oracle data for empty List.");
        }
        final AtomicInteger i = new AtomicInteger(0);
        for (final BlueSearchRecord rec : recs) {
            sb // Do not list 'id', oracle will fill it in
                    .append("INSERT ")
                    .append("INTO law_sync.citation_lookup( title, subtitle, subsubtitle, name, " +
                            "abbrev_original, abbrev_normalized, start_date, end_date, note ) \n")
                    .append(" VALUES (")
                    .append(wrapSingleQuote(rec.title)).append(", ")
                    .append(wrapSingleQuote(rec.subtitle)).append(", ")
                    .append(wrapSingleQuote(rec.subsubtitle)).append(", ")
                    .append(wrapSingleQuote(rec.name)).append(", ")
                    .append(wrapSingleQuote(rec.blueParts.getOriginalBluebook())).append(", ")
                    .append(wrapSingleQuote(rec.blueParts.getNormalizedBluebook())).append(", ")
                    .append(wrapSingleQuote(rec.startDate)).append(", ")
                    .append(wrapSingleQuote(rec.endDate)).append(", ")
                    .append(wrapSingleQuote(rec.note)).append(");\n ")
            ;
        }
        return sb.toString();
    }
}