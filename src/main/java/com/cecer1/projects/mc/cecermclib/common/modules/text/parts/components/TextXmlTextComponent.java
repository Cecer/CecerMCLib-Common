package com.cecer1.projects.mc.cecermclib.common.modules.text.parts.components;

import com.cecer1.projects.mc.cecermclib.common.modules.text.ITextAdapter;
import com.cecer1.projects.mc.cecermclib.common.modules.text.TextState;
import com.cecer1.projects.mc.cecermclib.common.modules.text.WrappedComponent;

public class TextXmlTextComponent extends AbstractXmlTextComponent {
    private final String text;
    public String getText() {
        return this.text;
    }

    public TextXmlTextComponent(TextState state, String text) {
        super(state);
        this.text = text;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void adaptPart(ITextAdapter adapter, WrappedComponent root) {
        adapter.appendPart(root, this);
    }
}
