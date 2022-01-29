package com.cecer1.projects.mc.cecermclib.common.modules.text;

import com.cecer1.projects.mc.cecermclib.common.CecerMCLib;
import com.google.common.base.Suppliers;

import java.util.function.Supplier;

public abstract class WrappedComponent<TRoot> {
    private final TRoot component;
    private final Supplier<WrappedComponent<TRoot>> flattenedComponent;

    public WrappedComponent(TRoot component) {
        this.component = component;
        this.flattenedComponent = Suppliers.memoize(this::computeFlattened);
    }

    public TRoot getComponent() {
        return this.component;
    }
    public WrappedComponent<TRoot> getFlattenedComponent() {
        return this.flattenedComponent.get();
    }

    public abstract String getPlainString();
    public abstract String getFormattedString();

    public abstract TextColor getColor();
    public abstract boolean isItalic();
    public abstract boolean isBold();
    public abstract boolean isUnderline();
    public abstract boolean isStrikethrough();
    public abstract boolean isObfuscated();

    public abstract void appendChild(TRoot partComponent);
    public void appendChild(WrappedComponent<TRoot> component) {
        this.appendChild(component.getComponent());
    }

    public abstract WrappedComponent<TRoot>[] getChildren();
    public abstract WrappedComponent<TRoot> getCopyWithoutChildren();

    public abstract String getJson();

    protected WrappedComponent<TRoot> computeFlattened() {
        //noinspection unchecked
        WrappedComponent<TRoot> root = (WrappedComponent<TRoot>) CecerMCLib.get(TextModule.class).getAdapter().newRootComponent();
        this.buildFlattenedComponent(root, this);
        return root;
    }
    private void buildFlattenedComponent(WrappedComponent<TRoot> root, WrappedComponent<TRoot> component) {
        WrappedComponent<TRoot> c = component.getCopyWithoutChildren();
        root.appendChild(c);

        for (WrappedComponent<TRoot> sibling : component.getChildren()) {
            this.buildFlattenedComponent(root, sibling);
        }
    }


    public abstract void handleClick();
}
