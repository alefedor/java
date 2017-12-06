package ru.spbau.fedorov.test;

import org.junit.Test;
import ru.spbau.fedorov.algo.EmptyStackException;
import ru.spbau.fedorov.algo.Stack;

import static org.junit.Assert.*;

public class StackTest {
    @Test
    public void testAdd() {
        Stack<Integer> stack = new Stack<>();
        assertEquals(1, (int)stack.push(1));
        assertEquals(2, (int)stack.push(2));
    }

    @Test
    public void testAddLargeData() {
        Stack<String> stack = new Stack<>();
        stack.push("abacaba");
    }

    @Test
    public void testEmpty() {
        Stack<Integer> stack = new Stack<>();
        assertTrue(stack.empty());
    }

    @Test
    public void testEmptyFalse() {
        Stack<Integer> stack = new Stack<>();
        stack.push(2);
        assertFalse(stack.empty());
    }

    @Test
    public void testClear() {
        Stack<Integer> stack = new Stack<>();
        stack.push(2);
        stack.clear();
        assertTrue(stack.empty());
    }

    @Test
    public void testPop() {
        Stack<Integer> stack = new Stack<>();
        stack.push(2);
        assertEquals(2, (int)stack.pop());
        assertTrue(stack.empty());
    }

    @Test (expected = EmptyStackException.class)
    public void testPopEmpty() {
        Stack<Integer> stack = new Stack<>();
        stack.pop();
    }

    @Test
    public void testOrder() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, (int)stack.pop());
        assertEquals(2, (int)stack.pop());
        assertEquals(1, (int)stack.pop());
    }

    @Test
    public void testPeek() {
        Stack<Integer> stack = new Stack<>();
        stack.push(5);
        assertEquals(5, (int)stack.peek());
        assertFalse(stack.empty());
    }

}