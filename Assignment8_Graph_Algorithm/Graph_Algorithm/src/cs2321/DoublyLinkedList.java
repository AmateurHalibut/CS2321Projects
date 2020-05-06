package cs2321;

import net.datastructures.Position;
import net.datastructures.PositionalList;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Alex Hromada
 * Assignment 1
 * This class is a representation of a Doubly Linked List data scructure
 *
 * @param <E>
 */

public class DoublyLinkedList<E> implements PositionalList<E> {

    private int size = 0;            // Size of the list
    private Node<E> header;            // Header sentinel node
    private Node<E> trailer;        // Trailer sentinel node

    public DoublyLinkedList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return number of elements in the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Tests whether the list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Method to check if node at position is header or trailer,
     * reduces redundancy in methods that require this check
     *
     * @param node
     * @return node if not header or trailer
     */
    private Position<E> position(Node<E> node) {
        if (node == header || node == trailer) return null;  // Checks to see if node at position is header or trailer
        return node;
    }

    /**
     * Checks if a position is a valid position and casts and returns the position as a node
     *
     * @param p position in the list
     * @return node at position
     * @throws IllegalArgumentException
     */
    private Node<E> validate(Position<E> p) throws IllegalArgumentException {

        // Checks if position p is an instance of a Node
        if (!(p instanceof Node)) {
            throw new IllegalArgumentException("Position is invalid");
        }
        Node<E> node = (Node<E>) p;

        // Checks whether or not the node is in the list
        if (node.getNext() == null) {
            throw new IllegalArgumentException("Node is no longer in the list");
        }
        return node;
    }

    /**
     * Helper method that adds a node between 2 nodes
     *
     * @param e       element of the new node
     * @param precede preceding node
     * @param succeed succeeding node
     * @return the new node
     */
    private Position<E> addBetween(E e, Node<E> precede, Node<E> succeed) {
        Node<E> newNode = new Node<>(e, precede, succeed);

        // Inserts node between the 2 input nodes
        precede.setNext(newNode);
        succeed.setPrev(newNode);

        size++;
        return newNode;
    }

    /**
     * Returns the node first in the list
     *
     * @return node first in the list
     */
    @Override
    public Position<E> first() {
        return position(header.getNext());
    }

    /**
     * Returns the node last in the list
     *
     * @return node last in the list
     */
    @Override
    public Position<E> last() {
        return position(trailer.getPrev());
    }

    /**
     * Returns the position before the position passed in
     *
     * @param p a Position of the list
     * @return preceding position of position
     * @throws IllegalArgumentException
     */
    @Override
    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getPrev());
    }

    /**
     * Returns the position succeeding the position passed in
     *
     * @param p a Position of the list
     * @return position succeeding the position
     * @throws IllegalArgumentException
     */
    @Override
    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getNext());
    }

    /**
     * Adds a new node to the front of the list
     *
     * @param e the new element
     * @return position of the new node
     */
    @Override
    public Position<E> addFirst(E e) {
        return addBetween(e, header, header.getNext());
    }

    /**
     * Adds a new node to the end of the list
     *
     * @param e the new element
     * @return position of the new node
     */
    @Override
    public Position<E> addLast(E e) {
        return addBetween(e, trailer.getPrev(), trailer);
    }

    /**
     * Adds a new node preceding the position given
     *
     * @param p the Position before which the insertion takes place
     * @param e the new element
     * @return position of the new node
     * @throws IllegalArgumentException
     */
    @Override
    public Position<E> addBefore(Position<E> p, E e)
            throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node.getPrev(), node);
    }

    /**
     * Adds a new node succeeding the position given
     *
     * @param p the Position after which the insertion takes place
     * @param e the new element
     * @return position of the new node
     * @throws IllegalArgumentException
     */
    @Override
    public Position<E> addAfter(Position<E> p, E e)
            throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node, node.getNext());
    }

    /**
     * Sets the element of the node at the given position
     *
     * @param p the Position of the element to be replaced
     * @param e the new element
     * @return old element of the position
     * @throws IllegalArgumentException
     */
    @Override
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E element = node.getElement();
        node.setElement(e);
        return element;
    }

    /**
     * Remnoves the node of the given position
     *
     * @param p the Position of the element to be removed
     * @return element of the node removed
     * @throws IllegalArgumentException
     */
    @Override
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        Node<E> precede = node.getPrev();
        Node<E> succeed = node.getNext();


        E element = node.getElement();

        // Update references of the nodes around the node being removed
        precede.setNext(succeed);
        succeed.setPrev(precede);

        // Dereference removed node
        node.setPrev(null);
        node.setNext(null);
        node.setElement(null);

        size--;
        return element;
    }

    //================================================================================================

    /**
     * Private inner class to iterate Positions of the queue
     */
    private class PositionIterator implements Iterator<Position<E>> {

        private Position<E> cursor = first();
        private Position<E> recent = null;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return cursor != null;
        }


        /**
         * Returns the next position in the iteration.
         *
         * @return the next position in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Position<E> next() {
            // Throws NoSuchElementException if the iterator goes past the end of the queue
            if (cursor == null) {
                throw new NoSuchElementException("No elements remaining in queue");
            }


            recent = cursor;
            cursor = after(cursor);
            return recent;
        }

//		@Override
//		public void remove() {
//			if(recent == null){
//				throw new IllegalStateException("No element to remove");
//			}
//			DoublyLinkedList.this.remove(recent);
//			recent = null;
//		}
    }
    //===========================================================================================


    /**
     * Returns a position iterator
     */
    private class PositionIterable implements Iterable<Position<E>> {
        @Override
        public Iterator<Position<E>> iterator() {
            return new PositionIterator();
        }
    }
    //===========================================================================================

    /**
     * Private inner class to iterate elements of the queue
     */
    private class ElementIterator implements Iterator<E> {

        Iterator<Position<E>> posIter = new PositionIterator();

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return posIter.hasNext();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            return posIter.next().getElement();
        }

//		@Override
//		public void remove() {
//			posIter.remove();
//		}
    }
    //===========================================================================================

    /**
     * Returns a new ElementIterator
     *
     * @return new element iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    /**
     * Returns an Iterable representation of Positions in the queue
     *
     * @return an iterable of position
     */
    @Override
    public Iterable<Position<E>> positions() {
        return new PositionIterable();
    }

    /**
     * Remove the first element in the list
     *
     * @return removed element
     * @throws IllegalArgumentException
     */
    public E removeFirst() throws IllegalArgumentException {
        E element = remove(header.getNext());
        return element;
    }

    /**
     * Removed the last element in the list
     *
     * @return removed element
     * @throws IllegalArgumentException
     */
    public E removeLast() throws IllegalArgumentException {
        E element = remove(trailer.getPrev());
        return element;
    }

    /**
     * Inner Node Class for Doubly Linked Queue
     *
     * @param <E>
     */
    private static class Node<E> implements net.datastructures.Position<E> {

        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        /**
         * Returns the element stored at this position.
         *
         * @return the stored element
         * @throws IllegalStateException if position no longer valid
         */
        @Override
        public E getElement() throws IllegalStateException {
            if (next == null) {
                throw new IllegalStateException("Position is no longer valid");
            }
            return element;
        }
    }

}
