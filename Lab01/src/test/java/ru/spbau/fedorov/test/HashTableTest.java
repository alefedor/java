package ru.spbau.fedorov.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import ru.spbau.fedorov.algo.HashTable;

public class HashTableTest {
    private HashTable table;

    private class HashTableBadHash extends HashTable{
        protected int getHash(String s) {
            return 0;
        }
    }

    @BeforeEach
    public void setUp() {
        table = new HashTable();
    }

    private void fillHashTable(HashTable table, int num) {
        String s = "a";
        String v = "v";
        for (int i = 0; i < num; i++) {
            table.put(s, v);
            s = s + 'a';
            v = v + 'b';
        }
    }

    @Test
    public void testPutElement() {
        fillHashTable(table, 1);
    }

    @Test
    public void testPutElements() {
        fillHashTable(table, 5);
    }

    @Test
    public void testPutEqualElements() {
        table.put("a", "b");
        table.put("a", "c");
    }

    @Test
    public void testEmpty(){
        Assertions.assertEquals(0, table.size());
    }

    @Test
    public void testSize(){
        fillHashTable(table, 10);
        Assertions.assertEquals(10, table.size());
    }

    @Test
    public void testSizeEqualElements() {
        table.put("a", "b");
        table.put("a", "c");
        Assertions.assertEquals(1, table.size());
    }

    @Test
    public void testSizeAfterRemove() {
        table.put("1", "2");
        table.remove("1");
        Assertions.assertEquals(0, table.size());
    }

    @Test
    public void testPutReturnValue() {
        table.put("1", "2");
        String res = table.put("1", "3");
        Assertions.assertEquals("2", res);
    }

    @Test
    public void testPutReturnValueNull() {
        String res = table.put("1", "2");
        Assertions.assertEquals(null, res);
    }

    @Test
    public void testContainsFalse() {
        table.put("1", "2");
        Assertions.assertEquals(false, table.contains("3"));
    }

    @Test
    public void testContainsTrue() {
        table.put("1", "2");
        Assertions.assertEquals(true, table.contains("1"));
    }

    @Test
    public void testGetEmpty() {
        Assertions.assertEquals(null, table.get("1"));
    }

    @Test
    public void testGetOneElement() {
        table.put("1", "2");
        Assertions.assertEquals("2", table.get("1"));
    }

    @Test
    public void testGetInManyElements() {
        fillHashTable(table, 10);
        Assertions.assertEquals("vbbb", table.get("aaaa"));
    }

    @Test
    public void testGetEqualKeys() {
        String key = "key";
        String val = "v";
        for (int i = 0; i < 5; i++) {
            val = val + "v";
            table.put(key, val);
        }
        Assertions.assertEquals(val, table.get(key));
    }

    @Test
    public void testRemoveReturnValue() {
        table.put("1", "2");
        Assertions.assertEquals("2", table.remove("1"));
    }

    @Test
    public void testRemoveNothing() {
        Assertions.assertEquals(null, table.remove("1"));
    }

    @Test
    public void testRemoveInManyElements() {
        fillHashTable(table, 10);
        Assertions.assertEquals("vbbb", table.remove("aaaa"));
        Assertions.assertEquals(null, table.remove("aaaa"));
    }

    @Test
    public void clearNothing() {
        table.clear();
    }

    @Test
    public void clearManyElements() {
        fillHashTable(table, 10);
        table.clear();
        Assertions.assertEquals(false, table.contains("aaaa"));
    }

    @Test
    public void testManyElements() {
        fillHashTable(table, 100);
        String s = "a";
        String v = "v";

        for (int i = 0; i < 100; i++) {
            Assertions.assertEquals(v, table.get(s));
            s = s + "a";
            v = v + "b";
        }

        s = "a";
        v = "v";

        for (int i = 0; i < 100; i++) {
            Assertions.assertEquals(v, table.remove(s));
            s = s + "a";
            v = v + "b";
        }

        s = "a";
        v = "v";

        for (int i = 0; i < 100; i++) {
            Assertions.assertEquals(false, table.contains(s));
            s = s + "a";
            v = v + "b";
        }

        Assertions.assertEquals(0, table.size());
    }

    @Test
    public void testPutManyCollisions() {
        HashTableBadHash ht = new HashTableBadHash();
        fillHashTable(ht, 10);

        String s = "a";
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(true, ht.contains(s));
            s = s + "a";
        }
    }

    @Test
    public void testSizeInManyCollisions() {
        HashTableBadHash ht = new HashTableBadHash();
        fillHashTable(ht, 10);
        Assertions.assertEquals(10, ht.size());
    }

    @Test
    public void testRemoveInManyCollisions() {
        HashTableBadHash ht = new HashTableBadHash();
        fillHashTable(ht, 10);

        Assertions.assertEquals("vbb", ht.remove("aaa"));
        Assertions.assertEquals(false, ht.contains("aaa"));
        Assertions.assertEquals(true, ht.contains("aaaaa"));
    }

}