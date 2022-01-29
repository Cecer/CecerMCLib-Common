package com.cecer1.projects.mc.cecermclib.common.modules.text;

public class SegmentTextState extends TextState {
    private final String segmentId;
    @Override
    public String getSegmentId() {
        return this.segmentId;
    }

    public SegmentTextState(String segmentId) {
        this.segmentId = segmentId;
    }
}
