package ru.spbau.fedorov.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import ru.spbau.fedorov.algo.Trie;

public class TrieTest {
    private Trie trie;

    @Before
    public void setUp() {
        trie = new Trie();
    }

    private void fillWithStrings() {
        trie.add("java");
        trie.add("javadoc");
        trie.add("javadog");
        trie.add("pascal");
    }

    @Test
    public void testAddString() {
        assertEquals(true, trie.add("java"));
    }

    @Test
    public void testAddEqualStrings() {
        trie.add("java");
        assertEquals(false, trie.add("java"));
    }

    @Test
    public void testAddStrings() {
        fillWithStrings();
    }

    @Test
    public void testAddPrefix() {
        trie.add("javadoc");
        assertEquals(true, trie.add("java"));
    }

    @Test
    public void testContainFalse() {
        trie.add("java");
        assertEquals(false, trie.contains("jara"));
    }

    @Test
    public void testContainsTrue() {
        trie.add("java");
        assertEquals(true, trie.contains("java"));
    }

    @Test
    public void testContainsPrefix() {
        trie.add("java");
        assertEquals(false, trie.contains("jav"));
    }

    @Test
    public void testContainsManyStrings() {
        fillWithStrings();
        assertEquals(true, trie.contains("java"));
        assertEquals(true, trie.contains("javadoc"));
        assertEquals(true, trie.contains("javadog"));
        assertEquals(true, trie.contains("pascal"));
        assertEquals(false, trie.contains("python"));
    }

    @Test
    public void testSizeOfNothing() {
        assertEquals(0, trie.size());
    }

    @Test
    public void testSizeEqualAdd() {
        trie.add("java");
        trie.add("java");
        assertEquals(1, trie.size());
    }

    @Test
    public void testSizeManyStrings() {
        fillWithStrings();
        assertEquals(4, trie.size());
    }

    @Test
    public void testHowManyStartsWithPrefix() {
        trie.add("java");
        trie.add("pascal");
        assertEquals(1, trie.howManyStartsWithPrefix("jav"));
    }

    @Test
    public void testHowManyStartsWithPrefixNested() {
        trie.add("java");
        trie.add("javadoc");
        assertEquals(2, trie.howManyStartsWithPrefix("java"));
    }

    @Test
    public void testRemoveNothing() {
        assertEquals(false, trie.remove("java"));
    }

    @Test
    public void testRemoveString() {
        trie.add("java");
        assertEquals(true, trie.remove("java"));
        assertEquals(false, trie.contains("java"));
        assertEquals(0, trie.size());
        assertEquals(0, trie.howManyStartsWithPrefix("j"));
    }

    @Test
    public void testRemoveInManyStrings() {
        fillWithStrings();
        trie.remove("java");
        assertEquals(false, trie.contains("java"));
        assertEquals(true, trie.contains("javadoc"));
        assertEquals(2, trie.howManyStartsWithPrefix("java"));
        assertEquals(3, trie.size());
    }

    @Test
    public void testSerializeDeserializeEmptyTrie() throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            trie.serialize(out);
            try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
                trie.deserialize(in);
            } catch (Exception e){
                fail();
            }
        }
    }

    @Test
    public void testSerializeDeserialize() throws Exception {
        Trie newTrie = new Trie();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            fillWithStrings();

            trie.serialize(out);
            try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
                newTrie.deserialize(in);
            } catch (Exception e){
                fail();
            }
        }

        assertEquals(4, newTrie.size());
        assertEquals(true, newTrie.contains("java"));
        assertEquals(true, newTrie.contains("javadoc"));
        assertEquals(true, newTrie.contains("javadog"));
        assertEquals(true, newTrie.contains("pascal"));
        assertEquals(false, newTrie.contains("python"));
        assertEquals(false, newTrie.contains("javad"));
    }
}