package ru.spbau.fedorov.algo;

/**
 * Simple implementation of stack using list
 * @param <T> type of elements to store
 */
public class Stack<T>{
    private Node root = null;

    /**
     *Tests if this stack is empty.
     */
    public boolean empty() {
        return root == null;
    }

    /**
     * Looks at the object at the top of this stack without removing it from the stack.
     * @return Object at the top of the Stack
     */
    public T peek() {
        if (root == null)
            throw new EmptyStackException("Tried to get element from stack while it is empty");

        return root.value;
    }

    /**
     * Removes the object at the top of this stack and returns that object as the value of this function.
     * @return object removed from stack
     */
    public T pop() {
        if (root == null)
            throw new EmptyStackException("Tried to pop element from stack while it is empty");

        T result = root.value;
        root = root.next;
        return result;
    }

    /**
     * Pushes an item onto the top of this stack.
     * @param item object to push on stack
     * @return object pushed to stack
     */
    public T push(T item) {
        root = new Node(root, item);
        return item;
    }

    /**
     * Erases all elements from stack
     */
    public void clear() {
        root = null;
    }

    private class Node {
        private Node next;
        private T value;

        private Node(Node last, T v) {
            next = last;
            value = v;
        }
    }

}
