package com.pull.law.service;

import com.pull.law.misc.BlueParts;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BluebookParseService {

    public static final String ex1 = "17 Cal. $ 1213.43(a)(e)";

    String FILENAME = "/home/ekamradt/git/SpringGraphQL/tt.txt";


    //@PostConstruct
    public List<BlueParts> init() {
        return readAll(FILENAME);
        //parseThis(ex1);
    }

    private List<BlueParts> readAll(final String filename) {

        try {
            final String content = Files.readString(Path.of(filename));
            final String[] parts = content.split("\\n");
            final List<BlueParts> bluePartsList = Arrays.stream(parts)
                    .map(BlueParts::of)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            bluePartsList.forEach(b -> System.out.println(b.dump()));
            return bluePartsList;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String parseThis(final String line) {

        final String bluePattern = getBluePartTemplate(line);

        return null;
    }

    private String getBluePartTemplate(final String line) {

        final BlueParts pattern = parseIntoBluePart(line);


        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        return null;
    }

    private BlueParts parseIntoBluePart(final String part) {
        if (Strings.isEmpty(part)) {
            return null;
        }


        return null;
    }
}
