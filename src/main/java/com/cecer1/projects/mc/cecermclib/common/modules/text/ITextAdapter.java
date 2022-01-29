package com.cecer1.projects.mc.cecermclib.common.modules.text;

import com.cecer1.projects.mc.cecermclib.common.modules.text.parts.components.AbstractXmlTextComponent;
import com.cecer1.projects.mc.cecermclib.common.modules.text.parts.components.TextXmlTextComponent;

import java.util.Collection;

public interface ITextAdapter<TRoot> {

    WrappedComponent<TRoot> newRootComponent();

    default WrappedComponent<TRoot> adapt(Collection<AbstractXmlTextComponent> parts) {
        WrappedComponent<TRoot> root = this.newRootComponent();

        for (AbstractXmlTextComponent part : parts) {
            part.adaptPart(this, root);
        }

        return root;
    }

    void appendPart(WrappedComponent<TRoot> root, TextXmlTextComponent part);

    int getStringWidth(String s);

    int getFontHeight();
}
