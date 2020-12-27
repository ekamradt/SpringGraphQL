package com.pull.law.service;

import com.pull.law.misc.LineInfo;
import com.pull.law.pullers.uakronlibguides.AppealCourtPuller;
import com.pull.law.pullers.uakronlibguides.CourtsOtherPuller;
import com.pull.law.pullers.uakronlibguides.CourtsPullerBase;
import com.pull.law.pullers.uakronlibguides.LegalPeriodicalPuller;
import com.pull.law.pullers.uakronlibguides.StateCourtPuller;
import com.pull.law.pullers.uakronlibguides.SupremeCourtPuller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.pull.law.pullers.uakronlibguides.PullerBase.FILENAME;

@Service
@RequiredArgsConstructor
public class PullService {

    private final LegalPeriodicalPuller legalPeriodicalPuller;
    private final CourtsPullerBase courtsPuller;
    private final CourtsOtherPuller courtsOtherPuller;
    private final StateCourtPuller stateCourtPuller;
    private final SupremeCourtPuller supremeCourtPuller;
    private final AppealCourtPuller appealCourtPuller;

    public List<LineInfo>  pullAll() {

        final File file = new File(FILENAME);
        file.delete();


        final List<LineInfo> list = appealCourtPuller.call();
        list.addAll(supremeCourtPuller.call());
        list.addAll(stateCourtPuller.call());
        list.addAll(legalPeriodicalPuller.call());
        list.addAll(courtsPuller.call());
        list.addAll(courtsOtherPuller.call());
        return list;
    }
}
