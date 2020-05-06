package cs2321;

import net.datastructures.Entry;
import net.datastructures.SortedMap;

/**
 *
 * Alex Hromada
 * Assignment 6
 * This class is a representation of a Lookup Table data structure
 *
 */

public class LookupTable<K extends Comparable<K>, V> extends AbstractMap<K, V> implements SortedMap<K, V> {

    /* Use Sorted ArrayList for the Underlying storage for the map of entries.
     *
     */
    private ArrayList<Entry<K, V>> table;


    public LookupTable() {
        table = new ArrayList<>();
    }

    /**
     * Size of the map
     * @return size
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
    public boolean isEmpty() {
        return table.isEmpty();
    }

    /*---------------------------------------------------------------------------------------------------------------*/
    /* Helper Methods */

    /**
     * Return the index for the key if key exists
     * Otherwise return where key should be inserted
     * index:  0  1  2  3
     * data:  {B, C, H, K}
     * <p>
     * findIndex(C) returns 1
     * findIndex(A) returns 0 --  A, B, C, H, K      Question is [0] A?  No
     * findIndex(M) returns 4 --  valid index for now 0..3 -- return value is 4 (out of bounds)
     * is [4] M?  There is no [4]!
     *
     * @param key
     * @return
     */
    private int findIndex(K key) {
        int l, r, mid;

        l = 0;
        r = table.size() - 1;
        while (l <= r) {
            mid = (l + r) / 2;
            int ret = table.get(mid).getKey().compareTo(key);
            if (ret == 0) {
                return mid;
            } else if (ret < 0) {
                // the key in the middle is smaller than the search key
                l = mid + 1;
            } else {
                // the key in the middle is larger than the search key
                r = mid - 1;
            }
        }
        return l;
    }

    /**
     * Checks if the key at the index matches the argument key
     *
     * @param index
     * @param key
     * @return
     */
    private boolean match(int index, K key) {
        if (index < table.size() && table.get(index).getKey().compareTo(key) == 0) {
            return true;
        }
        return false;
    }

    /**
     * If index is valid, return the entry, otherwise return null
     *
     * @param index
     * @return
     */
    private Entry<K, V> getEntry(int index) {
        if (index >= 0 && index < table.size()) {
            return table.get(index);
        } else {
            return null;
        }
    }

    /*---------------------------------------------------------------------------------------------------------------*/
    /* Iterator/Iterable Methods */


    private Iterable<Entry<K,V>> snapshot(int startIndex, K stop){
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        int i = startIndex;
        while(i < table.size() && (stop == null || stop.compareTo(table.get(i).getKey()) > 0)){
            buffer.addLast(table.get(i++));
        }

        return buffer;
    }

    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) {
        return snapshot(findIndex(fromKey), toKey);
    }


    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return snapshot(0, null);
    }


    /*---------------------------------------------------------------------------------------------------------------*/

    /**
     * Gets the value of the entry with the given key
     * @param key  the key whose associated value is to be returned
     * @return value of the entry of the associated key
     */
    @Override
    @TimeComplexity("O(lg n)")
    public V get(K key) {
        int index = findIndex(key);
        boolean exist = match(index, key);
        if (exist) {
            return table.get(index).getValue();
        } else {
            return null;
        }
    }

    /**
     * Inserts new entry with given key, replaces value of the entry with given key if already exists
     * @param key    key with which the specified value is to be associated
     * @param value  value to be associated with the specified key
     * @return null if new entry, old value if key already exists
     */
    @Override
    @TimeComplexity("O(n)")
    public V put(K key, V value) {
        int index = findIndex(key);
        boolean exist = match(index, key);
        if (exist) {
            // replacement
            V oldValue = table.get(index).getValue();
            ((mapEntry) (table.get(index))).setValue(value);
            return oldValue;

        } else {
            // insertion
            table.add(index, new mapEntry<K, V>(key, value));
            return null;
        }
    }

    /**
     * Removes the entry with the given key
     * @param key  the key whose entry is to be removed from the map
     * @return value of removed entry
     */
    @Override
    @TimeComplexity("O(n)")
    public V remove(K key) {
        int index = findIndex(key);
        boolean exist = match(index, key);
        if(exist){
            return table.remove(index).getValue();
        } else{
            return null;
        }
    }

    /**
     * Returns the first entry in the map
     * @return entry at index 0
     */
    @Override
    public Entry<K, V> firstEntry() {
        return getEntry(0);
    }

    /**
     * Returns the entry last in the list
     * @return entry at index size - 1
     */
    @Override
    public Entry<K, V> lastEntry() {
        return getEntry(size() - 1);
    }


    /**
     * index:   0  1  2  3
     * key:    {B, C, H, K}
     * <p>
     * c(B) returns entry with key B	- findIndex(B) returns 0	-- That is where B is stored
     * c(D) returns entry with key H	- findIndex(D) returns 2  -- That is where H is stored
     * c(K) returns entry with key K	- findIndex(K) returns 3  -- That is where K is stored
     * c(M) returns null				- findIndex(M) returns 4	-- That is an index that has nothing in it
     *
     * @param key
     * @return entry with least key that is greater than or equal to the given key
     */
    @Override
    @TimeComplexity("O(lg n)")
    public Entry<K, V> ceilingEntry(K key) {
        int index = findIndex(key);
        return getEntry(index);
    }

    /**
     * Returns the entry with greatest key that is less than or equal to the given key
     * @param key
     * @return entry with greatest key that is less than or equal to the given key
     */
    @Override
    @TimeComplexity("O(n)")
    public Entry<K, V> floorEntry(K key) {
        int index = findIndex(key);
        boolean exist = match(index, key);
        if(!exist)
            index--;
        return getEntry(index);

    }

    /**
     * Returns the entry with greatest key that is strictly less than the given key
     * @param key
     * @return entry with greatest key that is strictly less than the given key
     */
    @Override
    public Entry<K, V> lowerEntry(K key) {
        return getEntry(findIndex(key) - 1);
    }

	/**
	 * index:   0  1  2  3
	 * key:    {B, C, H, K}
	 * <p>
	 * h(B) returns entry with key C		- findIndex(B) returns 0  -- That is where B is stored
	 * h(D) returns entry with key H		- findIndex(D) returns 2  -- That is where H is stored
	 * h(K) returns entry with key null		- findIndex(K) returns 3  -- That is where K is stored
	 * h(A) returns entry with key B		- findIndex(A) returns 0  -- That is where B is stored
	 *
	 * @param key
	 * @return entry with least key that is strictly greater than the given key
	 */
    @Override
    public Entry<K, V> higherEntry(K key) {
		int index = findIndex(key);
		boolean exist = match(index, key);
		if(exist)
		    index++;
		return getEntry(index);
    }



}
