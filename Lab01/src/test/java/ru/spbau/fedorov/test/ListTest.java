package ru.spbau.fedorov.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import ru.spbau.fedorov.algo.List;

public class ListTest {
    private List list;

    @BeforeEach
    public void setUp() {
        list = new List();
    }

    @Test
    public void testEmptyTrue() {
        Assertions.assertEquals(true, list.empty());
    }

    @Test
    public void testInsert() {
        list.insert("a", "bb");
    }

    @Test
    public void testEmptyFalse() {
        list.insert("a", "bb");
        Assertions.assertEquals(false, list.empty());
    }

    @Test
    public void testFindSimple() {
        list.insert("a", "bb");
        Assertions.assertEquals("bb", list.find("a"));
    }

    @Test
    public void testFindNothing() {
        Assertions.assertEquals(null, list.find("a"));
    }

    @Test
    public void testEraseNothing() {
        Assertions.assertEquals(null, list.erase("a"));
    }

    @Test
    public void testEraseElement() {
        list.insert("aa", "bb");
        Assertions.assertEquals("bb", list.erase("aa"));
        Assertions.assertEquals(true, list.empty());
    }

    @Test
    public void testInsertEraseMany() {
        list.insert("aa", "bb");
        list.insert("ac", "bc");
        list.insert("ad", "bd");
        Assertions.assertEquals("bc", list.erase("ac"));
        Assertions.assertEquals("bb", list.erase("aa"));
        Assertions.assertEquals("bd", list.erase("ad"));
    }

    @Test
    public void testClearNothing() {
        list.clear();
    }

    @Test
    public void testClear() {
        list.insert("aa", "bb");
        list.clear();
        Assertions.assertEquals(null, list.find("aa"));
    }

    @Test
    public void testPop() {
        list.insert("aa", "bb");
        list.insert("ac", "bc");
        String[] arr;
        arr = list.pop();
        Assertions.assertEquals("ac", arr[0]);
        Assertions.assertEquals("bc", arr[1]);

        arr = list.pop();
        Assertions.assertEquals("aa", arr[0]);
        Assertions.assertEquals("bb", arr[1]);

    }

}