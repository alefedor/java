package ru.spbau.fedorov.test;

import org.junit.Test;
import ru.spbau.fedorov.algo.Function1;
import ru.spbau.fedorov.algo.Predicate;

import static org.junit.Assert.*;

public class PredicateTest {
    private static final Predicate<Integer> isOdd = (x) -> x % 2 == 1;
    private static final Predicate<Character> isA = (c) -> c == 'a' || c == 'A';
    private static final Function1<String, Integer> length = (s) -> s.length();
    private static final Function1<Boolean, Integer> fromBoolToInt = (b) -> {if (b) return 1; else return 0;};

    @Test
    public void testApply() {
        assertEquals(false, isOdd.apply(4));
        assertEquals(true, isA.apply('a'));
    }

    @Test
    public void testCompose() {
        assertEquals(false, length.compose(isOdd).apply("abcd"));
        assertEquals(true, length.compose(isOdd).apply("abc"));
        assertEquals(1, (int)isOdd.compose(fromBoolToInt).apply(3));
    }

    @Test
    public void testAlwaysTrue() {
        assertEquals(true, Predicate.ALWAYS_TRUE.apply("abacaba"));
    }

    @Test
    public void testAlwaysFalse() {
        assertEquals(false, Predicate.ALWAYS_FALSE.apply(15));
    }

    @Test
    public void testNot() {
        assertEquals(false, Predicate.ALWAYS_TRUE.not().apply("aba"));
        assertEquals(true, isA.not().apply('b'));
    }

    @Test
    public void testAnd() {
        assertEquals(true, isOdd.and(isOdd).apply(3));
        assertEquals(false, isOdd.and(Predicate.ALWAYS_FALSE).apply(3));
        assertEquals(true, isOdd.and(Predicate.ALWAYS_TRUE).apply(3));
    }

    @Test
    public void testOr() {
        assertEquals(true, isOdd.or(isOdd).apply(3));
        assertEquals(true, isOdd.or(Predicate.ALWAYS_FALSE).apply(3));
        assertEquals(true, isOdd.or(Predicate.ALWAYS_TRUE).apply(4));
        assertEquals(false, isOdd.or(Predicate.ALWAYS_FALSE).apply(4));
    }
}