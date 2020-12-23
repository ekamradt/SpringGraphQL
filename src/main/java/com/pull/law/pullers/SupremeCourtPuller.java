package com.pull.law.pullers;

import com.pull.law.misc.ScanResult;
import org.springframework.stereotype.Service;

@Service
public class SupremeCourtPuller extends PullerBase {

    public static String TITLE = "Supreme Courts";
    public static String SUB_TITLE = "State Supreme Court";
    private static final String url = "https://law.uakron.libguides.com/c.php?g=627783&p=4379900";

    @Override
    public ScanResult call() {
        init();
        super.readThis(url);
        return ScanResult.builder().msg("Ok").build();
    }

    private void init() {
        title = TITLE;
        addSubtitle(SUB_TITLE);
        addIgnoreNameValue("State");
        addIgnoreNameValue("Supreme Court Abbreviations (or court of last resort)");
    }
}
