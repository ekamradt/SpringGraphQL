package com.pull.law.bluebooksearch.pullers.uakronlibguides;

import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtsPullerBase extends PullerBase {

    public static String TITLE = "Courts";
    private static final String url = "https://law.uakron.libguides.com/bluebook/federalabbreviations";

    @Override
    public List<BlueSearchRecord> call() {
        init();
        return super.readThis(url);
        //return ScanResult.builder().msg("Ok").build();
    }

    private void init() {
        title = TITLE;

        addSubtitle("United States Supreme Court");
        addSubtitle("United States Court of Appeals");
        addSubtitle("United States District Courts");

        addIgnoreNameValue(title);
        addIgnoreNameValue("Abbreviation");
    }
}
