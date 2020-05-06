package cs2321;

import net.datastructures.Queue;

/**
 * @author ruihong-adm
 *  Alex Hromada
 *  Assignment 1
 *  This class is a representation of a Doubly Linked List data scructure
 * @param <E>
 *
 */

public class CircularArrayQueue<E> implements Queue<E> {

    private static final int CAPACITY = 16; // Default capacity
    private int size = 0;                    // Size of the queue
    private int front = 0;                    // Index of the front of the queue
    private E[] data;                        // Array for queue data storage


    public CircularArrayQueue(int queueSize) {
        this.data = (E[]) new Object[queueSize];
    }

    public CircularArrayQueue() {
        this.data = (E[]) new Object[CAPACITY];
    }

    /**
     * Returns the current size of the list
     * @return size of the list
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Checks if the list is empty
     * @return true if empty, false if not
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds an element to the end of the queue
     * @param e  the element to be inserted
     */
    @Override
    public void enqueue(E e) {
        // Throws exception if queue is full
        if (size == data.length) {
            throw new IllegalStateException("Queue is full");
        }

        // Adds new element to end of queue
        int next = (front + size) % data.length;
        data[next] = e;

        size++;
    }

    /**
     * Returns the first element in the queue without removing the element
     * @return element at the front of the queue
     */
    @Override
    public E first() {
        if (isEmpty()) return null;

        return data[front];
    }

    /**
     * Removes the element at the front of the queue
     * @return element removed from the queue
     */
    @Override
    public E dequeue() {
        if (isEmpty()) return null;

        // Grabs element from front of queue
        E e = data[front];

        // Set current front index of the queue to null
        data[front] = null;

        // Sets front of the queue to the next element in queue
        front = (front + 1) % data.length;

        size--;
        return e;
    }

}
