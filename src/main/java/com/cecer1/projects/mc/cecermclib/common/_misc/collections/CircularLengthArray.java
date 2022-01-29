package com.cecer1.projects.mc.cecermclib.common._misc.collections;

import java.util.Arrays;
import java.util.function.IntFunction;

public class CircularLengthArray<T> implements Iterable<T> {
    protected final T[] backingArray;
    protected int startIndex;
    private int count;

    public CircularLengthArray(T[] backingArray) {
        this.backingArray = backingArray;
        this.startIndex = 0;
        this.count = 0;
    }
    public CircularLengthArray(int size, IntFunction<T[]> backingArrayConstructor) {
        this(backingArrayConstructor.apply(size));
    }

    private int resolveIndex(int index) {
        if (index < 0 || index >= this.count) {
            throw new ArrayIndexOutOfBoundsException(index);
        }

        index = this.startIndex + index;
        if (index > this.backingArray.length) {
            index -= this.backingArray.length;
        }
        return index;
    }

    public T get(int index) {
        return this.backingArray[this.resolveIndex(index)];
    }
    public void set(int index, T value) {
        this.backingArray[this.resolveIndex(index)] = value;
    }
    public void clear() {
        Arrays.fill(this.backingArray, null);
        this.startIndex = 0;
        this.count = 0;
    }

    public int count() {
        return this.count;
    }
    public boolean isEmpty() {
        return this.count == 0;
    }

    /**
     * Appends an entry to the end of the array removing from the start of the array if the array is full.
     * @return the entry removed from the start of the array or null if no entry was removed.
     */
    public T append(T newEntry) {
        if (this.count < this.backingArray.length) {
            this.set(this.count, newEntry);
            this.count++;
            return null;
        }

        T oldValue = this.get(0);
        this.set(0, newEntry);
        this.startIndex++;
        return oldValue;
    }

    public boolean remove(int index) {
        if (index < 0 || index >= this.count) {
            return false;
        }

        int shiftCount = this.count - index - 1;
        int from = index;
        for (int i = 0; i < shiftCount; i++) {
            int to = (index + i) % this.backingArray.length;
            from = (index + i + 1) % this.backingArray.length;
            this.backingArray[to] = this.backingArray[from];
        }
        this.set(from, null);
        this.count--;
        return true;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterator();
    }

    public class Iterator implements java.util.Iterator<T> {
        private final int start;
        private final int end;
        private int i;

        public Iterator() {
            this(0);
        }
        public Iterator(int start) {
            this.start = CircularLengthArray.this.resolveIndex(start);
            this.end = CircularLengthArray.this.resolveIndex(start + CircularLengthArray.this.count);
            this.i = this.start;
        }

        @Override
        public boolean hasNext() {
            return this.i != this.end;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                return null;
            }
            this.i = CircularLengthArray.this.resolveIndex(this.i + 1);
            return CircularLengthArray.this.backingArray[this.i];
        }

    }
}
