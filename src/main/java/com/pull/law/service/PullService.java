package com.pull.law.service;

import com.pull.law.pullers.AppealCourtPuller;
import com.pull.law.pullers.CourtsOtherPuller;
import com.pull.law.pullers.CourtsPullerBase;
import com.pull.law.pullers.LegalPeriodicalPuller;
import com.pull.law.pullers.StateCourtPuller;
import com.pull.law.pullers.SupremeCourtPuller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PullService {

    private final LegalPeriodicalPuller legalPeriodicalPuller;
    private final CourtsPullerBase courtsPuller;
    private final CourtsOtherPuller courtsOtherPuller;
    private final StateCourtPuller stateCourtPuller;
    private final SupremeCourtPuller supremeCourtPuller;
    private final AppealCourtPuller appealCourtPuller;

    public void pullAll() {
        appealCourtPuller.call();
        supremeCourtPuller.call();
        stateCourtPuller.call();
        legalPeriodicalPuller.call();
        courtsPuller.call();
        courtsOtherPuller.call();
    }
}
