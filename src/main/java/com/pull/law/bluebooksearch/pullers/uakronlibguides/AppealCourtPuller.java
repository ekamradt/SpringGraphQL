package com.pull.law.bluebooksearch.pullers.uakronlibguides;

import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppealCourtPuller extends PullerBase {

    public static String TITLE = "Court of Appeal";
    public static String SUB_TITLE = TITLE;
    private static final String url = "https://law.uakron.libguides.com/c.php?g=627783&p=4379901";

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
        addIgnoreNameValue("Court of Appeals Abbreviation");
    }
}
