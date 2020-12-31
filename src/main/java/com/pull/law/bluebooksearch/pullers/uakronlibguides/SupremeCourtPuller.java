package com.pull.law.bluebooksearch.pullers.uakronlibguides;

import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupremeCourtPuller extends PullerBase {

    public static String TITLE = "Supreme Courts";
    public static String SUB_TITLE = "State Supreme Court";
    private static final String url = "https://law.uakron.libguides.com/c.php?g=627783&p=4379900";

    @Override
    public List<BlueSearchRecord> call() {
        init();
        return super.readThis(url);
        //return ScanResult.builder().msg("Ok").build();
    }

    private void init() {
        title = TITLE;
        addSubtitle(SUB_TITLE);
        addIgnoreNameValue("State");
        addIgnoreNameValue("Supreme Court Abbreviations (or court of last resort)");
    }
}
