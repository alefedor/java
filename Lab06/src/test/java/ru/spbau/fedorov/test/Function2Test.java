package ru.spbau.fedorov.test;

import org.junit.Test;
import ru.spbau.fedorov.algo.Function1;
import ru.spbau.fedorov.algo.Function2;

import static org.junit.Assert.*;

public class Function2Test {
    private static final Function1<String, Integer> length = (s) -> s.length();
    private static final Function1<Integer, Integer> square = (i) -> i * i;
    private static final Function2<Integer, Integer, Integer> calc = (i, j) -> i * i + j;
    private static final Function2<Character, Integer, String> namePosition = (c, i) -> c + i.toString();

    @Test
    public void testApply() {
        assertEquals(7, (int)calc.apply(2, 3));
        assertEquals("c7", namePosition.apply('c', 7));
    }

    @Test
    public void testCompose() {
        assertEquals(49, (int)calc.compose(square).apply(2, 3));
        assertEquals(2, (int)namePosition.compose(length).apply('d', 5));
    }

    @Test
    public void testBind1() {
        assertEquals(7, (int)calc.bind1(2).apply(3));
        assertEquals("e1", namePosition.bind1('e').apply(1));
    }

    @Test
    public void testBind2() {
        assertEquals(7, (int)calc.bind2(3).apply(2));
        assertEquals("e1", namePosition.bind2(1).apply('e'));
    }

    @Test
    public void testCurry() {
        assertEquals(7, (int)calc.curry(3).apply(2));
        assertEquals("e1", namePosition.curry(1).apply('e'));
    }

}