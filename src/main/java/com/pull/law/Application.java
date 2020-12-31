package com.pull.law;

import com.pull.law.bluebooksearch.misc.BlueParts;
import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import com.pull.law.bluebooksearch.pullers.lawresourceorg.ResourceOrgPuller;
import com.pull.law.bluebooksearch.service.BluebookParseService;
import com.pull.law.bluebooksearch.service.BluebookSearchService;
import com.pull.law.bluebooksearch.service.LineInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;


@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    public static final String SYMBOL_SESSION = "§";

    /// private final PullService pullService;
    private final BluebookParseService bluebookParseService;
    private final ResourceOrgPuller resourceOrgPuller;
    private final LineInfoService lineInfoService;
    private final BluebookSearchService bluebookSearchService;

    private final ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run();
    }

    @PostConstruct
    public void runThis() {
        final List<BlueSearchRecord> recordsToSearch = resourceOrgPuller.call();
        //showLineInfos(infos);

        recordsToSearch.addAll(lineInfoService.buildCustomRecordsToSearch());

        System.out.println("*** END ***");
        System.out.println("*** END ***");
        System.out.println("*** END ***");

        // Bluebook reference
        // final List<LineInfo> infos = pullService.pullAll();

        // Wells Fargo Spreadsheet
        final List<BlueParts> bluePartsList = bluebookParseService.init();
        bluebookSearchService.findBlueParts(bluePartsList, recordsToSearch);
        exitExit();
    }

    private void out(final String msg) {
        System.out.println(msg);
    }

    private void showThis(final BlueSearchRecord info, final BlueParts blueParts, final String alphaNormalized) {
        final String msg = String.format(
                "'%s'  '%s'  '%s'  '%s'  '%s'  :  '%s' '%s' '%s' '%s'"
                , info.getTitle(), info.getSubtitle(), info.getName(), info.getValue(), info.getNormalizedValue()
                , blueParts.getOriginalBluebook(), blueParts.getNormalizedBluebook(), alphaNormalized
                , blueParts.getPattern()
        );
        System.out.println(msg);
    }

    // ====================================================================
    private void exitExit() {
        int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
            @Override
            public int getExitCode() {
                // return the error code
                return 0;
            }
        });
        System.exit(exitCode);
    }
}
