package com.pull.law.bluebooksearch.service;

import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LineInfoService {

    public List<BlueSearchRecord> buildCustomRecordsToSearch() {
        final List<BlueSearchRecord> blueSearchRecords = new ArrayList<>();

        final String masterCustomTitle = "Custom Master Abbreviations";
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle(null)
                        .setSubsubtitle(null)
                        .setName("Cal. Code Reg.")
                        .setValue("CCR")
                        .setDates(null)
                        .setNormalizedValue("CCR")
                        .setNote("Semi-official")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle(null)
                        .setSubsubtitle(null)
                        .setName("Government")
                        .setValue("GOV")
                        .setDates(null)
                        .setNormalizedValue("GOV")
                        .setNote("Offical abbreviation is \"Gov't\"")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle(null)
                        .setSubsubtitle(null)
                        .setName("Article")
                        .setValue("ARTICLE")
                        .setDates(null)
                        .setNormalizedValue("ARTICLE")
                        .setNote("Offical abbreviation is \"Art. or Arts.\"")
        );
        // ILCS as the abbreviation for all forms of the Illinois Compiled Statutes
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Illinois Compiled Statutes")
                        .setSubsubtitle(null)
                        .setName("Illinois Compiled Statutes")
                        .setValue("ILCS")
                        .setDates(null)
                        .setNormalizedValue("ILCS")
                        .setNote("Semi-offical abbreviation for 'Illinois Compiled Statutes'")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Illinois")
                        .setSubsubtitle("Illinois")
                        .setName("Illinois")
                        .setValue("IL")
                        .setDates(null)
                        .setNormalizedValue("IL")
                        .setNote("Not the official abbreviation for 'Illinois'")
        );
        blueSearchRecords.add(
                new BlueSearchRecord()
                        .setTitle(masterCustomTitle)
                        .setSubtitle("Administrative Code")
                        .setSubsubtitle(null)
                        .setName("Administrative Code")
                        .setValue("ADC")
                        .setDates(null)
                        .setNormalizedValue("ADC")
                        .setNote("Not the offical abbreviation for 'Administrative Code'")
        );
        return blueSearchRecords;
    }

}
