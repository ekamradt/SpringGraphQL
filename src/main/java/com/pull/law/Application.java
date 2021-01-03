package com.pull.law;

import com.pull.law.bluebook.misc.BlueSearchRecord;
import com.pull.law.bluebook.pullers.lawresourceorg.ResourceOrgPuller;
import com.pull.law.bluebook.service.BlueSearchRecordService;
import com.pull.law.bluebook.service.BluebookParseService;
import com.pull.law.bluebook.service.BluebookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    /// private final PullService pullService;
    private final BluebookParseService bluebookParseService;
    private final ResourceOrgPuller resourceOrgPuller;
    private final BlueSearchRecordService blueSearchRecordService;
    private final BluebookSearchService bluebookSearchService;

    private final ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run();
    }

    @PostConstruct
    public void runThis() {
        final List<BlueSearchRecord> recordsToSearch = resourceOrgPuller.call();

        recordsToSearch.addAll(blueSearchRecordService.buildCustomRecordsToSearch());

        recordsToSearch.forEach(rec -> {
            System.out.println(rec.toPipe());
        });

        System.out.println("*** END ***");
        System.out.println("*** END ***");
        System.out.println("*** END ***");

        writeOracleTable(recordsToSearch);

        //        // Wells Fargo Spreadsheet 01
        //        final List<FedInvComForm> forms = bluebookParseService.initForm01();
        //        if (forms != null) {
        //            final List<BlueParts> bluePartsList =
        //                    forms.stream().map(FedInvComForm::getBlueParts).collect(Collectors.toList());
        //            bluebookSearchService.findBlueParts(bluePartsList, recordsToSearch);
        //
        //            String content = FedInvComForm.headerPipe() + "\n";
        //
        //            content = content + forms.stream()
        //                    .filter(form -> !form.found())
        //                    .map(FedInvComForm::toPipe)
        //                    .collect(Collectors.joining("\n"));
        //
        //            content = content + forms.stream()
        //                    .filter(FedInvComForm::found)
        //                    .map(FedInvComForm::toPipe)
        //                    .collect(Collectors.joining("\n"));
        //
        //            writeToFile("FedResult01.txt", content);
        //        }
        exitExit();
    }

    private void writeOracleTable(final List<BlueSearchRecord> recordsToSearch) {
        final String createTable = BlueSearchRecord.creatOracleTable();
        final String inserts = BlueSearchRecord.buildOracleInserts(recordsToSearch);
        writeToFile("CREATE_CITATION_LOOKUP.sql", createTable + inserts);
    }

    private void writeToFile(final String filename, final String content) {
        final Path path = Path.of(filename);
        try {
            Files.writeString(path, content);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
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
