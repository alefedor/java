package ru.spbau.fedorov.algo;

import java.util.*;

public class HashMap<K, V> implements Map<K,V>{
    private static final int PRIME = 43;
    private static final int START_CAPACITY = 10;

    private int size = 0;
    private List[] data = new List[START_CAPACITY];
    private int capacity = START_CAPACITY;
    private MapIterator<Entry<K, V> > firstElem = null;

    private class MapEntry<K,V> implements Entry<K,V> {

        K key;
        V value;

        MapEntry(K k, V v) {
            key = k;
            value = v;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V v) {
            V result = value;
            value = v;
            return result;
        }
    }

    private int getHash(K a) {
        return a.hashCode() % size;
    }

    private void rebuild() {
        capacity *= 2;
        size = 0;

        List[] oldData = data;
        data = new List[capacity];

        for (List list : oldData)
            if (list != null) {
                while (!list.empty()){
                    Object[] elem = list.pop();
                    put((K)elem[0], (V)elem[1]);
                }
            }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        int hash = getHash((K)o);
        return data[hash] != null && data[hash].find(o) != null;
    }

    @Override
    public boolean containsValue(Object o) {
        return false;
    }

    @Override
    public V get(Object o) {
        int hash = getHash((K)o);
        if (data[hash] == null)
            return null;
        return (V)data[hash].find((K)o);
    }

    @Override
    public V put(K k, V v) {
        int hash = getHash(k);
        if (data[hash] == null)
            data[hash] = new List();

        Object result = data[hash].erase(k);
        if (result == null)
            size++;
        data[hash].insert(k, v);

        if (capacity < 2 * size)
            rebuild();

        return (V)result;
    }

    @Override
    public V remove(Object o) {
        int hash = getHash((K)o);
        if (data[hash] == null)
            return null;

        V result = (V) data[hash].erase((K)o);
        if (result != null)
            size--;

        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

    }

    @Override
    public void clear() {
        for (List list: data)
            if (list != null)
                list.clear();

        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return (Set)new MapSet<MapEntry<K, V>>(firstElem);
    }

    private class MapIterator<E> implements Iterator<E> {
        private E data;
        private MapIterator<E> next = null;

        MapIterator (E el) {
            data = el;
        }

        @Override
        public boolean hasNext() {
            return data != null;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException("No such element");
            E result = data;

            if (next != null) {
                data = next.data;
                next = next.next;
            }

            return result;
        }
    }

    private class MapSet<E> implements Set<E> {
        private MapIterator iter;

        MapSet(MapIterator it) {
            iter = it;
        }

        @Override
        public int size() {
            int res = 0;
            MapIterator tmp = iter;
            while (tmp.hasNext()) {
                res++;
                tmp.next();
            }
            return res;
        }

        @Override
        public Iterator<E> iterator() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            return null;
        }

        @Override
        public boolean add(E e) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }
    }
}
