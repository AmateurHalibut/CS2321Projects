package cs2321;

import net.datastructures.Entry;
import net.datastructures.Map;

/**
 *
 * Alex Hromada
 * Assignment 6
 * This class is a representation of an HashMap data structure
 *
 */

public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    /* Use Array of UnorderedMap<K,V> for the Underlying storage for the map of entries.
     *
     */
    private UnorderedMap<K, V>[] table;
    int size;  // number of mappings(entries)
    int capacity; // The size of the hash table.
    int DefaultCapacity = 17; //The default hash table size

    /* Maintain the load factor <= 0.75.
     * If the load factor is greater than 0.75,
     * then double the table, rehash the entries, and put then into new places.
     */
    double loadfactor = 0.75;


    /**
     * Constructor that takes a hash size
     *
     * @param hashtable size: the number of buckets to initialize
     */
    public HashMap(int hashtablesize) {
        size = 0;
        capacity = hashtablesize;
        createTable();

    }

    /**
     * Constructor that takes no argument
     * Initialize the hash table with default hash table size: 17
     */
    public HashMap() {
        size = 0;
        capacity = DefaultCapacity;
        createTable();
    }

    /* This method should be called by map an integer to the index range of the hash table
     */
    private int hashValue(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /*
     * The purpose of this method is for testing if the table was doubled when rehashing is needed.
     * Return the the size of the hash table.
     * It should be 17 initially, after the load factor is more than 0.75, it should be doubled.
     */
    public int tableSize() {
        return this.capacity;
    }

    /**
     * Returns amount of entries in hash table
     *
     * @return amount of entries in table
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Returns true if empty, false otherwise
     *
     * @return true if empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the value of the entry with the associated key
     *
     * @param key the key whose associated value is to be returned
     * @return value of entry
     */
    @Override
	@TimeComplexityExpected("O(1)")
    public V get(K key) {
        return getBucket(hashValue(key), key);
    }

    /**
     * Inserts a new entry into the table with the associated key and value, replaces value if key exists
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return Old value, null if new entry
     */
    @Override
	@TimeComplexityExpected("O(1)")
    public V put(K key, V value) {
        V oldVal = putBucket(hashValue(key), key, value);
        if (size > loadfactor * capacity) {
            resize(capacity);
        }
        return oldVal;
    }

    /**
     * Removes the entry with the given key
     *
     * @param key the key whose entry is to be removed from the map
     * @return value of removed entry
     */
    @Override
	@TimeComplexityExpected("O(1)")
    public V remove(K key) {
        return removeBucket(hashValue(key), key);
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> list = new ArrayList<>();
        for (int h = 0; h < capacity; h++)
            if (table[h] != null)
                for (Entry<K, V> e :
                        table[h].entrySet())
                    list.addLast(e);
        return list;
    }

    /*---------------------------------------------------------------------------------------------------------------*/
    /* Helper Methods */

    /**
     * Creates a new hash table the size of capacity
     */
    private void createTable() {
        table = (UnorderedMap<K, V>[]) new UnorderedMap[capacity];
    }

    /**
     * Resizes the table if it reaches capacity
     *
     * @param capacity
     */
    private void resize(int capacity) {
        ArrayList<Entry<K, V>> list = new ArrayList<>(size);  // Temp list to store values of the hash table
        for (Entry<K, V> e :
                entrySet()) {
            list.addLast(e);
        }
        this.capacity = (capacity * 2);    // Resize the table
        createTable();    // Create new table with the new size
        size = 0;

        // copy all values into the new table at their associated hash values
        for (Entry<K, V> e :
                list) {
            put(e.getKey(), e.getValue());
        }
    }

    /**
     * Helper method to retrieve a value with associated hash and key
     *
     * @param h
     * @param key
     * @return value
     */
    private V getBucket(int h, K key) {
        UnorderedMap<K, V> bucket = table[h];
        if (bucket == null)
            return null;
        return bucket.get(key);
    }

    /**
     * Inserts an entry into a bucket with associated key and hash
     *
     * @param h
     * @param key
     * @param value
     * @return old value
     */
    private V putBucket(int h, K key, V value) {
        UnorderedMap<K, V> bucket = table[h];
        if (bucket == null) {
            bucket = table[h] = new UnorderedMap<>();
        }
        int oldSize = bucket.size();
        V oldVal = bucket.put(key, value);
        size = size + (bucket.size() - oldSize);
        return oldVal;
    }

    /**
     * Removes the entry from a bucket at associated key and hash
     *
     * @param h
     * @param key
     * @return value of entry removed
     */
    private V removeBucket(int h, K key) {
        UnorderedMap<K, V> bucket = table[h];
        if (bucket == null)
            return null;
        int oldSize = bucket.size();
        V oldVal = bucket.remove(key);
        size = size - (oldSize - bucket.size());
        return oldVal;
    }

}
