package com.info6205.webcrawler.util.HashMap;

public class Entry<K, V> {
    K key;
    V value;
    Entry<K, V> next; // For handling collisions

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.next=null;
    }

    public K getKey() {
        return this.key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return this.value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Entry<K,V> getNext() {
        return this.next;
    }

    public void setNext(Entry<K,V> next) {
        this.next = next;
    }


}
