package com.pull.law.bluebooksearch.pullers.lawresourceorg;

import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceOrgPuller extends PullerBase2 {

    public static String TITLE = "Court of Appeal";
    public static String SUB_TITLE = TITLE;
    private static final String url = "https://law.resource.org/pub/us/code/blue/src/BabyBlue.20160205.html#R11";

    // <h3
    // <tbody
    // <tr
    // <td> ... </td>

    // START: <h4 id="T1.2" / class="indent"
    // END : </tbody>

    // h3 == title
    // h4 == subtitle
    // colspan="3" == subsubtitle

    @Override
    public List<BlueSearchRecord> call() {
        init();
        final List<BlueSearchRecord> blueSearchRecords = super.readThis(url);

        return blueSearchRecords;
        // return ScanResult.builder().msg("Ok").build();
    }

    private void init() {

        final File file = new File(OUTPUT_FILE);
        file.delete();

        title = TITLE;
        String endGroup = "";
        String startLine = "";
        addSubtitle(SUB_TITLE);

        startLine = "<h4 id=\"T1.2\"";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup("</tbody>")
                        .titleGroup(startLine)
                        .removeFromTitle("T1.2.")
                        .nameValueGroup("class=\"indent\"")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );
        startLine = "<h3 id=\"T2\"";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup("</tbody>")
                        .titleGroup(startLine)
                        .removeFromTitle("T2.")
                        .subtitleGroup("class=\"state\"")
                        .subsubtitleGroup("class=\"item\"")
                        .nameValueGroup("<tr><td")
                        .nameValueProcessType(ProcessType.TD_THREE)
                        .build()
        );
        startLine = "<h3 id=\"T3\"";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup("</tbody>")
                        .removeFromTitle("T3.")
                        .titleGroup(startLine)
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );
        startLine = "<h3 id=\"T4\"";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup("</tbody>")
                        .removeFromTitle("T4.")
                        .titleGroup(startLine)
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );
        startLine = "<h3 id=\"T5\"";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup("</tbody>")
                        .removeFromTitle("T5.")
                        .titleGroup(startLine)
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );
        startLine = "<h3 id=\"T6\"";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup("</tbody>")
                        .removeFromTitle("T6.")
                        .titleGroup(startLine)
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );
        startLine = "<h3 id=\"T7\"";
        endGroup = "<h3 id=\"T8\">";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup(endGroup)
                        .removeFromTitle("T7.")
                        .titleGroup(startLine)
                        .subtitleGroup("<h4 id=\"T7\\d+\"")
                        .removeFromSubtitle("T7\\.\\d+\\.")
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_THREE)
                        .build()
        );
        startLine = "<h3 id=\"T8\">";
        endGroup = "<h3 id=\"T9\">";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup(endGroup)
                        .removeFromTitle("T8.")
                        .titleGroup(startLine)
                        .subtitleGroup("<h4 id=\"T8.")
                        .removeFromSubtitle("T8\\.\\d+\\.")
                        .subsubtitleGroup("<tr><td><i>")
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );
        startLine = "<h3 id=\"T9\">";
        endGroup = "</table>";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup(endGroup)
                        .removeFromTitle("T9.")
                        .titleGroup(startLine)
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_THREE)
                        .build()
        );
        startLine = "<h3 id=\"T10\"";
        endGroup = "</table>";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup(endGroup)
                        .removeFromTitle("T10.")
                        .removeFromSubtitle("T10.\\d+\\.")
                        .titleGroup(startLine)
                        .subtitleGroup("<h4 id=\"T10.")
                        .subsubtitleGroup("<tr><td><b>")
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );
        startLine = "<h3 id=\"T11\"";
        endGroup = "</table>";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup(endGroup)
                        .removeFromTitle("T11.")
                        .removeFromSubtitle("T11.")
                        .titleGroup(startLine)
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );
        startLine = "<h3 id=\"T12\"";
        endGroup = "</table>";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup(endGroup)
                        .removeFromTitle("T12.")
                        .titleGroup(startLine)
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );
        startLine = "<h3 id=\"T13\"";
        endGroup = "<h3 id=\"T14\"";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup(endGroup)
                        .removeFromTitle("T13.")
                        .removeFromSubtitle("T13.\\d+\\.")
                        .titleGroup(startLine)
                        .subtitleGroup("<h4 id=\"T13.")
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );
        startLine = "<h3 id=\"T14\"";
        endGroup = "</table>";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup(endGroup)
                        .removeFromTitle("T14.")
                        .titleGroup(startLine)
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );
        startLine = "<h3 id=\"T16\"";
        endGroup = "</table>";
        pullGroups.add(
                PullGroup.builder()
                        .begGroup(startLine)
                        .endGroup(endGroup)
                        .removeFromTitle("T16.")
                        .titleGroup(startLine)
                        .nameValueGroup("<tr><td>")
                        .nameValueProcessType(ProcessType.TD_TWO)
                        .build()
        );

        pullGroups.add(PullGroup.notAMatchGroup());
    }
}
