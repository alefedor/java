package ru.spbau.fedorov.algo;

import com.sun.istack.internal.NotNull;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Implementation of set using binary tree and a list over it
 * @param <E> type of key to store
 */
public class MyTreeSet<E extends Comparable<E>> extends AbstractSet<E> implements IMyTreeSet<E>{

    private Comparator<? super E> comparator;
    private Node root;
    private Node first = null;
    private Node last = null;
    private int size = 0;
    private boolean reverseOrder = false;

    /**
     * Constructs empty set with natural comparator
     */
    public MyTreeSet() {
        comparator = Comparator.naturalOrder();
    }

    /**
     * Constructs empty set with given comparator
     * @param cmpr comparator to use
     */
    public MyTreeSet(@NotNull Comparator<? super E> cmpr) {
        comparator = cmpr;
    }

    private MyTreeSet(@NotNull MyTreeSet<E> st) {
        root = st.root;
        first = st.first;
        last = st.last;
        size = st.size();
        reverseOrder = !st.reverseOrder;
        comparator = st.comparator;
    }

    private int compare(E x, E y) {
        return comparator.compare(x, y);
    }

    private Node getNode(E key) {
        Node cur = root;
        int cmpr = 0;
        while (cur != null) {
            cmpr = compare(cur.key, key);
            if (cmpr == 0)
                return cur;
            cur = cmpr == -1 ? cur.right : cur.left;
        }
        return null;
    }

    private void hang(Node par, Node last, Node n) {
        if (par != null) {
            if (par.left == last)
                par.left = n;
            else
                par.right = n;
        }
        if (n != null)
            n.parent = par;
    }

    private void swapKeys(@NotNull Node a, @NotNull Node b){
        E x = a.key;
        a.key = b.key;
        b.key = x;
    }

    private Node placeToInsert(@NotNull Node v, E key) {
        if (compare(key, v.key) < 0) {
            if (v.left == null)
                return v;
            else
                return placeToInsert(v.left, key);
        }else{
            if (v.right == null)
                return v;
            else
                return  placeToInsert(v.right, key);
        }
    }

    @Override
    public boolean add(@NotNull E key) {
        if (contains(key))
            return false;

        size++;

        if (root == null){
            root = new Node(key);
            first = root;
            last = root;
            return true;
        }

        Node node = placeToInsert(root, key);
        int cmpr = compare(node.key, key);

        Node newNode = new Node(key);
        newNode.parent = node;
        if (cmpr == -1) { //to right
            newNode.next = node.next;
            if (node.next != null)
                node.next.prev = newNode;

            node.next = newNode;
            newNode.prev = node;

            node.right = newNode;
            if (node == last)
                last = newNode;
        } else { //to left
            newNode.prev = node.prev;
            if (node.prev != null)
                node.prev.next = newNode;

            node.prev = newNode;
            newNode.next = node;

            node.left = newNode;
            if (node == first)
                first = newNode;
        }

        return true;
    }

    @Override
    public boolean contains(@NotNull Object key) {
        Node node = getNode((E)key);
        return node != null;
    }

    @Override
    public boolean remove(@NotNull Object key) {
        Node node = getNode((E)key);
        if (node == null)
            return false;

        if (size == 1){
            root = null;
            return true;
        }
        if (node == first)
            first = node.next;
        if (node == last)
            last = node.prev;

        size--;
        if (node.left == null){
            hang(node.parent, node, node.right);
        }else{
            swapKeys(node.prev, node);
            hang(node.prev.parent, node.prev, node.prev.left);
            if (node.prev.prev != null)
                node.prev.prev.next = node;
            node.prev = node.prev.prev;
        }
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        if (reverseOrder)
            return new MyTreeSetDescendingIterator();
        else
            return new MyTreeSetIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> descendingIterator() {
        if (reverseOrder)
            return new MyTreeSetIterator();
        else
            return new MyTreeSetDescendingIterator();

    }

    @Override
    public MyTreeSet<E> descendingSet() {
        return new MyTreeSet<>(this);
    }

    @Override
    public E first() {
        if (!reverseOrder)
            return first.key;
        else
            return last.key;
    }

    @Override
    public E last() {
        if (reverseOrder)
            return first.key;
        else
            return last.key;
    }

    private Node getMatching(Node v, @NotNull Function<E, Boolean> predicate, boolean lower) {
        if (v == null)
            return null;

        if (predicate.apply(v.key)){
            Node result;
            if (lower)
                result = getMatching(v.right, predicate, lower);
            else
                result = getMatching(v.left, predicate, lower);
            if (result != null)
                return result;
            else
                return v;
        } else {
            if (lower)
                return getMatching(v.left, predicate, lower);
            else
                return getMatching(v.right, predicate, lower);
        }
    }

    @Override
    public E lower(E e) {
        Node node;
        if (!reverseOrder)
            node = getMatching(root, x -> compare(x, e) < 0, true);
        else
            node = getMatching(root, x -> compare(x, e) > 0, false);
        if (node != null)
            return node.key;
        else
            return null;
    }

    @Override
    public E floor(E e) {
        Node node;
        if (!reverseOrder)
            node = getMatching(root, x -> compare(x, e) <= 0, true);
        else
            node = getMatching(root, x -> compare(x, e) >= 0, false);
        if (node != null)
            return node.key;
        else
            return null;
    }

    @Override
    public E ceiling(E e) {
        Node node;
        if (reverseOrder)
            node = getMatching(root, x -> compare(x, e) <= 0, true);
        else
            node = getMatching(root, x -> compare(x, e) >= 0, false);
        if (node != null)
            return node.key;
        else
            return null;
    }

    @Override
    public E higher(E e) {
        Node node;
        if (reverseOrder)
            node = getMatching(root, x -> compare(x, e) < 0, true);
        else
            node = getMatching(root, x -> compare(x, e) > 0, false);
        if (node != null)
            return node.key;
        else
            return null;
    }

    private class Node {
        E key;
        Node left = null;
        Node right = null;
        Node parent = null;
        Node next = null;
        Node prev = null;

        Node (E e){
            key = e;
        }
    }

    private class MyTreeSetIterator implements Iterator<E> {
        private Node pos = first;

        @Override
        public boolean hasNext() {
            return (pos != null);
        }

        @Override
        public E next() {
            E result = pos.key;
            pos = pos.next;
            return result;
        }
    }

    private class MyTreeSetDescendingIterator implements Iterator<E> {
        private Node pos = last;

        @Override
        public boolean hasNext() {
            return (pos != null);
        }

        @Override
        public E next() {
            E result = pos.key;
            pos = pos.prev;
            return result;
        }
    }
}
