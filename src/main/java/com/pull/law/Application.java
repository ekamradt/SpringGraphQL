package com.pull.law;

import com.pull.law.misc.BluePart;
import com.pull.law.misc.BlueParts;
import com.pull.law.misc.LineInfo;
import com.pull.law.service.BluebookParseService;
import com.pull.law.service.PullService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    private final PullService pullService;
    private final BluebookParseService bluebookParseService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void runThis() {
        // Bluebook reference
        final List<LineInfo> infos = pullService.pullAll();
        // Wells Fargo Spreadsheet
        final List<BlueParts> bluePartsList = bluebookParseService.init();

        final BlueParts blueParts = bluePartsList.get(0);

        findBlueParts(bluePartsList, infos);
    }

    // Cal. Const. art. 13A, 1
    // CAL CONST ART 13A, 1
    private void findBlueParts(final List<BlueParts> bluePartList, List<LineInfo> infos) {
        //final String normalizedBluebook = blueParts.getNormalizedBluebook();
        //final String[] split = normalizedBluebook.split(BluePart.SPACE)
        System.out.println("*** Start");
        bluePartList.forEach(blueParts -> {
            final String normalizedBluebook = blueParts.getNormalizedBluebook();
            final IndexPair indexPair = getFirstAlphaPart(blueParts.getPattern());
            final List<String> blueBits = Arrays.asList(normalizedBluebook.split(BluePart.SPACE));
            final List<String> alphaBits = blueBits.subList(indexPair.getIStart(), indexPair.iEnd);
            final String alphaNormalized = String.join(BluePart.SPACE, alphaBits);

            for (final LineInfo info : infos) {
                final String normalized = info.getNormalizedBluebook();
                if (normalized.contains(alphaNormalized)) {
                    showThis(info, blueParts, alphaNormalized);
                }
            }
        });
        System.out.println("*** End");
    }

    private void showThis(final LineInfo info, final BlueParts blueParts, final String alphaNormalized) {
        final String msg = String.format(
                "'%s'  '%s'  '%s'  '%s'  '%s'  :  '%s' '%s' '%s' '%s'"
                , info.getTitle(), info.getSubtitle(), info.getName(), info.getValue(), info.getNormalizedBluebook()
                , blueParts.getOriginalBluebook(), blueParts.getNormalizedBluebook(), alphaNormalized
                , blueParts.getPattern()
        );
        System.out.println(msg);
    }

    public IndexPair getFirstAlphaPart(final String pattern) {
        int iStart = -1;
        int iEnd = -1;
        for (int i = 0; i < pattern.length(); i++) {
            final char ch = pattern.charAt(i);
            if (iStart == -1) {
                if (ch == 'A') {
                    iStart = i;
                }
                continue;
            }
            if (ch != 'A') {
                iEnd = i;
                break;
            }
        }
        if (iStart > -1 && iEnd == -1) {
            iEnd = pattern.length();
        }
        return IndexPair.builder().iStart(iStart).iEnd(iEnd).build();
    }

    @Getter
    @Builder
    public static class IndexPair {
        private int iStart = -1;
        private int iEnd = -1;
    }
}
