package com.pull.law.bluebooksearch.service;

import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import com.pull.law.bluebooksearch.pullers.uakronlibguides.AppealCourtPuller;
import com.pull.law.bluebooksearch.pullers.uakronlibguides.CourtsOtherPuller;
import com.pull.law.bluebooksearch.pullers.uakronlibguides.CourtsPullerBase;
import com.pull.law.bluebooksearch.pullers.uakronlibguides.LegalPeriodicalPuller;
import com.pull.law.bluebooksearch.pullers.uakronlibguides.StateCourtPuller;
import com.pull.law.bluebooksearch.pullers.uakronlibguides.SupremeCourtPuller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.pull.law.bluebooksearch.pullers.uakronlibguides.PullerBase.FILENAME;

@Service
@RequiredArgsConstructor
public class PullService {

    private final LegalPeriodicalPuller legalPeriodicalPuller;
    private final CourtsPullerBase courtsPuller;
    private final CourtsOtherPuller courtsOtherPuller;
    private final StateCourtPuller stateCourtPuller;
    private final SupremeCourtPuller supremeCourtPuller;
    private final AppealCourtPuller appealCourtPuller;

    public List<BlueSearchRecord>  pullAll() {

        final File file = new File(FILENAME);
        file.delete();

        final List<BlueSearchRecord> list = appealCourtPuller.call();
        list.addAll(supremeCourtPuller.call());
        list.addAll(stateCourtPuller.call());
        list.addAll(legalPeriodicalPuller.call());
        list.addAll(courtsPuller.call());
        list.addAll(courtsOtherPuller.call());
        return list;
    }
}
