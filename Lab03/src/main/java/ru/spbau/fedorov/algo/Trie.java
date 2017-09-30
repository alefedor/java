package ru.spbau.fedorov.algo;

import java.io.*;

/**
 * Realization of trie which can contain strings
 * consisting of uppercase and lowercase English characters
 * and quickly find them
 */
public class Trie implements Serializable{

    private Node root = new Node();

    /** Checks whether there is String in Trie
     * @param element string to search in Trie
     * @return true if there is element in Trie and false otherwise
     */
    public boolean contains(String element) {
        Node cur = root;
        for (char c : element.toCharArray()) {
            if (!cur.canGo(c))
                return false;
            cur = cur.go(c);
        }

        return cur.isEnd;
    }

    /**
     * Adds String to Trie
     * @param element string to add
     * @return true, if string is new and false otherwise
     */
    public boolean add(String element) {
        if (contains(element))
            return false;

        Node cur = root;
        for (char c : element.toCharArray()) {
            cur.num++;
            cur = cur.go(c);
        }

        cur.num++;
        cur.isEnd = true;

        return true;
    }

    /**
     * Removes String from Trie
     * @param element string to remove
     * @return true, if there was such string and false otherwise
     */
    public boolean remove(String element) {
        if (!contains(element))
            return false;

        Node cur = root;
        for (char c : element.toCharArray()) {
            cur.num--;
            cur = cur.go(c);
        }
        cur.num--;
        cur.isEnd = false;

        return true;
    }

    /**
     * Get size of Trie
     * @return number of Strings contained in Trie
     */
    public int size() {
        return root.num;
    }

    /**
     * Counts the number of strings with certain prefix
     * @param prefix the beginning of strings to count
     * @return number of elements in Trie with certain prefix
     */
    public int howManyStartsWithPrefix(String prefix) {
        Node cur = root;
        for (char c : prefix.toCharArray()) {
            if (!cur.canGo(c))
                return 0;
            cur = cur.go(c);
        }

        return cur.num;
    }

    /**
     * Converts class object to a sequence of bytes
     * @param out stream to write in
     * @throws IOException
     */
    public void serialize(OutputStream out) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(this);
        stream.close();
    }

    /**
     * Converts a sequence of bytes to a class object
     * replacing this object by a new one
     * @param in stream to read from
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(in);
        Trie tmp = (Trie) stream.readObject();
        root = tmp.root;
        stream.close();
    }

    private class Node implements Serializable{
        private Node[] next = new Node[60];
        private int num = 0;
        private boolean isEnd = false;

        private boolean canGo(char symbol) {
            if (symbol < 'A' || symbol > 'z')
                throw new IllegalArgumentException("Illegal characters");

            return next[symbol - 'A'] != null;
        }

        private Node go(char symbol) {
            if (!canGo(symbol)) {
                next[symbol - 'A'] = new Node();
            }
            return next[symbol - 'A'];
        }
    }
}
