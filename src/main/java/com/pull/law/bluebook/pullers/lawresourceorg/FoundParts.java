package com.pull.law.bluebook.pullers.lawresourceorg;

import com.pull.law.bluebook.misc.BlueParts;
import com.pull.law.bluebook.misc.BlueFormatType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FoundParts {
    private List<FoundPart> foundParts = new ArrayList<>();
    private boolean matchFound = false;
    private BlueParts blueParts;
    private BlueFormatType blueFormatType = BlueFormatType.ANY;
    private String state = null;
    private Throwable throwable;

    public void addFoundPart(final FoundPart foundPart) {
        foundParts.add(foundPart);
    }
}
