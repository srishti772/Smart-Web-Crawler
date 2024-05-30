package com.info6205.webcrawler.util.HashMap;

import com.info6205.webcrawler.util.ArrayBag.BagInterface;

public interface UrlMapInterface<K, V> {
    /**
     * Adds a key-value pair to the UrlMap.
     * 
     * @param key The key of the entry to be added.
     * @param value The value of the entry to be added.
     * @return true if the entry was successfully added, false otherwise.
     */
    boolean put(K key, V value);

    /**
     * Retrieves the value associated with the specified key from the UrlMap.
     * 
     * @param key The key whose associated value is to be retrieved.
     * @return The value associated with the key, or null if the key is not found.
     */
    V get(K key);

    /**
     * Removes the entry with the specified key from the UrlMap.
     * 
     * @param key The key of the entry to be removed.
     * @return true if the entry was successfully removed, false otherwise.
     */
    boolean remove(K key);

    /**
     * Checks if the UrlMap is empty.
     * 
     * @return true if the UrlMap is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Retrieves the number of entries in the UrlMap.
     * 
     * @return The number of entries in the UrlMap.
     */
    int size();

    /**
     * Retrieves a bag containing all keys in the UrlMap.
     * 
     * @return A bag containing all keys in the UrlMap.
     */
    BagInterface<K> getKeys();

    /**
     * Retrieves a bag containing all values in the UrlMap.
     * 
     * @return A bag containing all values in the UrlMap.
     */
    BagInterface<V> getValues();
  /**
     * Clears all entries from the UrlMap.
     */
    public void clear();
    /**
     * Checks if the UrlMap contains the specified key.
     * 
     * @param key The key to be checked.
     * @return true if the UrlMap contains the key, false otherwise.
     */
    public boolean containsKey(K key) ;
}