package com.cecer1.projects.mc.cecermclib.common._misc.collections;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Tree<T> {

    private Tree<T> parent;
    private final Set<Tree<T>> children;

    public Tree<T> getParent() {
        return this.parent;
    }

    public Set<Tree<T>> getChildren() {
        return Collections.unmodifiableSet(this.children);
    }
    public Set<Tree<T>> getDeepChildren() {
        Set<Tree<T>> results = this.children.stream()
                .flatMap(c -> c.getDeepChildren().stream())
                .collect(Collectors.toSet());
        results.add(this);
        return results;
    }

    private T value;
    public T getValue() {
        return this.value;
    }
    public void setValue(T value) {
        if (value == null) {
            throw new NullPointerException("Setting value to null is not supported");
        }
        this.value = value;
    }

    public Tree(T value) {
        if (value == null) {
            throw new NullPointerException("Setting value to null is not supported");
        }

        this.parent = null;
        this.children = new HashSet<>();
        this.value = value;
    }

    public Tree<T> addChild(T value) {
        if (value == null) {
            throw new NullPointerException("Adding a null child is not supported");
        }

        return this.addChildTree(new Tree<>(value));
    }
    public Tree<T> addChildTree(Tree<T> valueTree) {
        if (valueTree == null) {
            throw new NullPointerException("Adding a null child tree is not supported");
        }

        valueTree.parent = this;
        this.children.add(valueTree);
        return valueTree;
    }

    public void removeChild(T value, boolean moveChildrenToParent) {
        if (value == null) {
            return;
        }

        Tree<T> valueTree = this.children.stream()
                .filter(c -> c.value.equals(value))
                .findFirst().orElse(null);

        if (valueTree != null) {
            valueTree.parent = null;
            this.children.remove(valueTree);

            if (moveChildrenToParent) {
                for (Tree<T> childTree : valueTree.children) {
                    childTree.parent = this;
                    this.children.add(childTree);
                }
            }
        }
    }

    public void moveTo(Tree<T> newParent) {
        if (newParent == null) {
            throw new NullPointerException("Moving a tree to a null parent is not supported");
        }

        this.parent.children.remove(this);
        this.parent = newParent;
        this.parent.children.add(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        if (obj instanceof Tree) {
            return this.value.equals(((Tree)obj).value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.parent, this.value);
    }

    public boolean deepContains(T value) {
        if (this.value == value) {
            return true;
        }

        for (Tree<T> child : this.children) {
            if (child.deepContains(value)) {
                return true;
            }
        }
        return false;
    }
}