package com.cecer1.projects.mc.cecermclib.common.modules.text;

import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;

import java.util.HashMap;
import java.util.Map;

public class TextColor {
    public static final TextColor BLACK = new TextColor("black", '0');
    public static final TextColor DARK_BLUE = new TextColor("dark_blue", '1');
    public static final TextColor DARK_GREEN = new TextColor("dark_blue", '2');
    public static final TextColor DARK_AQUA = new TextColor("dark_aqua", '3');
    public static final TextColor DARK_RED = new TextColor("dark_red", '4');
    public static final TextColor DARK_PURPLE = new TextColor("dark_purple", '5');
    public static final TextColor GOLD = new TextColor("gold", '6');
    public static final TextColor GRAY = new TextColor("gray", '7');
    public static final TextColor DARK_GRAY = new TextColor("dark_gray", '8');
    public static final TextColor BLUE = new TextColor("blue", '9');
    public static final TextColor GREEN = new TextColor("green", 'a');
    public static final TextColor AQUA = new TextColor("aqua", 'b');
    public static final TextColor RED = new TextColor("red", 'c');
    public static final TextColor LIGHT_PURPLE = new TextColor("light_purple", 'd');
    public static final TextColor YELLOW = new TextColor("yellow", 'e');
    public static final TextColor WHITE = new TextColor("white", 'f');
    
    private static final TextColor[] legacyColors = new TextColor[] {
            BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA,
            DARK_RED, DARK_PURPLE, GOLD, GRAY,
            DARK_GRAY, BLUE, GREEN, AQUA,
            RED, LIGHT_PURPLE, YELLOW, WHITE
    };
    private static final Map<String, TextColor> valueLookup = new HashMap<>();
    private static final Char2ObjectMap<TextColor> legacyLookup = new Char2ObjectArrayMap<>();
    static {
        for (TextColor color : legacyColors) {
            valueLookup.put(color.value, color);
            legacyLookup.put(color.legacyChar, color);
        }
    }
    public static TextColor fromName(String name) {
        return valueLookup.get(name);
    }
    public static TextColor fromLegacy(String name) {
        if (name.length() == 2 && name.charAt(0) == '\u00a7') {
            return legacyLookup.get(name.charAt(1));
        } else if (name.length() == 1) {
            return legacyLookup.get(name.charAt(0));
        }
        return null;
    }

    private final String value;
    private final char legacyChar;

    public TextColor(String value, char legacyChar) {
        this.value = value;
        this.legacyChar = legacyChar;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf('\u00a7' + this.legacyChar);
    }
}
