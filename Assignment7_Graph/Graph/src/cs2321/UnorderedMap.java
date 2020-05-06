package cs2321;


import net.datastructures.Entry;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * Alex Hromada
 * Assignment 6
 * This class is a representation of an Unordered Map data structure
 *
 */

public class UnorderedMap<K,V> extends AbstractMap<K,V> {
	
	/* Use ArrayList or DoublyLinked list for the Underlying storage for the map of entries.
	 * Uncomment one of these two lines;
	 * private ArrayList<Entry<K,V>> table; 
	 * private DoublyLinkedList<Entry<K,V>> table;
	 */

	private ArrayList<Entry<K,V>> table;


	public UnorderedMap() {
		table = new ArrayList<>();
	}

	/**
	 * Return size of map
	 * @return size of unordered map
	 */
	@Override
	public int size() {
		return table.size();
	}

	/**
	 * Checks if map is empty
	 * @return true if empty, false otherwise
	 */
	@Override
	@TimeComplexity("O(n)")
	public boolean isEmpty() {
		return table.isEmpty();
	}

	/**
	 * Searches for the index of the associated key
	 * @param key
	 * @return index if exists, -1 otherwise
	 */
	private int findIndex(K key){
		for(int i = 0; i < size(); i++){
			if(table.get(i).getKey().equals(key)){
				return i;
			}
		} return -1;
	}

	/**
	 * Returns the value of the entry of the given key
	 * @param key  the key whose associated value is to be returned
	 * @return value of the entry, null if not found
	 */
	@Override
	@TimeComplexity("O(n)")
	public V get(K key) {
		int index = findIndex(key);
		if(index == -1)
			return null;
		return table.get(index).getValue();
	}

	/**
	 * Inserts a new entry with associated key/value into the map
	 * @param key    key with which the specified value is to be associated
	 * @param value  value to be associated with the specified key
	 * @return value of the old entry of replaced, null if new entry
	 */
	@Override
	@TimeComplexity("O(n)")
	public V put(K key, V value) {
		int index = findIndex(key);
		if(index == -1){
			table.addLast(new mapEntry<>(key, value));
			return null;
		} else{
			V oldVal = table.get(index).getValue();
			((mapEntry) table.get(index)).setValue(value);
			return oldVal;
		}

	}

	/**
	 * Removes the entry with the given key
	 * @param key  the key whose entry is to be removed from the map
	 * @return value of entry if removed, null otherwise
	 */
	@Override
	public V remove(K key) {
		int index = findIndex(key);
		if(index == -1)
			return null;

		V value = table.get(index).getValue();
		if(index != size() - 1)
			table.set(index, table.get(size() - 1));

		table.removeLast();

		return value;
	}

	private class EntryIterator implements Iterator<Entry<K,V>>{

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
			return index < table.size();
		}

		/**
		 * Returns the next element in the iteration.
		 *
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public Entry<K, V> next() {
			if(index == table.size())
				throw new NoSuchElementException();
			return table.get(index++);
		}

	}

	private class EntryIterable implements Iterable<Entry<K,V>>{
		/**
		 * Returns an iterator over elements of type {@code T}.
		 *
		 * @return an Iterator.
		 */
		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new EntryIterator();
		}
	}

	/**
	 * Makes map iterable over its set of entries
	 * @return new EntryIterable
	 */
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return new EntryIterable();
	}

}
