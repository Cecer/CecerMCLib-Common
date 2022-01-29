package com.cecer1.projects.mc.cecermclib.common.modules.text.parts.components;

import com.cecer1.projects.mc.cecermclib.common.modules.text.ITextAdapter;
import com.cecer1.projects.mc.cecermclib.common.modules.text.TextColor;
import com.cecer1.projects.mc.cecermclib.common.modules.text.TextState;
import com.cecer1.projects.mc.cecermclib.common.modules.text.WrappedComponent;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractXmlTextComponent {
    private final TextState state;
    public TextState getState() {
        return this.state;
    }

    protected AbstractXmlTextComponent(TextState state) {
        this.state = state;
    }

    public static Collection<TextXmlTextComponent> fromLegacyText(TextState baseState, String legacyText) {
        Collection<TextXmlTextComponent> parts = new ArrayList<>();
        String[] splitText = legacyText.split("\u00a7");

        parts.add(new TextXmlTextComponent(baseState, splitText[0]));

        TextState state = baseState;
        for (int i = 1; i < splitText.length; i++) {
            switch (splitText[i].charAt(0)) {
                case '0': {
                    state = state.newChild().setColor(TextColor.BLACK);
                    break;
                }
                case '1': {
                    state = state.newChild().setColor(TextColor.DARK_BLUE);
                    break;
                }
                case '2': {
                    state = state.newChild().setColor(TextColor.DARK_GREEN);
                    break;
                }
                case '3': {
                    state = state.newChild().setColor(TextColor.DARK_AQUA);
                    break;
                }
                case '4': {
                    state = state.newChild().setColor(TextColor.DARK_RED);
                    break;
                }
                case '5': {
                    state = state.newChild().setColor(TextColor.DARK_PURPLE);
                    break;
                }
                case '6': {
                    state = state.newChild().setColor(TextColor.GOLD);
                    break;
                }
                case '7': {
                    state = state.newChild().setColor(TextColor.GRAY);
                    break;
                }
                case '8': {
                    state = state.newChild().setColor(TextColor.DARK_GRAY);
                    break;
                }
                case '9': {
                    state = state.newChild().setColor(TextColor.BLUE);
                    break;
                }
                case 'a': {
                    state = state.newChild().setColor(TextColor.GREEN);
                    break;
                }
                case 'b': {
                    state = state.newChild().setColor(TextColor.AQUA);
                    break;
                }
                case 'c': {
                    state = state.newChild().setColor(TextColor.RED);
                    break;
                }
                case 'd': {
                    state = state.newChild().setColor(TextColor.LIGHT_PURPLE);
                    break;
                }
                case 'e': {
                    state = state.newChild().setColor(TextColor.YELLOW);
                    break;
                }
                case 'f': {
                    state = state.newChild().setColor(TextColor.WHITE);
                    break;
                }
                case 'l': {
                    state = state.newChild().setBold(true);
                    break;
                }
                case 'o': {
                    state = state.newChild().setItalic(true);
                    break;
                }
                case 'n': {
                    state = state.newChild().setUnderlined(true);
                    break;
                }
                case 'm': {
                    state = state.newChild().setStrikethrough(true);
                    break;
                }
                case 'k': {
                    state = state.newChild().setObfuscated(true);
                    break;
                }
                case 'r': {
                    state = baseState;
                }
                default: {
                }
            }
            if (splitText[i].length() >= 1) {
                parts.add(new TextXmlTextComponent(state, splitText[i].substring(1)));
            }
        }
        return parts;
    }

    @SuppressWarnings({"rawtypes"})
    public abstract void adaptPart(ITextAdapter adapter, WrappedComponent root);
}
