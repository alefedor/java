package ru.spbau.fedorov.algo;

/**
 * Forward linked list
 */
public class List {

    private Node head = null;

    private class Node {
        public Node next;
        Object value;
        Object key;

        public Node(Object k, Object v) {
            key = k;
            value = v;
            next = null;
        }
    }

    /**
     * Checks whether List is empty
     *
     * @return true if there are no elements in list and false otherwise
     */
    public boolean empty() {
        return head == null;
    }

    /**
     * Adds pair (Key, Value) to the head of the List
     *
     * @param key first Object to be contained in Node
     * @param value second Object to be contained in Node
     */
    public void insert(Object key, Object value) {
        Node prev = head;
        head = new Node(key, value);
        head.next = prev;
    }

    /**
     * Searches for the pair with special first Object
     *
     * @param key object to be searched for
     * @return second Object or null if there is no such Object
     */
    public Object find(Object key) {
        Node cur = head;
        while (cur != null){
            if (cur.key.equals(key))
                return cur.value;
            cur = cur.next;
        }
        return null;
    }

    /**
     * Deletes pair with special first Object
     *
     * @param key object to be searched for
     * @return second Object or null if there is no such Object
     */
    public Object erase(Object key) {
        if (head == null)
            return null;

        if (head.key.equals(key)){
            Object res = head.value;
            head = head.next;
            return res;
        }

        Node cur = head;
        while (cur.next != null) {
            if (cur.next.key.equals(key)) {
                Object res = cur.next.value;
                cur.next = cur.next.next;
                return res;
            }
            cur = cur.next;
        }

        return null;
    }

    /**
     * Deletes the pair (Key, Value) in the head of the List
     *
     * @return Array with exactly two Objects: Key, Value of the deleted pair
     */
    public Object[] pop() {
        Object[] res = {head.key, head.value};
        head = head.next;
        return res;
    }

    /**
     * Deletes all nodes in List
     */
    public void clear() {
        head = null;
    }
}