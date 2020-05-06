package cs2321;

import net.datastructures.Entry;
import net.datastructures.Position;
import net.datastructures.SortedMap;

/**
 *
 * Alex Hromada
 * Assignment 6
 * This class is a representation of an Binary Search Tree data structure
 *
 */

public class BinarySearchTree<K extends Comparable<K>, V> extends AbstractMap<K, V> implements SortedMap<K, V> {

    /* all the data will be stored in tree*/
    LinkedBinaryTree<Entry<K, V>> tree;
    int size;  //the number of entries (mappings)

    /*
     * default constructor
     */
    public BinarySearchTree() {
        tree = new LinkedBinaryTree<>();
        size = 0;
        tree.addRoot(null);
    }

    /*
     * Return the tree. The purpose of this method is purely for testing.
     * You don't need to make any change. Just make sure to use object tree to store your entries.
     */
    public LinkedBinaryTree<Entry<K, V>> getTree() {
        return tree;
    }

    @Override
    public int size() {
        return size;
    }


    /*---------------------------------------------------------------------------------------------------------------*/
    /* Helper Methods */

    /**
     * Search tree for the given key
     *
     * @param p
     * @param key
     * @return Position of the key
     */
    private Position<Entry<K, V>> searchTree(Position<Entry<K, V>> p, K key) {
        if (tree.isExternal(p))
            return p;
        if (key.compareTo(p.getElement().getKey()) == 0)
            return p;
        else if (key.compareTo(p.getElement().getKey()) < 0)
            return searchTree(tree.left(p), key);
        else
            return searchTree(tree.right(p), key);
    }

    /**
     * Checks if the key at the position matches the argument key
     *
     * @param p
     * @param key
     * @return
     */
    private boolean match(Position<Entry<K, V>> p, K key) {
        if (p.getElement() == null)
            return false;
        else if (p.getElement().getKey().compareTo(key) == 0)
            return true;
        return false;

    }

    /**
     * Adds the new entry into the tree and expands the tree with 2 new sentinel nodes
     *
     * @param p
     * @param entry
     */
    private void expandSentinels(Position<Entry<K, V>> p, Entry<K, V> entry) {
        tree.setElement(p, entry);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    /**
     * Returns the position with the maximum key in the tree
     *
     * @param p
     * @return position with max key
     */
    private Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> walk = p;
        while (tree.isInternal(walk)) {
            walk = tree.right(walk);
        }
        return tree.parent(walk);
    }

    /**
     * Returns the position with the minimum key in the tree
     *
     * @param p
     * @return position with max key
     */
    private Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> walk = p;
        while (tree.isInternal(walk)) {
            walk = tree.left(walk);
        }
        return tree.parent(walk);
    }

    /*---------------------------------------------------------------------------------------------------------------*/
    /* Iterator/Iterable Methods */

    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey)
            throws IllegalArgumentException {
        ArrayList<Entry<K, V>> list = new ArrayList<>();
        if (fromKey.compareTo(toKey) < 0) {
            Position<Entry<K, V>> p = searchTree(tree.root(), fromKey);
            inOrder(list, p, fromKey, toKey);
        }
        return list;

    }

    /**
     * Implementation of an in order tree traversal
     *
     * @param list
     * @param p
     * @param startKey
     * @param stopKey
     */
    private void inOrder(ArrayList<Entry<K, V>> list, Position<Entry<K, V>> p, K startKey, K stopKey) {

        if (!isEmpty()) {

            if (tree.isInternal(tree.left(p)) && p.getElement().getKey().compareTo(startKey) >= 0) {
                inOrder(list, tree.left(p), startKey, stopKey);
            }

            list.addLast(p.getElement());

            if (tree.isInternal(tree.right(p)) && p.getElement().getKey().compareTo(stopKey) <= 0) {
                inOrder(list, tree.right(p), startKey, stopKey);
            }
        }
    }

    /**
     * Implementation of in order tree traversal
     *
     * @param list
     * @param p
     */
    private void inOrder(ArrayList<Entry<K, V>> list, Position<Entry<K, V>> p) {

        if (!isEmpty()) {

            if (tree.isInternal(tree.left(p))) {
                inOrder(list, tree.left(p));
            }

            list.addLast(p.getElement());

            if (tree.isInternal(tree.right(p))) {
                inOrder(list, tree.right(p));
            }
        }
    }

    /**
     * Iterable of the Entries in tree
     *
     * @return
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> list = new ArrayList<>();
        Position<Entry<K, V>> p = tree.root();
        inOrder(list, p);
        return list;
    }

    /*---------------------------------------------------------------------------------------------------------------*/

    /**
     * Returns key associated with the value
     *
     * @param key the key whose associated value is to be returned
     * @return
     */
    @Override
    @TimeComplexity("O(lg n)")
    public V get(K key) {
        Position<Entry<K, V>> p = searchTree(tree.root(), key);
        boolean exist = match(p, key);
        if (exist)
            return p.getElement().getValue();
        else
            return null;

    }

    /**
     * Inserts a new entry with the associated key, replaces value if key exists
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return null if new entry, old value if key exists
     */
    @Override
    @TimeComplexity("O(lg n)")
    public V put(K key, V value) {
        Position<Entry<K, V>> p = searchTree(tree.root(), key);
        boolean exist = match(p, key);
        Entry<K, V> newEntry = new mapEntry<>(key, value);
        if (exist) {
            V oldVal = p.getElement().getValue();
            tree.setElement(p, newEntry);
            return oldVal;
        } else {
            expandSentinels(p, newEntry);
            size++;
            return null;
        }

    }

    /**
     * Removes entry at the associated key
     *
     * @param key the key whose entry is to be removed from the map
     * @return value of entry removed, null if doesn't exist
     */
    @Override
    @TimeComplexity("O(lg n)")
    public V remove(K key) {
        Position<Entry<K, V>> p = searchTree(tree.root(), key);
        boolean exist = match(p, key);
        if (exist) {
            V value = p.getElement().getValue();
            if (tree.isInternal(tree.left(p)) && tree.isInternal(tree.right(p))) {
                Position<Entry<K, V>> replacement = treeMin(tree.right(p));
                tree.setElement(p, replacement.getElement());
                p = replacement;
            }
            Position<Entry<K, V>> leaf = (tree.isExternal(tree.left(p)) ? tree.left(p) : tree.right(p));
            tree.remove(leaf);
            tree.remove(p);
            size--;
            return value;
        } else {
            return null;
        }
    }

    /**
     * Returns the entry with smallest key in tree
     *
     * @return entry with smallest key
     */
    @Override
    public Entry<K, V> firstEntry() {
        if (isEmpty())
            return null;
        return treeMin(tree.root()).getElement();
    }

    /**
     * Returns entry with the largest key in the tree
     *
     * @return
     */
    @Override
    public Entry<K, V> lastEntry() {
        if (isEmpty())
            return null;
        return treeMax(tree.root()).getElement();
    }

    /**
     * Returns the entry with least key greater than or equal to given key
     * (or null if no such key exists).
     *
     * @param key
     * @return entry with least key greater than or equal to given key
     */
    @Override
    public Entry<K, V> ceilingEntry(K key) {
        Position<Entry<K, V>> p = searchTree(tree.root(), key);
        if (tree.isInternal(p))
            return p.getElement();
        while (!tree.isRoot(p)) {
            if (p == tree.left(tree.parent(p)))
                return tree.parent(p).getElement();
            else p = tree.parent(p);
        }

        return null;
    }

    /**
     * Returns the entry with greatest key less than or equal to given key
     * (or null if no such key exists).
     *
     * @param key
     * @return entry with greatest key less than or equal to given key
     */
    @Override
    public Entry<K, V> floorEntry(K key) {
        Position<Entry<K, V>> p = searchTree(tree.root(), key);
        if (tree.isInternal(p))
            return p.getElement();
        while (!tree.isRoot(p)) {
            if (p == tree.right(tree.parent(p)))
                return tree.parent(p).getElement();
            else p = tree.parent(p);
        }

        return null;
    }

    /**
     * Returns the entry with greatest key strictly less than given key
     * (or null if no such key exists).
     *
     * @return entry with greatest key strictly less than given (or null if no such entry)
     */
    @Override
    public Entry<K, V> lowerEntry(K key) {
        Position<Entry<K, V>> p = searchTree(tree.root(), key);
        if (tree.isInternal(p) && tree.isInternal(tree.left(p)))
            return treeMax(tree.left(p)).getElement();
        while (!tree.isRoot(p)) {
            if (p == tree.right(tree.parent(p)))
                return tree.parent(p).getElement();
            else p = tree.parent(p);
        }
        return null;
    }

    /**
     * Returns the entry with least key strictly greater than given key
     * (or null if no such key exists).
     *
     * @return entry with least key strictly greater than given (or null if no such entry)
     */
    @Override
    public Entry<K, V> higherEntry(K key) {
        Position<Entry<K, V>> p = searchTree(tree.root(), key);
        if (tree.isInternal(p) && tree.isInternal(tree.right(p)))
            return treeMin(tree.right(p)).getElement();
        while (!tree.isRoot(p)) {
            if (p == tree.left(tree.parent(p)))
                return tree.parent(p).getElement();
            else p = tree.parent(p);
        }
        return null;
    }

    /**
     * Returns true if empty, false otherwise
     * @return true if empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }


}
