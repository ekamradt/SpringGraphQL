package com.pull.law.misc;

import com.pull.law.pullers.PullerBase;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ScanThis {
    private final String url;
    private final PullerBase puller;
    private final String trigger;
}
