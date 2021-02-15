package com.pull.law.bluebook.service;

import com.pull.law.bluebook.misc.BluePieces;
import com.pull.law.bluebook.spreadsheet.FedInvComForm;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BluebookParseService {

    public static final String ex1 = "17 Cal. $ 1213.43(a)(e)";

    // String FILENAME = "/home/ekamradt/git/SpringGraphQL/WellsFarge_Abbr_001.txt";
    // String FILENAME = "/home/ekamradt/git/SpringGraphQL/WellsFarge_Abbr_002.txt";
    String FILENAME = "/home/ekamradt/git/SpringGraphQL/src/main/resources/FedInvComForm922020.csv";

    //@PostConstruct
    public List<FedInvComForm> initForm01() {
        //return readAll(FILENAME);
        return readAllIntoStruct01(FILENAME);
    }

    private Collection<? extends BluePieces> buildKnowAbbreviations() {
        final List<BluePieces> bluePartList = new ArrayList<>();
        return bluePartList;
    }

    private List<FedInvComForm> readAllIntoStruct01(final String filename) {
        try {
            final String content = Files.readString(Path.of(filename));
            final String[] parts = content.split("\\n");
            final List<FedInvComForm> formList = Arrays.stream(parts)
                    .map(FedInvComForm::fromPipedLine)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // bluePartsList.forEach(b -> System.out.println(b.dump()));
            return formList;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<BluePieces> readAll(final String filename) {

        try {
            final String content = Files.readString(Path.of(filename));
            final String[] parts = content.split("\\n");
            final List<BluePieces> bluePiecesList = Arrays.stream(parts)
                    .map(BluePieces::of)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            //// bluePartsList.forEach(b -> System.out.println(b.dump()));
            return bluePiecesList;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String parseThis(final String line) {

        final String bluePattern = getBluePartTemplate(line);

        return null;
    }

    private String getBluePartTemplate(final String line) {

        final BluePieces pattern = parseIntoBluePart(line);


        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        return null;
    }

    private BluePieces parseIntoBluePart(final String part) {
        if (Strings.isEmpty(part)) {
            return null;
        }


        return null;
    }
}
