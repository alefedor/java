package ru.spbau.fedorov.algo;

import org.jetbrains.annotations.NotNull;

/**
 * Implementation of binary tree for storing elements
 * @param <T> type of objects to store in set
 */
public class Set<T> {
    private Node root = null;
    private int size = 0;

    private class Node {
        private T value;
        private Node left;
        private Node right;

        private Node(T t) {
            value = t;
        }

        private boolean canGoLeft() {
            return left != null;
        }

        private boolean canGoRight() {
            return right != null;
        }

        //...
    }

    /**
     * Check, whether an elements is in set
     * @param t object to search for
     * @return true if object was in the set and false otherwise
     */
    public boolean contains(@NotNull T t) {
        if (root == null)
            return false;

        Node cur = root;
        while (true) {
            if (cur.value.equals(t))
                return true;
            if (cur.value.hashCode() > t.hashCode()) {
                if (cur.canGoLeft())
                    cur = cur.left;
                else
                    return false;
            } else {
                if (cur.canGoRight())
                    cur = cur.right;
                else
                    return false;
            }
        }
    }

    /**
     * Adds object to set.
     * If there was one, nothing happens.
     * @param t object to add to set
     * @return true, if element wasn't in set and was added, false otherwise
     */
    public boolean add(@NotNull T t) {
        if (contains(t))
            return false;

        size++;
        if (root == null) {
            root = new Node(t);
        } else {
            Node cur = root;
            while (true) {
                if (cur.value.equals(t))
                    break;
                if (cur.value.hashCode() > t.hashCode()) {
                    if (cur.canGoLeft())
                        cur = cur.left;
                    else {
                        cur.left = new Node(t);
                        break;
                    }
                } else {
                    if (cur.canGoRight())
                        cur = cur.right;
                    else {
                        cur.right = new Node(t);
                        break;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Get number of elements in set
     * @return number of elements in set
     */
    public int size() {
        return size;
    }
}
