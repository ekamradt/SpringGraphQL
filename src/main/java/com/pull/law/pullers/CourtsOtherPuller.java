package com.pull.law.pullers;

import com.pull.law.misc.LineInfo;
import com.pull.law.misc.ScanResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class CourtsOtherPuller extends PullerBase {

    public static String TITLE = "Courts";
    private static final String url = "https://law.uakron.libguides.com/c.php?g=627783&p=4379902";

    // Bankruptcy Courts
    // Every United States Court District has its own bankruptcy court.
    //      Simply add "Bankr." preceding the district court abbreviations.

    @Override
    public ScanResult call() {
        init();
        super.readThis(url);
        return ScanResult.builder().msg("Ok").build();
    }

    private void init() {
        title = TITLE;
        addSubtitle("Military Courts");
        addSubtitle("Bankruptcy Courts");
        addSubtitle("Additional Federal Courts");

        addIgnoreNameValue("Example");
        addIgnoreNameValue("Court");
        addIgnoreNameValue("Bankruptcy Court Abbreviation");
        addIgnoreNameValue("Abbreviation");
    }
}