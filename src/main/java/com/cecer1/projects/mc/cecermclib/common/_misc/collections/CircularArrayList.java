package com.cecer1.projects.mc.cecermclib.common._misc.collections;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class CircularArrayList<E> extends AbstractList<E> 
		implements List<E>, RandomAccess, Cloneable, Serializable {

	private static final long serialVersionUID = -4886139346193901717L;
	private transient Object[] elementData;
	private int size;
	private int head;
	private int tail;

	/**
	 * Constructs an empty list with the specified initial capacity.
	 *
	 * @param initialCapacity the initial capacity of the list
	 * @exception IllegalArgumentException if the specified initial capacity
	 *            is negative
	 */
	public CircularArrayList(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		}

		this.elementData = new Object[initialCapacity];
		this.size = this.tail = this.head = 0;
	}

	/**
	 * Constructs an empty list with an initial capacity of ten.
	 */
	public CircularArrayList() {
		this(10);
	}

	/**
	 * Constructs a list containing the elements of the specified
	 * collection, in the order they are returned by the collection's
	 * iterator.
	 *
	 * @param c the collection whose elements are to be placed into this list
	 * @throws NullPointerException if the specified collection is null
	 */
	public CircularArrayList(Collection<? extends E> c) {
		this.elementData = c.toArray();
		this.tail = 0;
		this.size = this.head = this.elementData.length;

		// c.toArray might (incorrectly) not return Object[] (see 6260652)
		if (this.elementData.getClass() != Object[].class) {
			this.elementData = Arrays.copyOf(this.elementData, this.size, Object[].class);
		}
	}

	private void copy(Object[] src, Object[] dest) {
		System.arraycopy(src, 0, dest, 0, this.head);
		System.arraycopy(src, src.length + this.tail, dest, dest.length + this.tail, -this.tail);
	}

	private void ensureCapacityInternal(int minCapacity) {
		this.modCount++;

		// overflow-conscious code
		if (minCapacity - this.elementData.length > 0) {
			this.grow(minCapacity);
		}
	}

	/**
	 * The maximum size of array to allocate.
	 * Some VMs reserve some header words in an array.
	 * Attempts to allocate larger arrays may result in
	 * OutOfMemoryError: Requested array size exceeds VM limit
	 */
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	/**
	 * Increases the capacity to ensure that it can hold at least the
	 * number of elements specified by the minimum capacity argument.
	 *
	 * @param minCapacity the desired minimum capacity
	 */
	private void grow(int minCapacity) {
		// overflow-conscious code
		int oldCapacity = this.elementData.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);

		if (newCapacity - minCapacity < 0) {
			newCapacity = minCapacity;
		}

		if (newCapacity - MAX_ARRAY_SIZE > 0) {
			newCapacity = hugeCapacity(minCapacity);
		}

		Object[] oldData = this.elementData;
		this.elementData = new Object[newCapacity];

		this.copy(oldData, this.elementData);
	}

	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0) { // overflow
			throw new OutOfMemoryError();
		}

		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}

	/**
	 * Returns the number of elements in this list.
	 *
	 * @return the number of elements in this list
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Returns <tt>true</tt> if this list contains no elements.
	 *
	 * @return <tt>true</tt> if this list contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
	 * More formally, returns <tt>true</tt> if and only if this list contains
	 * at least one element <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
	 *
	 * @param o element whose presence in this list is to be tested
	 * @return <tt>true</tt> if this list contains the specified element
	 */
	@Override
	public boolean contains(Object o) {
		return this.indexOf(o) >= 0;
	}

	/**
	 * Returns the index of the first occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 * More formally, returns the lowest index <tt>i</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
	 * or -1 if there is no such index.
	 */
	@Override
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < this.size; i++) {
				if (this.elementData[this.pos(i)] == null) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < this.size; i++) {
				if (o.equals(this.elementData[this.pos(i)])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Returns the index of the last occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 * More formally, returns the highest index <tt>i</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
	 * or -1 if there is no such index.
	 */
	@Override
	public int lastIndexOf(Object o) {
		if (o == null) {
			for (int i = this.size - 1; i >= 0; i--) {
				if (this.elementData[this.pos(i)] == null) {
					return i;
				}
			}
		} else {
			for (int i = this.size - 1; i >= 0; i--) {
				if (o.equals(this.elementData[this.pos(i)])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Returns a shallow copy of this <tt>CircularArrayList</tt> instance.  
	 * (The elements themselves are not copied.)
	 *
	 * @return a clone of this <tt>CircularArrayList</tt> instance
	 */
	@Override
	public Object clone() {
		try {
			@SuppressWarnings("unchecked")
			CircularArrayList<E> c = (CircularArrayList<E>) super.clone();

			c.elementData = new Object[this.size];
			this.copy(this.elementData, c.elementData);
			c.modCount = 0;
			return c;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	/**
	 * Returns an array containing all of the elements in this list
	 * in proper sequence (from first to last element).
	 *
	 * <p>The returned array will be "safe" in that no references to it are
	 * maintained by this list.  (In other words, this method must allocate
	 * a new array).  The caller is thus free to modify the returned array.
	 *
	 * <p>This method acts as bridge between array-based and collection-based
	 * APIs.
	 *
	 * @return an array containing all of the elements in this list in
	 *         proper sequence
	 */
	@Override
	public Object[] toArray() {
		Object[] a = new Object[size];

		System.arraycopy(this.elementData, this.elementData.length + this.tail, a, 0, -this.tail);
		System.arraycopy(this.elementData, 0, a, -this.tail, this.head);

		return a;
	}

	/**
	 * Returns an array containing all of the elements in this list in proper
	 * sequence (from first to last element); the runtime type of the returned
	 * array is that of the specified array. If the list fits in the
	 * specified array, it is returned therein. Otherwise, a new array is
	 * allocated with the runtime type of the specified array and the size of
	 * this list.
	 *
	 * <p>If the list fits in the specified array with room to spare
	 * (i.e., the array has more elements than the list), the element in
	 * the array immediately following the end of the collection is set to
	 * <tt>null</tt>.  (This is useful in determining the length of the
	 * list <i>only</i> if the caller knows that the list does not contain
	 * any null elements.)
	 *
	 * @param a the array into which the elements of the list are to
	 *          be stored, if it is big enough; otherwise, a new array of the
	 *          same runtime type is allocated for this purpose.
	 * @return an array containing the elements of the list
	 * @throws ArrayStoreException if the runtime type of the specified array
	 *         is not a supertype of the runtime type of every element in
	 *         this list
	 * @throws NullPointerException if the specified array is null
	 */
	@Override
	@SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
	public <T> T[] toArray(T[] a) {
		if (a.length < this.size) {
			// Make a new array of a's runtime type
			a = (a.getClass() == Object[].class)
					? (T[]) new Object[this.size]
					: (T[]) Array.newInstance(a.getClass().getComponentType(), this.size);

		}

		System.arraycopy(this.elementData, this.elementData.length + this.tail, a, 0, -this.tail);
		System.arraycopy(this.elementData, 0, a, -this.tail, this.head);

		if (a.length > this.size) {
			a[this.size] = null;
		}

		return a;
	}

	// Positional Access Operations

	private int pos(int index) {
		return (this.elementData.length + this.tail + index) % this.elementData.length;
	}

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param  index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E get(int index) {
		this.rangeCheck(index);

		return (E) this.elementData[this.pos(index)];
	}

	/**
	 * Replaces the element at the specified position in this list with
	 * the specified element.
	 *
	 * @param index index of the element to replace
	 * @param element element to be stored at the specified position
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E set(int index, E element) {
		this.rangeCheck(index);

		int pos = this.pos(index);
		E oldValue = (E) this.elementData[pos];
		this.elementData[pos] = element;
		return oldValue;
	}

	/**
	 * Appends the specified element to the end of this list.
	 *
	 * @param e element to be appended to this list
	 * @return <tt>true</tt> (as specified by {@link Collection#add})
	 */
	@Override
	public boolean add(E e) {
		this.ensureCapacityInternal(this.size + 1); // Increments modCount!!
		this.elementData[this.head] = e;
		this.head++;
		this.size++;
		return true;
	}

	/**
	 * Inserts the specified element at the specified position in this
	 * list. Shifts elements if necessarily
	 *
	 * @param index index at which the specified element is to be inserted
	 * @param element element to be inserted
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public void add(int index, E element) {
		this.rangeCheckForAdd(index);

		this.ensureCapacityInternal(this.size + 1); // Increments modCount!!

		int i = index + this.tail;
		if (i <= 0) {
			System.arraycopy(
					this.elementData, this.elementData.length + this.tail,
					this.elementData, this.elementData.length + this.tail - 1,
					index);

			this.elementData[this.elementData.length + i - 1] = element;
			this.tail--;
		} else {
			System.arraycopy(this.elementData, i, this.elementData, i + 1, this.size - index);
			this.elementData[i] = element;
			this.head++;
		}

		this.size++;
	}

	/**
	 * Removes the element at the specified position in this list.
	 * Shifts elements if necessarily
	 *
	 * @param index the index of the element to be removed
	 * @return the element that was removed from the list
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E remove(int index) {
		this.rangeCheck(index);

		E value = (E) this.elementData[this.pos(index)];
		this.fastRemove(index);

		return value;
	}

	/**
	 * Removes the first occurrence of the specified element from this list,
	 * if it is present.  If the list does not contain the element, it is
	 * unchanged.  More formally, removes the element with the lowest index
	 * <tt>i</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
	 * (if such an element exists).  Returns <tt>true</tt> if this list
	 * contained the specified element (or equivalently, if this list
	 * changed as a result of the call).
	 *
	 * @param o element to be removed from this list, if present
	 * @return <tt>true</tt> if this list contained the specified element
	 */
	public boolean remove(Object o) {
		if (o == null) {
			for (int index = 0; index < size; index++) {
				if (this.elementData[this.pos(index)] == null) {
					this.fastRemove(index);
					return true;
				}
			}
		} else {
			for (int index = 0; index < this.size; index++) {
				if (o.equals(this.elementData[this.pos(index)])) {
					this.fastRemove(index);
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Private remove method that skips bounds checking and does not
	 * return the value removed.
	 */
	private void fastRemove(int index) {
		this.modCount++;

		int i = index + this.tail;
		if (i < 0) {
			int numMoved = index;
			if (numMoved > 0) {
				System.arraycopy(
						this.elementData, this.elementData.length + this.tail,
						this.elementData, this.elementData.length + this.tail + 1,
						numMoved);
			}

			this.elementData[this.elementData.length + this.tail] = null; // Let gc do its work
			this.tail++;
		} else {
			int numMoved = this.size - index - 1;
			if (numMoved > 0) {
				System.arraycopy(this.elementData, i + 1, this.elementData, i, numMoved);
			}

			this.head--;
			this.elementData[this.head] = null; // Let gc do its work
		}

		size--;
	}

	/**
	 * Removes all of the elements from this list.  The list will
	 * be empty after this call returns.
	 */
	public void clear() {
		this.modCount++;

		// Let gc do its work
		for (int i = 0; i < this.size; i++) {
			this.elementData[this.pos(i)] = null;
		}

		this.size = this.tail = this.head = 0;
	}

	/**
	 * Appends all of the elements in the specified collection to the end of
	 * this list, in the order that they are returned by the
	 * specified collection's Iterator.  The behavior of this operation is
	 * undefined if the specified collection is modified while the operation
	 * is in progress.  (This implies that the behavior of this call is
	 * undefined if the specified collection is this list, and this
	 * list is nonempty.)
	 *
	 * @param c collection containing elements to be added to this list
	 * @return <tt>true</tt> if this list changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 */
	public boolean addAll(Collection<? extends E> c) {
		Object[] a = c.toArray();
		int numNew = a.length;
		ensureCapacityInternal(this.size + numNew); // Increments modCount
		System.arraycopy(a, 0, this.elementData, this.head, numNew);
		this.head += numNew;
		this.size += numNew;
		return numNew != 0;
	}

	/**
	 * Checks if the given index is in range. If not, throws an appropriate
	 * runtime exception.
	 */
	private void rangeCheck(int index) {
		if (index >= this.size || index < 0) {
			throw new IndexOutOfBoundsException(
					"Index: " + index + ", Size: " + this.size);
		}
	}

	/**
	 * A version of rangeCheck used by add and addAll.
	 */
	private void rangeCheckForAdd(int index) {
		if (index > this.size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	/**
	 * Constructs an IndexOutOfBoundsException detail message.
	 * Of the many possible refactorings of the error handling code,
	 * this "outlining" performs best with both server and client VMs.
	 */
	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + this.size;
	}

}