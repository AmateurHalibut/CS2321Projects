package cs2321;

import net.datastructures.Stack;

/**
 * Alex Hromada
 * Assignment 2
 * This class is a representation of a Doubly Linked Stack data structure
 *
 * @param <E>
 */
public class DLLStack<E> implements Stack<E> {

	private int size = 0;
	private Node<E> header;
	private Node<E> trailer;


	/**
	 * Constructor for Doubly Linked Stack
	 */
	public DLLStack() {
		header = new Node(null, null, null);
		trailer = new Node(null, header, null);
		header.setNext(trailer);
	}

	/**
	 * Returns the number of elements in the stack.
	 * @return number of elements in the stack
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Tests whether the stack is empty.
	 *
	 * @return true if the stack is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Pushes an element to the top of the stack
	 * @param e   the element to be inserted
	 */
	@Override
	public void push(E e) {
		Node<E> n = new Node(e, header, header.getNext());
		Node<E> next = header.getNext();
		Node<E> prev = header;

		next.setPrev(n);
		prev.setNext(n);
		size++;
	}

	/**
	 * Returns the top element in the stack without removing the element
	 * @return top element in the stack
	 */
	@Override
	public E top() {
		if(isEmpty()){
			return null;
		}
		return header.getNext().getElement();
	}

	/**
	 * Removes and returns the top element in the stack
	 * @return top element in the stack
	 */
	@Override
	public E pop() {
		if(isEmpty()){
			return null;
		}


		Node<E> node = header.getNext();
		Node<E> prev = header;
		Node<E> next = node.getNext();

		// Get element from node being popped
		E element = node.getElement();

		header.setNext(next);
		next.setPrev(prev);

		// Dereference the node
		node.setPrev(null);
		node.setNext(null);
		node.setPrev(null);

		size--;
		return element;
	}

	/**
	 * Private inner doubly linked node class
	 * @param <E>
	 */
	private static class Node<E> {

		private E element;
		private Node<E> prev;
		private Node<E> next;

		public Node(E element, Node<E> prev, Node<E> next) {
			this.element = element;
			this.prev = prev;
			this.next = next;
		}

		public E getElement() {
			return element;
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
	}

}
