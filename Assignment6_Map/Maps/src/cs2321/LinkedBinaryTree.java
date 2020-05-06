package cs2321;

import net.datastructures.BinaryTree;
import net.datastructures.Position;

import java.util.Iterator;

/**
 *
 * Alex Hromada
 * Assignment 6
 * This class is a representation of a Linked Binary Tree data structure
 *
 */

public class LinkedBinaryTree<E> implements BinaryTree<E>{

	private Node<E> root = null;
	private int size = 0;

	private Node<E> creatNode(E element, Node<E> parent, Node<E> left, Node<E> right){
		return new Node<E>(element, parent, left, right);
	}
	@Override
	public Position<E> root() {
		return root;
	}

    public  LinkedBinaryTree( ) {

    }


    /*---------------------------------------------------------------------------------------------------------------*/
    /* Helper Methods */

	/**
	 * Checks if a position is a valid position and casts and returns the position as a node
	 *
	 * @param p position in the tree
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
		if (node.getParent() == node) {
			throw new IllegalArgumentException("Node is no longer in the tree");
		}
		return node;
	}


    /*---------------------------------------------------------------------------------------------------------------*/
    /* Iterable/Iterator Methods */

    @Override
    public Iterable<Position<E>> positions() {
        ArrayList<Position<E>> list = new ArrayList<>();

        inOrderPosition(root, list);

        return list;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
        ArrayList<Position<E>> list = new ArrayList<>();
        Node<E> node = validate(p);

        if(node.left != null)
            list.addLast(node.left);
        if(node.right != null)
            list.addLast(node.right);
        return list;
    }

    @Override
    public Iterator<E> iterator() {
        ArrayList<E> list = new ArrayList<>();
        inOrder(root, list);
        return list.iterator();
    }

    /**
     * InOrder traversal of positions of tree
     * @param v
     * @param list
     */
    private void inOrderPosition(Node<E> v, ArrayList<Position<E>> list){
        if(v.left != null){
            inOrderPosition(v.left, list);
        }

        list.addLast(v);

        if(v.right != null){
            inOrderPosition(v.right, list);
        }
    }

    /**
     * InOrder traversal of nodes in the tree
     * @param v
     * @param list
     */
    private void inOrder(Node<E> v, ArrayList<E> list){
        if(v.left != null){
            inOrder(v.left, list);
        }

        list.addLast(v.element);

        if(v.right != null){
            inOrder(v.right, list);
        }
    }


    /*---------------------------------------------------------------------------------------------------------------*/

	/**
	 * Returns the parent node of the given position
	 * @param p    A valid Position within the tree
	 * @return parent node
	 * @throws IllegalArgumentException
	 */
	@Override
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent();
	}

	/**
	 * Returns the number of children of a given node
	 * @param p    A valid Position within the tree
	 * @return number of children
	 * @throws IllegalArgumentException
	 */
	@Override
	/* count only direct child of the node, not further descendant. */
	public int numChildren(Position<E> p) throws IllegalArgumentException {
		int count = 0;
		if(left(p) != null)
			count++;
		if(right(p) != null)
			count++;
		return count;
	}

	/**
	 * Checks if a position is an internal node
	 * @param p    A valid Position within the tree
	 * @return true if node has 1 or more children, false otherwise
	 * @throws IllegalArgumentException
	 */
	@Override
	public boolean isInternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p) > 0;
	}

	/**
	 * Checks if node is an external node
	 * @param p    A valid Position within the tree
	 * @return true if node has 0 children, false otherwise
	 * @throws IllegalArgumentException
	 */
	@Override
	public boolean isExternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p) == 0;
	}

	/**
	 * Checks if node is root
	 * @param p    A valid Position within the tree
	 * @return true if root, false otherwise
	 * @throws IllegalArgumentException
	 */
	@Override
	public boolean isRoot(Position<E> p) throws IllegalArgumentException {
		return p == root();
	}

	/**
	 * Returns size of tree
	 * @return size of tree
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Returns true if tree is empty
	 * @return true if empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Returns left child of given node
	 * @param p A valid Position within the tree
	 * @return left child
	 * @throws IllegalArgumentException
	 */
	@Override
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getLeft();
	}

	/**
	 * Returns right child of given node
	 * @param p A valid Position within the tree
	 * @return right child
	 * @throws IllegalArgumentException
	 */
	@Override
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getRight();
	}

	/**
	 * Sets the element of a given position
	 * @param p
	 * @param element
	 */
	public void setElement(Position<E> p, E element){
        Node<E> node = validate(p);
        node.setElement(element);
	}

	/**
	 * Returns the sibling of a node if it has one
	 * @param p A valid Position within the tree
	 * @return left node if sibling is left node, right node if sibling is right node, null if no siblings
	 * @throws IllegalArgumentException
	 */
	@Override
	public Position<E> sibling(Position<E> p) throws IllegalArgumentException {
		Position<E> parent = parent(p);
		if(parent == null)
			return null;
		if(p == left(parent)){
            return right(parent);
		}
		else{
		    return left(parent);
        }
	}
	
	/* creates a root for an empty tree, storing e as element, and returns the 
	 * position of that root. An error occurs if tree is not empty. 
	 */
	public Position<E> addRoot(E e) throws IllegalStateException {
		if(!isEmpty())
			throw new IllegalStateException("Tree is not empty");

		root = creatNode(e, null, null, null);
		size = 1;
		return root;
	}
	
	/* creates a new left child of Position p storing element e, return the left child's position.
	 * If p has a left child already, throw exception IllegalArgumentExeption. 
	 */
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if(parent.getLeft() != null)
			throw new IllegalArgumentException("A left child already exists here");

		Node<E> child = creatNode(e, parent, null, null);
		parent.setLeft(child);
		size++;
		return child;
	}

	/* creates a new right child of Position p storing element e, return the right child's position.
	 * If p has a right child already, throw exception IllegalArgumentExeption. 
	 */
	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if(parent.getRight() != null)
			throw new IllegalArgumentException("A left child already exists here");

		Node<E> child = creatNode(e, parent, null, null);
		parent.setRight(child);
		size++;
		return child;
	}
	
	/* Attach trees t1 and t2 as left and right subtrees of external Position. 
	 * if p is not external, throw IllegalArgumentExeption.
	 */
	public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2)
			throws IllegalArgumentException {
		Node<E> node = validate(p);
		if(isInternal(p))
		    throw new IllegalArgumentException("p must be a leaf");
		size += t1.size() + t2.size();
		if(!t1.isEmpty()){
		    t1.root.setParent(node);
		    node.setLeft(t1.root);
		    t1.root = null;
		    t1.size = 0;
        }
		if(t2.isEmpty()){
		    t2.root.setParent(node);
		    node.setRight(t2.root);
		    t2.root = null;
		    t2.size = 0;
        }
	
	}

	/**
	 * Removes the node at a given position
	 * @param p
	 * @return entry of the node
	 */
	public E remove(Position<E> p) {
		Node<E> node = validate(p);
		// Cannot remove an internal node with 2 children
		if(numChildren(p) == 2)
		    throw new IllegalArgumentException("p has two children, must have 1 or fewer to remove");
		// Get the child of the node if it has a singular child, then set it to be the child of the given node's parent
		Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
		if(child != null)
		    child.setParent(node.getParent());
		if(node == root)
		    root = child;
		else{
		    Node<E> parent = node.getParent();
		    if(node == parent.getLeft())
		        parent.setLeft(child);
		    else
		        parent.setRight(child);
        }

        size--;		// shrink the tree

		E temp = node.getElement();
		node.setElement(null);      // Yeet the defunct node
		node.setLeft(null);
		node.setRight(null);
		node.setParent(node);

		return temp;
	}

    /*---------------------------------------------------------------------------------------------------------------*/
    /* Inner Node Class */

	protected static class Node<E> implements Position<E> {

		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;

		public Node(E element, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
			this.element = element;
			this.parent = parent;
			this.left = leftChild;
			this.right = rightChild;
		}

		public void setElement(E element) {
			this.element = element;
		}

		public Node<E> getParent() {
			return parent;
		}

		public void setParent(Node<E> parent) {
			this.parent = parent;
		}

		public Node<E> getLeft() {
			return left;
		}

		public void setLeft(Node<E> left) {
			this.left = left;
		}

		public Node<E> getRight() {
			return right;
		}

		public void setRight(Node<E> right) {
			this.right = right;
		}

		/**
		 * Returns the element stored at this position.
		 *
		 * @return the stored element
		 * @throws IllegalStateException if position no longer valid
		 */
		@Override
		public E getElement() throws IllegalStateException {
			return element;
		}
	}
}
