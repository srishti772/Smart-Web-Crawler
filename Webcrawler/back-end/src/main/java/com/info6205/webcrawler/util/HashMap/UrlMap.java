package com.info6205.webcrawler.util.HashMap;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.info6205.webcrawler.util.ArrayBag.ArrayBag;
import com.info6205.webcrawler.util.ArrayBag.BagInterface;

@Component
public class UrlMap<K,V> implements UrlMapInterface<K,V> {
    private static final int DEFAULT_CAPACITY = 500;
    private Entry<K, V>[] buckets;
    private int numberOfEntries;
    boolean initialized=false;
    private static final int MAX_CAPACITY = 10000;
    

    public UrlMap() {
        this(DEFAULT_CAPACITY);
       
    }

    /**
     * Constructs a new UrlMap with the specified initial capacity.
     * 
     * @param desiredCapacity The desired initial capacity of the UrlMap.
     * @throws IllegalStateException if the desired capacity exceeds the maximum allowed capacity.
     */
    @SuppressWarnings("unchecked")
    public UrlMap(int desiredCapacity){
        if (desiredCapacity <= MAX_CAPACITY) {
           
    
            buckets  = new Entry[desiredCapacity];;
            numberOfEntries = 0;
            initialized = true;
        }
        else
            throw new IllegalStateException("Attempt to create a bag " +
                                            "whose capacity exceeds " +
                                            "allowed maximum.");
    }

    /**
     * Adds a key-value pair to the UrlMap.
     * 
     * @param key The key of the entry to be added.
     * @param value The value of the entry to be added.
     * @return true if the entry was successfully added, false otherwise.
     */
    @Override
    public boolean put(K key, V value) {
        checkInitialization();
           
        if (isBucketFull()) {
            doubleCapacity();
        } 
        
        int index = hash(key);
        Entry<K,V> newEntry = new Entry<>(key, value);
        
        if (buckets[index] == null) {
            buckets[index] = newEntry; // No collision, directly add the entry
            numberOfEntries++;
            return true;
        } else {
            Entry<K, V> current = buckets[index];
            
            while (current != null) {
                if (current.getKey().equals(key)) {
                    current.setValue(value); // Update value if key exists
                    return true;
                }
                
                if (current.getNext() == null) {
                    current.setNext(newEntry); // Add to the end of the linked list
                    numberOfEntries++;
                    return true;
                }
                
                current = current.getNext();
            }
            
            return false; 
        }
    }

    /**
     * Retrieves the value associated with the specified key from the UrlMap.
     * 
     * @param key The key whose associated value is to be retrieved.
     * @return The value associated with the key, or null if the key is not found.
     */
    @Override
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null; // Key not found
    }

    /**
     * Removes the entry with the specified key from the UrlMap.
     * 
     * @param key The key of the entry to be removed.
     * @return true if the entry was successfully removed, false otherwise.
     */
    @Override
    public boolean remove(K key) {
        int index = hash(key);
        Entry<K, V> current = buckets[index];
        Entry<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = current.next; // Remove first element

                } else {
                    prev.next = current.next; // Remove from linked list
                }
                numberOfEntries--;
                return  true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    /**
     * Checks if the UrlMap is empty.
     * 
     * @return true if the UrlMap is empty, false otherwise.
     */
    @Override
    public boolean isEmpty(){
        return numberOfEntries==0;
    }

    /**
     * Retrieves the number of entries in the UrlMap.
     * 
     * @return The number of entries in the UrlMap.
     */
    @Override
    public int size(){
        return numberOfEntries;
    }

    /**
     * Retrieves a bag containing all keys in the UrlMap.
     * 
     * @return A bag containing all keys in the UrlMap.
     */
    @Override
    public BagInterface<K> getKeys(){
        checkInitialization();

        BagInterface<K> keys=new ArrayBag<>();
        
        for (Entry<K, V> entry : buckets) {
            Entry<K, V> current = entry;
            while (current != null) {
                keys.add(current.getKey());
                 current = current.getNext();
            }
        }

        return keys;
    }

    /**
     * Retrieves a bag containing all values in the UrlMap.
     * 
     * @return A bag containing all values in the UrlMap.
     */
    @Override
    public BagInterface<V> getValues(){
        checkInitialization();

        BagInterface<V> values=new ArrayBag<>();
        
        for (Entry<K, V> entry : buckets) {
            Entry<K, V> current = entry;
            while (current != null) {
                values.add(current.getValue());
                current = current.getNext();
            }
        }

        return values;
    }

    /** 
     * Throws an exception if this object is not initialized.
     */
    private void checkInitialization() {
        if (!initialized)
              throw new SecurityException("UrlMap object is not initialized properly.");
    }

    /**
     * Checks if the buckets array is full.
     * 
     * @return true if the buckets array is full, false otherwise.
     */
    private boolean isBucketFull() {
        return numberOfEntries >= buckets.length;
    }

    /**
     * Doubles the capacity of the UrlMap.
     */
    private void doubleCapacity() {
		int newLength = 2 * buckets.length;
		checkSize(newLength);
		buckets = Arrays.copyOf(buckets, newLength);
	}

    /**
     * Calculates the hash value for the given key.
     * 
     * @param key The key for which to calculate the hash value.
     * @return The hash value for the key.
     */
    private int hash(K key) {
        // Ensure key is not null
    if (key == null) {
        throw new IllegalArgumentException("Key cannot be null.");
    }

    // Use Java's Objects.hash method to calculate the hash code of the key
    int hashCode = Objects.hash(key);

    // Apply a hash function using a prime number multiplier to ensure better distribution
    int hash = (hashCode * 31) % buckets.length;

    return Math.abs(hash);
    }

    /**
     * Checks if size of the UrlMap exceeds maximum allowed capacity and throws exception if it does.
     * 
     * @param size The size to be checked.
     */
    private void checkSize(int size) {
        if (size > MAX_CAPACITY)
            throw new IllegalStateException(
                    "Attempt to create a UrlMap whose " + "capacity exceeds allowed maximum of " + MAX_CAPACITY);
    }
    /**
     * Checks if the UrlMap contains the specified key.
     * 
     * @param key The key to be checked.
     * @return true if the UrlMap contains the key, false otherwise.
     */

    public boolean containsKey(K key) {
        checkInitialization();
        

           int index = hash(key);
           Entry<K, V> current = buckets[index];
              
           while (current != null) {
               if (current.getKey().equals(key)) {
                   return true; // Key found
               }
               current = current.getNext();
           }

           return false; // Key not found
   }
    /**
     * Generates a string representation of the UrlMap.
     * 
     * @return A string representation of the UrlMap.
     */
  

   @Override
   public String toString(){
       StringBuilder sb=new StringBuilder("UrlMap [\n");        
                    
       for (Entry<K, V> entry : buckets) {
           Entry<K, V> current = entry;
           while (current != null) {
                 sb.append(current.getKey()+" : ");
                 sb.append(current.getValue()+"\n");
               current = current.getNext();
           }
       }
       sb.append("]");
       
       
       return sb.toString();
   }
    /**
     * Clears all entries from the UrlMap.
     */
   @Override
   public void clear() {
    if(numberOfEntries>0){
        for(K k : getKeys().toArray()){
            System.out.println("Removing "+k);
            remove(k);
           }
    }
      
      
   }
   

   
}
