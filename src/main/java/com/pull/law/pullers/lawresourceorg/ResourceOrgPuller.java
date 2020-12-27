package com.pull.law.pullers.lawresourceorg;

import com.pull.law.misc.LineInfo;
import com.pull.law.pullers.uakronlibguides.PullerBase;

import java.util.List;

public class ResourceOrgPuller extends PullerBase {

    public static String TITLE = "Court of Appeal";
    public static String SUB_TITLE = TITLE;
    private static final String url = "https://law.resource.org/pub/us/code/blue/src/BabyBlue.20160205.html7";

    // <h3
    // <tbody
    // <tr
    // <td> ... </td>


    // T1 -- 3 td
    // T2 -- 3 td
        // <tr class="state"><td colspan="3">North Carolina</td></tr>
        // <tr class="item"><td colspan="3"><b>Supreme Court (N.C.):</b> Cite to S.E. or S.E.2d.</td></tr>
        // <tr class="item"><td colspan="3"><b>Court of Appeals (N.C. Ct. App.):</b> Cite to S.E.2d.</td></tr>
        // <tr class="item"><td colspan="3"><b>Statutory compilations:</b> Cite to N.C. Gen. Stat. (published by LexisNexis).</td></tr>
    // T3 - 2 td
    // T4 - 2 td
    // T5 - 2 td
    // T6 - 2 td
    // T7
    // T7.1 - 3 td
    // T7.2 - 3 td
    // T7.3 - 2 td
    // T7.4 - 2 td
    // T7.4 - 2 td
    // T8.1 - 2 td
    // T8.2 - 2 td
    // T9 - 3 td
    // T10.1/2/3 - 2 td
    // T11 - 2 td
    // T12 - 2 td
    // T13.1/2 - 2 td
    // T14 - 2 td
    // T15 - 1 td : If a phrase is followed a case name as the direct object, the comma should be omitted.
    // T16 - 2d
    


    @Override
    public List<LineInfo> call() {
        init();
        return super.readThis(url);
        //return ScanResult.builder().msg("Ok").build();
    }

    private void init() {
        title = TITLE;
        addSubtitle(SUB_TITLE);
        //addIgnoreNameValue("State");
        //addIgnoreNameValue("Court of Appeals Abbreviation");
    }
}
