package com.pull.law.pullers;

import com.pull.law.misc.ScanResult;
import org.springframework.stereotype.Service;

@Service
public class StateCourtPuller extends PullerBase {

    public static String TITLE = "State Courts";
    private static final String url = "https://law.uakron.libguides.com/c.php?g=627783&p=4379899";

    @Override
    public ScanResult call() {
        init();
        super.readThis(url);
        return ScanResult.builder().msg("Ok").build();
    }

    private void init() {
        title = TITLE;
        addSubtitle(title);
        addIgnoreNameValue("Court");
        addIgnoreNameValue("Abbreviation");
    }
}
