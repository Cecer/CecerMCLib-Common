package com.cecer1.projects.mc.cecermclib.common.modules.text;

import com.cecer1.projects.mc.cecermclib.common.CecerMCLib;
import com.cecer1.projects.mc.cecermclib.common.modules.IModule;
import com.cecer1.projects.mc.cecermclib.common.modules.logger.LoggerModule;
import com.cecer1.projects.mc.cecermclib.common.modules.text.parts.components.AbstractXmlTextComponent;

import javax.xml.stream.XMLStreamException;
import java.util.Collection;
import java.util.function.Consumer;

public class TextModule implements IModule {

    private final ITextAdapter<?> adapter;
    public TextModule(ITextAdapter<?> adapter) {
        this.adapter = adapter;
    }

    public ITextAdapter<?> getAdapter() {
        return this.adapter;
    }
    public WrappedComponent<?> parseXmlText(String xmlString) {
        return this.parseXmlText(xmlString, null);
    }
    public WrappedComponent<?> parseXmlText(String xmlString, Consumer<XMLTextContext> contextAdapter) {
        Collection<AbstractXmlTextComponent> parts;
        try {
            XMLTextContext context = new XMLTextContext();
            if (contextAdapter != null) {
                contextAdapter.accept(context);
            }
            parts = context.parse(xmlString);
        } catch (XMLStreamException e) {
            CecerMCLib.get(LoggerModule.class).getChannel(TextModule.class).log("Failed to parse XML text!", e); // TODO: A better logging system. This should be a warning.
            return null;
        }

        return this.getAdapter().adapt(parts);
    }

    public int getStringWidth(String s) {
        return this.getAdapter().getStringWidth(s);
    }
}

/*

Okay so, here is the plan off the top of my head

• Create a blank interface at `com.cecer1.projects.mc.cecermclib.modules.xmltext.ITextComponent`
• Depending on the environment, inject this as an implemented interface in the environments equivalent IChatComponent
• Each platform would have a converter from XMLText to ITextComponent or something like that.

This allows a common API for XMLText to ITextComponent stuff across all environments. (edited)

As for full colour:
• `ITextComponent` would have a method can exist called `isFullColourSupported()` which'll return a `boolean`.
• `ITextComponent` would have also have a `getFullColourText()` method or something which will return a string similar to how `getFormattedText()` returns the text with formatting codes.
• Environments that do not support this will simply throw an `UnsupportedOperationException`.
• The result from `getFullColourText()` can then potentially be fed into the custom FontRenderer for older version.
• To allow for easy universal support then a `getBestColourText()` method or something can also exist. Essentially just `return isFullColourSupported() ? getFullColour() : getFormattedText()`;

*/
