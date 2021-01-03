package com.pull.law.bluebook.pullers.lawresourceorg;

import com.pull.law.bluebook.misc.BlueSearchRecord;
import lombok.Data;

import java.util.List;

@Data
public class FoundPart {
    private List<BlueSearchRecord> matchedBlueSearchRecords;
    private String alphaNormalized;
}
