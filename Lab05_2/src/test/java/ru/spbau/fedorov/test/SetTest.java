package ru.spbau.fedorov.test;

import org.junit.Assert;
import org.junit.Before;
import ru.spbau.fedorov.algo.Set;
import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class SetTest {

    @Test
    public void testEmptySet() {
        Set<Integer>  set = new Set<>();
        assertEquals(0, set.size());
    }

    @Test
    public void testAddOneInteger() {
        Set<Integer>  set = new Set<>();
        assertEquals(true, set.add(1));
    }

    @Test
    public void testContainsTrue() {
        Set<Integer>  set = new Set<>();
        set.add(1);
        assertEquals(true, set.contains(1));
    }

    @Test
    public void testContainsFalse() {
        Set<Integer> set = new Set<>();
        assertEquals(false, set.contains(0));
    }

    @Test
    public void testManyStrings() {
        Set<String> set = new Set<>();
        assertEquals(true, set.add("abacaba"));
        assertEquals(true, set.add("java"));
        assertEquals(true, set.add("abaaba"));
        assertEquals(true, set.add("javadoc"));

        assertEquals(true, set.contains("abacaba"));
        assertEquals(true, set.contains("abaaba"));
        assertEquals(true, set.contains("javadoc"));
        assertEquals(true, set.contains("java"));
        assertEquals(4, set.size());
    }

    @Test
    public void testAddEqualElements() {
        Set<Integer> set = new Set<>();
        assertEquals(true, set.add(2));
        assertEquals(false, set.add(2));
        assertEquals(true, set.contains(2));
        assertEquals(1, set.size());
    }
}
