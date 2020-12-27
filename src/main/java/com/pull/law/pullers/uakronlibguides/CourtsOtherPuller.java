package com.pull.law.pullers.uakronlibguides;

import com.pull.law.misc.LineInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtsOtherPuller extends PullerBase {

    public static String TITLE = "Courts";
    private static final String url = "https://law.uakron.libguides.com/c.php?g=627783&p=4379902";

    // Bankruptcy Courts
    // Every United States Court District has its own bankruptcy court.
    //      Simply add "Bankr." preceding the district court abbreviations.

    @Override
    public List<LineInfo> call() {
        init();
        return super.readThis(url);
        //return ScanResult.builder().msg("Ok").build();
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
