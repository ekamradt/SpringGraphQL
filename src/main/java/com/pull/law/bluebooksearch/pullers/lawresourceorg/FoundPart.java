package com.pull.law.bluebooksearch.pullers.lawresourceorg;

import com.pull.law.bluebooksearch.misc.BlueSearchRecord;
import lombok.Data;

import java.util.List;

@Data
public class FoundPart {
    private List<BlueSearchRecord> matchedBlueSearchRecords;
    private String alphaNormalized;
}
