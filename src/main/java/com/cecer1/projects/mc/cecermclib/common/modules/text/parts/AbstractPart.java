package com.cecer1.projects.mc.cecermclib.common.modules.text.parts;

import com.cecer1.projects.mc.cecermclib.common.modules.text.TextState;

public abstract class AbstractPart {
    private final TextState state;
    public TextState getState() {
        return this.state;
    }

    public AbstractPart(TextState state) {
        this.state = state;
    }
}
