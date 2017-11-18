package ru.spbau.fedorov.test;

import org.junit.Test;
import ru.spbau.fedorov.algo.Collections;
import ru.spbau.fedorov.algo.Function1;
import ru.spbau.fedorov.algo.Function2;
import ru.spbau.fedorov.algo.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CollectionsTest {
    private static final Predicate<Integer> isOdd = (x) -> x % 2 == 1;
    private static final Function2<Integer, Integer, Integer> calc = (i, j) -> i * i + j;
    private static final Function1<String, Integer> length = (s) -> s.length();
    private static final Function2<Integer, String, String> concatr = (i, s) -> i.toString() + s;
    private static final Function2<String, Integer, String> concatl = (s, i) -> s + i.toString();
    private static final Function1<String, String> doubleString = (s) -> s + s;
    private static final List<String> listStr = new ArrayList<>(Arrays.asList("a", "aba", "abacaba"));
    private static final List<String> listStrDoubled = new ArrayList<>(Arrays.asList("aa", "abaaba", "abacabaabacaba"));
    private static final List<Integer> listInt = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
    private static final List<Integer> listIntShort = new ArrayList<>(Arrays.asList(1, 2));
    private static final List<Integer> listIntLength = new ArrayList<>(Arrays.asList(1, 3, 7));

    @Test
    public void testMapSameTypes() {
        assertEquals(listStrDoubled, Collections.map(doubleString, listStr));
    }

    @Test
    public void testMapDifferentTypes() {
        assertEquals(listIntLength, Collections.map(length, listStr));
    }

    @Test
    public void testFilter() {
        assertEquals(new ArrayList<Integer>(Arrays.asList(1, 3)), Collections.filter(isOdd, listInt));
    }

    @Test
    public void testTakeWhile() {
        Predicate<Integer> less3 = (i) -> i < 3;
        assertEquals(listIntShort, Collections.takeWhile(less3, listInt));
    }

    @Test
    public void testTakeWhileReachEnd() {
        Predicate<Integer> less10 = (i) -> i < 10;
        assertEquals(listInt, Collections.takeWhile(less10, listInt));
    }

    @Test
    public void testTakeUnless() {
        Predicate<Integer> greater2 = (i) -> i > 2;
        assertEquals(listIntShort, Collections.takeUnless(greater2, listInt));
    }

    @Test
    public void testTakeUnlessReachEnd() {
        Predicate<Integer> greater10 = (i) -> i > 10;
        assertEquals(listInt, Collections.takeUnless(greater10, listInt));
    }

    @Test
    public void testFoldrEmptyList() {
        assertEquals(5, (int)Collections.foldr(calc, 5, new ArrayList<Integer>()));
    }

    @Test
    public void testFoldlEmptyList() {
        assertEquals(5, (int)Collections.foldl(calc, 5, new ArrayList<Integer>()));
    }

    @Test
    public void testFoldr() {
        assertEquals(35, (int)Collections.foldr(calc, 5, listInt));
    }

    @Test
    public void testFoldl() {
        assertEquals(678, (int)Collections.foldl(calc, 5, listIntShort));
    }

    @Test
    public void testFoldrDifferentTypes() {
        assertEquals("1234", Collections.foldr(concatr, "", listInt));
    }

    @Test
    public void testFoldlDifferentTypes() {
        assertEquals("1234", Collections.foldl(concatl, "", listInt));
    }
}