package cs2321;

import net.datastructures.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Alex Hromada
 * Assignment 1
 * This class is a representation of a Doubly Linked List data scructure
 *
 * @param <E>
 */

public class ArrayList<E> implements List<E> {

    public static final int CAPACITY = 16;            // Default capacity
    private int size = 0;                            // Current size of the list, updated on add and remove
    private E[] data;                                // Array used to store the data of the list

    public ArrayList() {
        this(CAPACITY);
    }

    public ArrayList(int capacity) {
        this.data = (E[]) new Object[capacity];
    }

    /**
     * Returns the size of the list
     *
     * @return size of the list
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Checks if the list is empty
     *
     * @return true if empty, false if not
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the element at a given index without removing the element
     *
     * @param i the index of the element to return
     * @return element at the given index
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        return data[i];
    }

    /**
     * Sets the element of a given index
     *
     * @param i the index of the element to replace
     * @param e the new element to be stored
     * @throws IndexOutOfBoundsException
     * @return old value of the given index
     */
    @Override
    public E set(int i, E e) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E temp = data[i];
        data[i] = e;
        return temp;
    }

    /**
     * Adds a value to the list at a given index
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     * @throws IndexOutOfBoundsException
     */
    @Override
    public void add(int i, E e) throws IndexOutOfBoundsException {
        checkIndex(i, size + 1);

        // If the size of the list is the size of the array, resize the array
        if (size == data.length) {
            resize(data.length);
        }

        // Loops from the end of the list to the index and moves each item one to the right to make room
        // for the new element
        for (int j = size - 1; j >= i; j--) {
            data[j + 1] = data[j];
        }

        // Sets element at index to the new element
        data[i] = e;
        size++;

    }

    /**
     * Removes an element from the list at a given index
     *
     * @param i the index of the element to be removed
     * @throws IndexOutOfBoundsException
     * @return removed element
     */
    @Override
    public E remove(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E e = data[i];

        // loops from index to the end of the list and moves each item one to the left
        for (int j = i; j < size - 1; j++) {
            data[j] = data[j + 1];
        }

        // Sets end value to null to clean up
        data[size - 1] = null;
        size--;
        return e;
    }

    /**
     * Checks if the passed index is within the bounds of the list
     *
     * @param i index
     * @param n size of the list
     * @throws IndexOutOfBoundsException
     */
    protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("Illegal index: " + i);
        }
    }

    /**
     * Resizes the array of the list.  Doubles the size of the array when called
     *
     * @param capacity current capacity of the array
     */
    protected void resize(int capacity) {
        E[] temp = (E[]) new Object[capacity * 2];

        for (int k = 0; k < size; k++) {
            temp[k] = data[k];
        }
        data = temp;
    }

    private class ArrayListIterator implements Iterator<E> {
        private int index = 0;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return index < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            if (index >= size) {
                throw new NoSuchElementException("No element at index: " + index);
            }
            return data[index++];
        }
    }

    /**
     * Returns an iterator of the ArrayList class
     *
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    /**
     * Adds a new element to the front of the list
     *
     * @param e an element
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * Adds a new element to the end of the list
     *
     * @param e an element
     */
    public void addLast(E e) {
        add(size, e);
    }

    /**
     * Removes the element at the front of the list
     *
     * @return element removed
     * @throws IndexOutOfBoundsException
     */
    public E removeFirst() throws IndexOutOfBoundsException {
        E e = remove(0);
        return e;
    }

    /**
     * Removes the element at the end of the list
     *
     * @return element removed
     * @throws IndexOutOfBoundsException
     */
    public E removeLast() throws IndexOutOfBoundsException {
        E e = remove(size - 1);
        return e;
    }

    // Return the capacity of array, not the number of elements.
    // Notes: The initial capacity is 16. When the array is full, the array should be doubled.

    /**
     * Returns the capacity of the storage array
     *
     * @return length of the storage array
     */
    public int capacity() {
        return data.length;
    }

}
