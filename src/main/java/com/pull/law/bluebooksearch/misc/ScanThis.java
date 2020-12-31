package com.pull.law.bluebooksearch.misc;

import com.pull.law.bluebooksearch.pullers.uakronlibguides.PullerBase;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ScanThis {
    private final String url;
    private final PullerBase puller;
    private final String trigger;
}
