package com.pull.law.pullers;

import com.pull.law.misc.LineInfo;
import com.pull.law.misc.ScanResult;
import org.springframework.stereotype.Service;

@Service
public class CourtsPullerBase extends PullerBase {

    public static String TITLE = "Courts";
    private static final String url = "https://law.uakron.libguides.com/bluebook/federalabbreviations";

    @Override
    public ScanResult call() {
        init();
        super.readThis(url);
        return ScanResult.builder().msg("Ok").build();
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
