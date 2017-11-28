package ru.spbau.fedorov.test;

import org.jetbrains.annotations.TestOnly;
import org.junit.Test;
import ru.spbau.fedorov.algo.MyTreeSet;

import java.util.Iterator;

import static org.junit.Assert.*;

public class MyTreeSetTest {

    @Test
    public void testConstruct() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
    }

    @Test
    public void testAddOneElement() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        set.add(5);
    }

    @Test
    public void testAddNewElements() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        assertTrue(set.add(5));
        assertTrue(set.add(6));
        assertTrue(set.add(1));
    }

    @Test
    public void testAddSameElements() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        assertTrue(set.add(5));
        assertFalse(set.add(5));
        assertFalse(set.add(5));
    }

    @Test
    public void testContains() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        assertTrue(set.add(5));
        assertTrue(set.add(6));
        assertTrue(set.add(1));
        assertTrue(set.contains(5));
        assertTrue(set.contains(1));
        assertFalse(set.contains(10));
    }

    @Test
    public void testConstructorWithComparatorSimple() {
        MyTreeSet<Integer> set = new MyTreeSet<>((x, y) -> 0);
        assertTrue(set.add(5));
        assertFalse(set.add(6));
        assertFalse(set.add(1));
        assertTrue(set.contains(10));
    }

    @Test
    public void testConstructorWithComparatorReverse() {
        MyTreeSet<Integer> set = new MyTreeSet<>((x, y) -> y.compareTo(x));
        assertTrue(set.add(5));
        assertTrue(set.add(6));
        assertTrue(set.add(1));
        assertTrue(set.contains(5));
        assertTrue(set.contains(1));
        assertFalse(set.contains(10));
    }

    @Test
    public void testIsEmpty() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        assertTrue(set.isEmpty());
        set.add(1);
        assertFalse(set.isEmpty());
    }

    @Test
    public void testRemove() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        assertTrue(set.add(5));
        assertTrue(set.add(6));
        assertTrue(set.add(1));
        assertTrue(set.remove(5));
        assertFalse(set.remove(5));
        assertFalse(set.remove(7));
        assertTrue(set.remove(6));
    }

    @Test
    public void testContainsAfterRemove() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        assertTrue(set.add(5));
        assertTrue(set.remove(5));
        assertFalse(set.contains(5));
    }

    @Test
    public void testSize() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        set.add(5);
        assertEquals(1, set.size());
        set.add(6);
        assertEquals(2, set.size());
        set.add(1);
        assertEquals(3, set.size());
    }

    @Test
    public void testIterator() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        for (int i = 0; i < 100; ++i) {
            assertTrue(set.add(i));
        }
        int i = 0;
        for (Integer value : set) {
            assertEquals(i, (int)value);
            i++;
        }
        assertEquals(100, i);
    }

    @Test
    public void testDescendingIterator() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        for (int i = 0; i < 100; ++i) {
            assertTrue(set.add(i));
        }
        int i = 100;
        for (Integer value : set) {
            i--;
            assertEquals(i, (int)value);
        }
        assertEquals(0, i);
    }

    @Test
    public void testDescendingSet() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        for (int i = 0; i < 100; ++i) {
            assertTrue(set.add(i));
        }
        MyTreeSet<Integer> descendingTree = set.descendingSet();
        int i = 100;
        for (Integer value : descendingTree) {
            i--;
            assertEquals(i, (int)value);
        }
        assertEquals(0, i);
    }

    @Test
    public void testFirst() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        assertTrue(set.add(5));
        assertTrue(set.add(6));
        assertTrue(set.add(1));
        assertEquals(1, (int)set.first());
        MyTreeSet<Integer> setRev = new MyTreeSet<>((x, y) -> y.compareTo(x));
        assertTrue(setRev.add(5));
        assertTrue(setRev.add(6));
        assertTrue(setRev.add(1));
        assertEquals(6, (int)setRev.first());
    }

    @Test
    public void testLast() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        assertTrue(set.add(5));
        assertTrue(set.add(6));
        assertTrue(set.add(1));
        assertEquals(6, (int)set.last());
        MyTreeSet<Integer> setRev = new MyTreeSet<>((x, y) -> y.compareTo(x));
        assertTrue(setRev.add(5));
        assertTrue(setRev.add(6));
        assertTrue(setRev.add(1));
        assertEquals(1, (int)setRev.last());
    }

    @Test
    public void testLower() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        for (int i = 0; i < 50; ++i) {
            assertTrue(set.add(i * 2));
        }
        assertEquals(40, (int)set.lower(42));
        assertEquals(40, (int)set.lower(41));
        assertEquals(98, (int)set.lower(200));
        assertNull(set.lower(0));
    }

    @Test
    public void testFloor() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        for (int i = 0; i < 50; ++i) {
            assertTrue(set.add(i * 2));
        }
        assertEquals(42, (int)set.floor(42));
        assertEquals(40, (int)set.floor(41));
        assertEquals(98, (int)set.floor(200));
        assertEquals(0, (int)set.floor(0));
        assertNull(set.floor(-1));
    }

    @Test
    public void testCeiling() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        for (int i = 0; i < 50; ++i) {
            assertTrue(set.add(i * 2));
        }
        assertEquals(42, (int)set.ceiling(42));
        assertEquals(42, (int)set.ceiling(41));
        assertNull(set.ceiling(200));
        assertEquals(0, (int)set.ceiling(0));
        assertEquals(0, (int)set.ceiling(-1));
        assertEquals(98, (int)set.ceiling(98));
    }

    @Test
    public void testHigher() {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        for (int i = 0; i < 50; ++i) {
            assertTrue(set.add(i * 2));
        }
        assertEquals(44, (int)set.higher(42));
        assertEquals(42, (int)set.higher(41));
        assertNull(set.higher(200));
        assertEquals(2, (int)set.higher(0));
        assertEquals(0, (int)set.higher(-1));
        assertNull(set.higher(98));
    }

}