package com.cecer1.projects.mc.cecermclib.common.modules.text;

import com.cecer1.projects.mc.cecermclib.common.modules.text.parts.Click;
import com.cecer1.projects.mc.cecermclib.common.modules.text.parts.Hover;

public class TextState {
    private TextState parent;
    public TextState getParent() {
        if (this.parent == null) {
            throw new NullPointerException("Cannot get parent of root text state");
        }
        return this.parent;
    }

    public String getSegmentId() {
        if (this.parent == null) {
            return null;
        }
        return this.parent.getSegmentId();
    }

    private TextColor color;
    public TextColor getColor() {
        if (this.color == null) {
            return this.getParent().getColor();
        }
        return this.color;
    }
    public TextState setColor(TextColor color) {
        this.color = color;
        return this;
    }

    private Boolean isBold;
    public boolean isBold() {
        if (this.isBold == null) {
            return this.getParent().isBold();
        }
        return this.isBold;
    }
    public TextState setBold(boolean bold) {
        this.isBold = bold;
        return this;
    }

    private Boolean isItalic;
    public Boolean isItalic() {
        if (this.isItalic == null) {
            return this.getParent().isItalic();
        }
        return this.isItalic;
    }
    public TextState setItalic(boolean italic) {
        this.isItalic = italic;
        return this;
    }

    private Boolean isUnderlined;
    public Boolean isUnderlined() {
        if (this.isUnderlined == null) {
            return this.getParent().isUnderlined();
        }
        return this.isUnderlined;
    }
    public TextState setUnderlined(boolean underlined) {
        this.isUnderlined = underlined;
        return this;
    }

    private Boolean isStrikethrough;
    public Boolean isStrikethrough() {
        if (this.isStrikethrough == null) {
            return this.getParent().isStrikethrough();
        }
        return this.isStrikethrough;
    }
    public TextState setStrikethrough(boolean strikethrough) {
        this.isStrikethrough = strikethrough;
        return this;
    }

    private Boolean isObfuscated;
    public Boolean isObfuscated() {
        if (this.isObfuscated == null) {
            return this.getParent().isObfuscated();
        }
        return this.isObfuscated;
    }
    public TextState setObfuscated(boolean obfuscated) {
        this.isObfuscated = obfuscated;
        return this;
    }

    private Click click;
    public Click getClick() {
        if (this.click == null) {
            return this.getParent().getClick();
        }
        return this.click;
    }
    public TextState setClick(Click click) {
        this.click = click;
        return this;
    }

    private String insertion;
    public String getInsertion() {
        if (this.insertion == null) {
            return this.getParent().getInsertion();
        }
        return this.insertion;
    }
    public TextState setInsertion(String insertion) {
        this.insertion = insertion;
        return this;
    }

    private Hover hover;
    public Hover getHover() {
        if (this.hover == null) {
            return this.getParent().getHover();
        }
        return this.hover;
    }
    public TextState setHover(Hover hover) {
        this.hover = hover;
        return this;
    }

    public static TextState newRootState() {
        TextState state = new TextState();
        state.resetAll();
        return state;
    }
    public TextState newSegment(String id) {
        if (this.getSegmentId() != null) {
            throw new IllegalStateException("Nesting segments is not supported");
        }

        TextState state = new SegmentTextState(id);
        state.resetAll();
        state.parent = this;
        return state;
    }
    protected void resetAll() {
        this.setColor(TextColor.WHITE);
        this.setBold(false);
        this.setItalic(false);
        this.setUnderlined(false);
        this.setStrikethrough(false);
        this.setObfuscated(false);
        this.setClick(Click.NONE);
        this.setInsertion("");
        this.setHover(Hover.NONE);
    }

    public TextState newChild() {
        TextState state = new TextState();
        state.parent = this;
        return state;
    }
}
