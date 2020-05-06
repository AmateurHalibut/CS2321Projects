package cs2321;

import java.util.Comparator;

import net.datastructures.*;
/**
 * A Adaptable PriorityQueue based on an heap. 
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author Alex Hromada
 */

public class HeapPQ<K,V> implements AdaptablePriorityQueue<K,V> {
	
	private int size = 0;
	private Comparator<K> comp = null;
	private ArrayList<Entry<K,V>> heap = new ArrayList<>();
	
	/* use default comparator, see DefaultComparator.java */
	public HeapPQ() {
		this(new DefaultComparator<K>());
	}
	
	/* use specified comparator */
	public HeapPQ(Comparator<K> c) {
		comp = c;
	}
	
	
	/* 
	 * Return the data array that is used to store entries  
	 * This method is purely for testing purpose of auto-grader
	 */
	Object[] data() {
		return heap.getData();
	}
	
	/**
	 * The entry should be bubbled up to its appropriate position 
	 * @param int move the entry at index j higher if necessary, to restore the heap property
	 */
	public void upheap(int j){

		// Base Case
		if(j == 0) return;

		// Recursive Step
		// j has a parent
		int p = parent(j);
		if(comp.compare(heap.get(j).getKey(), heap.get(p).getKey()) < 0){
			swapButForAdaptablePQ(j,p);
			upheap(p);
		}
	}
	
	/**
	 * The entry should be bubbled down to its appropriate position 
	 * @param int move the entry at index j lower if necessary, to restore the heap property
	 */
	public void downheap(int j){

		// Base Case
		if(hasLeft(j) == false) return;

		// Recursive Step
		// j has children
		int s = left(j);

		// If j has a right child, compare left and right children
		if(hasRight(j)){
			int r = right(j);
			if(comp.compare(heap.get(r).getKey(), heap.get(s).getKey()) < 0){
				s = r;
			}
		}

		if(comp.compare(heap.get(s).getKey(), heap.get(j).getKey()) < 0){
			swapButForAdaptablePQ(s,j);
			downheap(s);
		}
	}

	/**
	 * Compares parent with child and upheaps or downheaps based on which is bigger
	 * @param j
	 */
	public void bubble(int j){
		if (j > 0 && comp.compare(heap.get(j).getKey(), heap.get(parent(j)).getKey()) < 0){
			upheap(j);
		}
		else{
			downheap(j);
		}
	}

	/**
	 * Returns size of the heap
	 * @return size of the heap
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Return true if the heap is empty, false otherwise
	 * @return true if heap is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}


	/**
	 * Checks if key is of a comparable type
	 * @param key
	 * @return true if key is of comparable type
	 * @throws IllegalArgumentException
	 */
	private boolean checkKey(K key) throws IllegalArgumentException{
		try{
			return(comp.compare(key, key) == 0);
		} catch(ClassCastException e){
			throw new IllegalArgumentException("Incompatible key");
		}
	}

	/**
	 * Checks if the entry is an instance of a PQEntry and casts it to a PQEntry
	 * @param entry
	 * @return
	 * @throws IllegalArgumentException
	 */
	private PQEntry<K,V> validate(Entry<K,V> entry) throws IllegalArgumentException{
		if(!(entry instanceof PQEntry)){
			throw new IllegalArgumentException("Invalid Entry");
		}
		PQEntry<K,V> locator = (PQEntry<K,V>) entry;
		int j = locator.getIndex();
		if (j >= heap.size() || heap.get(j) != locator){
			throw new IllegalArgumentException("Invalid entry");
		}
		return locator;
	}

	/**
	 * Inserts a new entry at the end of the heap
	 * @param key     the key of the new entry
	 * @param value   the associated value of the new entry
	 * @return the new entry
	 * @throws IllegalArgumentException
	 */
	@Override
	@TimeComplexity("O(n)")
	@TimeComplexityAmortized("O(lg n)")
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		checkKey(key);
		Entry<K,V> e = new PQEntry<>(key, value, heap.size());
		heap.addLast(e);

		upheap(heap.size() - 1);

		size++;
		return e;
	}

	/**
	 * Returns the entry with the minimum key in the stack
	 * @return entry with minimum key
	 */
	@Override
	@TimeComplexity("O(1)")
	public Entry<K, V> min() {
		if(isEmpty()){
			return null;
		}
		return heap.get(0);
	}

	/**
	 * Removes the entry with the minimum key from the heap
	 * @return entry with min key
	 */
	@Override
	@TimeComplexity("O(lg n)")
	public Entry<K, V> removeMin() {
		if(isEmpty()){
			return null;
		}

		Entry<K,V> min = min();
		heap.set(0, heap.get(heap.size() - 1));
		((PQEntry<K,V>) heap.get(0)).setIndex(0);

		heap.removeLast();
		downheap(0);

		size--;
		return min;
	}

	/**
	 * Removes an entry from the heap
	 * @param entry  an entry of this priority queue
	 * @throws IllegalArgumentException
	 */
	@Override
	@TimeComplexity("O(lg n)")
	public void remove(Entry<K, V> entry) throws IllegalArgumentException {
		PQEntry<K,V> locator = validate(entry);
		int j = locator.getIndex();

		if(j == heap.size() - 1){
			heap.removeLast();
		}
		else{
			swapButForAdaptablePQ(j, heap.size() - 1);
			heap.removeLast();
			bubble(j);
		}
		size--;
	}

	/**
	 * Replaces the key of an entry in the heap
	 * @param entry  an entry of this priority queue
	 * @param key    the new key
	 * @throws IllegalArgumentException
	 */
	@Override
	@TimeComplexity("O(lg n)")
	public void replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException {
		PQEntry<K,V> locator = validate(entry);
		checkKey(key);
		locator.setKey(key);
		bubble(locator.getIndex());
	}

	/**
	 * Replace the value in a given entry
	 * @param entry  an entry of this priority queue
	 * @param value  the new value
	 * @throws IllegalArgumentException
	 */
	@Override
	@TimeComplexity("O(lg n)")
	public void replaceValue(Entry<K, V> entry, V value) throws IllegalArgumentException {
		PQEntry<K,V> locator = validate(entry);
		locator.setValue(value);
	}


	/**
	 * Returns index of the givens index's parent
	 * @param i current index
	 * @return	index of the parent
	 */
	private int parent(int i){
		return (i - 1) / 2;
	}

	/**
	 * Returns the left child of the current index
	 * @param i current index
	 * @return index of the left child
	 */
	private int left(int i){
		return (i * 2) + 1;
	}

	/**
	 * Returns the right child of the current index
	 * @param i current index
	 * @return index of the right child
	 */
	private int right(int i){
		return (i * 2) + 2;
	}

	/**
	 * Checks if the current index has a left child
	 * @param i current index
	 * @return true if has a left child, false otherwise
	 */
	private boolean hasLeft(int i){
		if (left(i) < heap.size()){
			return true;
		}
		return false;
	}

	/**
	 * Checks if the current index has a right child
	 * @param i current index
	 * @return true if has a right child, false otherwise
	 */
	private boolean hasRight(int i){
		if(right(i) < heap.size()){
			return true;
		}
		return false;
	}

	/**
	 * Swaps 2 entries in the heap
	 * @param i 1st index
	 * @param j 2nd index
	 */
	private void swap(int i, int j){
		Entry<K,V> temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}

	/**
	 * Calls swap and sets the new indices
	 * @param i 1st index
	 * @param j 2nd index
	 */
	private void swapButForAdaptablePQ(int i, int j){
		swap(i,j);
		((PQEntry<K,V>) heap.get(i)).setIndex(i);
		((PQEntry<K,V>) heap.get(j)).setIndex(j);
	}

	/**
	 * Nested PQEntry class
	 * @param <K>
	 * @param <V>
	 */
	private static class PQEntry<K,V> implements Entry<K,V>{

		private K k;
		private V v;
		private int index;

		public PQEntry(K key, V value, int j) {
			this.k = key;
			this.v = value;
			this.index = j;
		}

		/**
		 * Returns key
		 * @return key
		 */
		public K getKey() {
			return k;
		}

		/**
		 * Sets key
		 * @param k new key
		 */
		public void setKey(K k) {
			this.k = k;
		}

		/**
		 * Returns value
		 * @return value
		 */
		public V getValue() {
			return v;
		}

		/**
		 * Sets value
		 * @param v new value
		 */
		public void setValue(V v) {
			this.v = v;
		}


		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}


}
