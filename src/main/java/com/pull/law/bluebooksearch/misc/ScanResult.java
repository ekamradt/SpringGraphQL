package com.pull.law.bluebooksearch.misc;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ScanResult {
    private final Exception e;
    private final String msg;
}
