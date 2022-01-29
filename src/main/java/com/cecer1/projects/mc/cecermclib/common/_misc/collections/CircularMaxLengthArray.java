package com.cecer1.projects.mc.cecermclib.common._misc.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.function.IntFunction;

public class CircularMaxLengthArray<T> implements Iterable<T> {
    protected final T[] backingArray;
    protected int startIndex;
    private int count;
    private int modCount;

    public CircularMaxLengthArray(T[] backingArray) {
        this.backingArray = backingArray;
        this.startIndex = 0;
        this.count = 0;
        this.modCount = 0;
    }
    public CircularMaxLengthArray(int size, IntFunction<T[]> backingArrayConstructor) {
        this(backingArrayConstructor.apply(size));
    }

    private int resolveInternalIndex(int publicIndex) {
        return Math.floorMod(this.startIndex + publicIndex, this.backingArray.length);
    }

    public T get(int index) {
        if (index < 0 || index >= this.count) {
            throw new ArrayIndexOutOfBoundsException(index);
        }

        return this.backingArray[this.resolveInternalIndex(index)];
    }
    public T set(int index, T value) {
        return this.set0(index, value, true);
    }
    private T set0(int index, T value, boolean adjustModCount) {
        if (index < 0 || index >= this.count) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        if (adjustModCount) {
            this.modCount++;
        }

        T replaced = this.backingArray[this.resolveInternalIndex(index)];
        this.backingArray[this.resolveInternalIndex(index)] = value;
        return replaced;
    }
    public void clear() {
        this.modCount++;
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
       return this.append(newEntry, true);
    }
    public T append(T newEntry, boolean autoRemoveExcess) {
        if (this.count < this.backingArray.length) {
            // Is not full
            this.modCount++;
            this.count++;
            this.set0(this.count-1, newEntry, false);
            return null;
        }

        // Is full
        T oldValue = this.get(0);
        if (autoRemoveExcess) {
            this.modCount++;
            this.set0(0, newEntry, false);
            this.startIndex++;
        }
        return oldValue;
    }
    public boolean prepend(T newEntry) {
        if (this.count == this.backingArray.length) {
            // Is full
            return false;
        }

        // Is not full
        this.startIndex = this.resolveInternalIndex(-1);
        this.modCount++;
        this.count++;
        this.set0(0, newEntry, false);
        return true;
    }

    public T remove(int index) {
        if (index < 0 || index >= this.count) {
            return null;
        }

        this.modCount++;
        for (int i = index; i < this.count; i++) {
            this.set0(i + 1, this.get(i), false);
        }
        T removed = this.get(this.count - 1);
        this.set0(this.count - 1, null, false);
        this.count--;
        return removed;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterator(false);
    }
    public java.util.Iterator<T> reverseIterator() {
        return new Iterator(true);
    }

    public class Iterator implements java.util.Iterator<T> {
        private int end;
        private int i;
        private int step;
        private int expectedModCount;

        public Iterator(boolean reversed) {
            if (!reversed) {
                this.i = 0;
                this.end = CircularMaxLengthArray.this.count;
                this.step = 1;
            } else {
                this.i = CircularMaxLengthArray.this.count-1;
                this.end = -1;
                this.step = -1;
            }
            this.expectedModCount = CircularMaxLengthArray.this.modCount;
        }

        private void checkModCount() {
            if (this.expectedModCount != CircularMaxLengthArray.this.modCount) {
                throw new ConcurrentModificationException();
            }
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
            this.checkModCount();
            this.i += this.step;
            return CircularMaxLengthArray.this.get(this.i);
        }

        @Override
        public void remove() {
            this.checkModCount();
            CircularMaxLengthArray.this.remove(this.i);
            this.expectedModCount++;
            this.i -= this.step;
            this.end -= this.step;
        }
    }
}
