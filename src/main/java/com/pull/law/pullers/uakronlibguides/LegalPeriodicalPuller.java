package com.pull.law.pullers.uakronlibguides;

import com.pull.law.misc.LineInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalPeriodicalPuller extends PullerBase {


    public static String TITLE = "Legal Periodicals";
    private static final String url = "https://law.uakron.libguides.com/c.php?g=627783&p=4379897";
    private static final String IGNORE_HEADING = "Periodical Name";

    @Override
    public List<LineInfo> call() {
        init();
        return super.readThis(url);
        //return ScanResult.builder().msg("Ok").build();
    }

    private void init() {
        title = TITLE;
        addIgnoreNameValue(title);
        addIgnoreNameValue("Periodical Name");
        addSubtitle(title);
    }
}
