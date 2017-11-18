package ru.spbau.fedorov.test;

import org.junit.Assert;
import ru.spbau.fedorov.algo.Function1;

import static org.junit.Assert.*;
import org.junit.Test;

public class Function1Test {
    private static final Function1<String, Integer> length = (s) -> s.length();
    private static final Function1<Integer, Integer> square = (i) -> i * i;
    private static final Function1<String, String> doubleString = (s) -> s + s;

    @Test
    public void testApply() {
        assertEquals(3, (int)length.apply("abc"));
        assertEquals(9, (int)square.apply(3));
        assertEquals("abcabc", doubleString.apply("abc"));
    }

    @Test
    public void testCompose() {
        Function1<String, Integer> squareLength = length.compose(square);
        assertEquals(9, (int)squareLength.apply("abc"));
    }

    @Test
    public void testComposeComplex() {
        Function1<String, Integer> f = doubleString.compose(length.compose(square));
        assertEquals(36, (int)f.apply("abc"));
    }

}