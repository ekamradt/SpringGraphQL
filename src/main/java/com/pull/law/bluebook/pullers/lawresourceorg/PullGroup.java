package com.pull.law.bluebook.pullers.lawresourceorg;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PullGroup {
    public static final String NOT_A_MATCH = "*** NOT A MATCH ***";

    private final String begGroup;
    private final String endGroup;
    private final String titleGroup;
    private final String removeFromTitle;
    private final String removeFromSubtitle;
    private final String subtitleGroup;
    private final String subsubtitleGroup;
    private final String nameValueGroup;
    private final ProcessType nameValueProcessType;

    private final String removeIfExists = "Required Abbreviations for";

    public static PullGroup notAMatchGroup(){
        return PullGroup.builder()
                .begGroup(NOT_A_MATCH)
                .endGroup(NOT_A_MATCH)
                .titleGroup(NOT_A_MATCH)
                .removeFromTitle(NOT_A_MATCH)
                .removeFromSubtitle(NOT_A_MATCH)
                .subtitleGroup(NOT_A_MATCH)
                .subsubtitleGroup(NOT_A_MATCH)
                .nameValueGroup(NOT_A_MATCH)
                .build();
    }
}
