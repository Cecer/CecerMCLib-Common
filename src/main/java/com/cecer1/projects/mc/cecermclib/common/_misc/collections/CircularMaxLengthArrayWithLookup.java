package com.cecer1.projects.mc.cecermclib.common._misc.collections;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.function.IntFunction;

public class CircularMaxLengthArrayWithLookup<T> extends CircularMaxLengthArray<T> {
    private Object2IntMap<T> lookup;

    public CircularMaxLengthArrayWithLookup(T[] backingArray) {
        super(backingArray);
        this.lookup = new Object2IntOpenHashMap<>();
    }
    public CircularMaxLengthArrayWithLookup(int size, IntFunction<T[]> backingArrayConstructor) {
        super(size, backingArrayConstructor);
        this.lookup = new Object2IntOpenHashMap<>();
    }
    public T set(int index, T value) {
        T replaced = super.set(index, value);
        this.lookup.remove(replaced);
        this.lookup.put(value, index);
        return replaced;
    }
    public void clear() {
        super.clear();
        this.lookup.clear();;
    }

    public T remove(int index) {
        T removed = super.remove(index);
        if (removed != null) {
            this.lookup.remove(removed);
        }
        return removed;
    }

    public boolean remove(T value) {
        int index = this.lookup.getInt(value);
        if (index == this.lookup.defaultReturnValue()) {
            return false;
        }
        T removed = this.remove(index);
        if (value != removed) {
            throw new IllegalStateException("Removed the wrong item in CircularMaxLengthWithLookupArray#remove(T)! This is likely a serious bug in CircularMaxLengthWithLookupArray and should be reported!");
        }
        return true;
    }
}
