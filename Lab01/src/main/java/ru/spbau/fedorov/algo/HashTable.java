package ru.spbau.fedorov.algo;

import ru.spbau.fedorov.algo.List;

/**
 * HashTable implemented using separate chaining and forward list
 */
public class HashTable {
    private static int PRIME = 43;
    private static int START_CAPACITY = 11;

    private List[] data = new List[START_CAPACITY];
    private int size = 0;
    private int capacity = START_CAPACITY;

    /**
     * Get size
     *
     * @return Number of keys in HashTable
     */
    public int size() {
        return size;
    }

    /**
     * Checks, whether there is an element in HashTable
     * with such key
     * @param key String to be searched for
     * @return true if there is such element and false otherwise
     */
    public boolean contains(String key) {
        int hash = getHash(key);
        return data[hash] != null && data[hash].find(key) != null;
    }

    /**
     * Gets value by key in HashTable
     *
     * @param key String to be searched for
     * @return found string or null if there is no such element
     */
    public String get(String key) {
        int hash = getHash(key);
        if (data[hash] == null)
            return null;
        return data[hash].find(key);
    }

    /**
     * Puts pair <Key, Value> in HashTable
     *
     * @param key String by which you would access the element in HashTable
     * @param value String to be stored
     * @return previous value with such key or null if there is no one
     */
    public String put(String key, String value) {
        int hash = getHash(key);
        if (data[hash] == null)
            data[hash] = new List();

        String result = data[hash].erase(key);
        if (result == null)
            size++;
        data[hash].insert(key, value);

        if (capacity < size * 2)
            rebuild();

        return result;
    }

    /**
     * Removes pair <Key, Value> determined by key
     *
     * @param key String to be searched for
     * @return deleted value with such key or null if there is no one
     */
    public String remove(String key) {
        int hash = getHash(key);
        if (data[hash] == null)
            return null;

        String result = data[hash].erase(key);
        if (result != null)
            size--;

        return result;
    }

    /**
     * Removes all elements from HashTable
     */
    public void clear() {
        for (List list : data)
            if (list != null)
                list.clear();

        size = 0;
    }

    private int getHash(String s) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++){
            hash = (hash * PRIME + s.charAt(i)) % capacity;
        }
        return (int)hash;
    }

    private void rebuild() {
        capacity *= 2;
        size = 0;

        List[] oldData = data;
        data = new List[capacity];

        for (List list : oldData)
            if (list != null) {
                while (!list.empty()){
                    String[] elem = list.pop();
                    put(elem[0], elem[1]);
                }
            }
    }
}

