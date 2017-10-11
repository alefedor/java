package ru.spbau.fedorov.test;

import org.junit.Assert;
import ru.spbau.fedorov.algo.HashMap;
import org.junit.Test;
import org.junit.Assert.*;

public class HashMapTest {
    @Test
    public void testPut() {
        HashMap<Integer, Integer> mp = new HashMap<>();
        Assert.assertEquals(null, mp.put(1, 2));
    }

    @Test
    public void testContains() {
        HashMap<Integer, Integer> mp = new HashMap<>();
        mp.put(1, 2);
        Assert.assertEquals(true, mp.containsKey(1));
    }

    @Test
    public void testSize() {
        HashMap<Integer, Integer> mp = new HashMap<>();
        mp.put(1, 2);
        Assert.assertEquals(1, mp.size());
    }

    @Test
    public void testRemove() {
        HashMap<Integer, Integer> mp = new HashMap<>();
        mp.put(1, 2);
        mp.remove(1);
        Assert.assertEquals(0, mp.size());
        Assert.assertEquals(false, mp.containsKey(1));
    }

    @Test
    public void testGet() {
        HashMap<Integer, Integer> mp = new HashMap<>();
        mp.put(1, 2);
        Assert.assertEquals((Integer)2, mp.get(1));
    }

    @Test
    public void testPutEqual() {
        HashMap<Integer, Integer> mp = new HashMap<>();
        mp.put(1, 2);
        Assert.assertEquals((Integer)2, mp.put(1, 3));
    }

    @Test
    public void testPutMany() {
        HashMap<Integer, Integer> mp = new HashMap<>();
        mp.put(1, 2);
        mp.put(3, 4);
        mp.put(5, 6);
        Assert.assertEquals(3, mp.size());
        Assert.assertEquals(true, mp.containsKey(1));
        Assert.assertEquals(true, mp.containsKey(3));
        Assert.assertEquals(true, mp.containsKey(5));
    }
}
