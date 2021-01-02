package com.pull.law.bluebooksearch.pullers.lawresourceorg;

import com.pull.law.bluebooksearch.misc.BlueParts;
import com.pull.law.bluebooksearch.misc.LimitType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FoundParts {
    private List<FoundPart> foundParts = new ArrayList<>();
    private boolean matchFound = false;
    private BlueParts blueParts;
    private LimitType limitType = LimitType.ANY;
    private String state = null;
    private Throwable throwable;

    public void addFoundPart(final FoundPart foundPart) {
        foundParts.add(foundPart);
    }
}
