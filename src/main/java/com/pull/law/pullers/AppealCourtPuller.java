package com.pull.law.pullers;

import com.pull.law.misc.ScanResult;
import org.springframework.stereotype.Service;

@Service
public class AppealCourtPuller extends PullerBase {

    public static String TITLE = "Court of Appeal";
    public static String SUB_TITLE = TITLE;
    private static final String url = "https://law.uakron.libguides.com/c.php?g=627783&p=4379901";

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
        addIgnoreNameValue("Court of Appeals Abbreviation");
    }
}
