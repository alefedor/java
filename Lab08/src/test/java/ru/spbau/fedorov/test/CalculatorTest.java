package ru.spbau.fedorov.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import ru.spbau.fedorov.algo.Calculator;
import ru.spbau.fedorov.algo.Stack;

public class CalculatorTest {

    private Stack<Integer> stack;
    private Calculator calculator;

    private static int openBracket = (int)'(';
    private static int closedBracket = (int)')';
    private static int add = (int)'+';
    private static int sub = (int)'-';
    private static int mult = (int)'*';
    private static int div = (int)'/';


    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        stack = mock(Stack.class);
        calculator = new Calculator(stack);
    }

    @Test
    public void testEvaluateExpressionInReversePolishNotationSimple() {
        String expr = "6 1 -";
        when(stack.pop()).thenReturn(1, 6, 5);
        int result = calculator.evaluateExpressionInReversePolishNotation(expr);
        assertEquals(5, result);

        InOrder inOrder = inOrder(stack);

        verify(stack, times(3)).push(anyInt());
        verify(stack, times(3)).pop();

        inOrder.verify(stack).clear();
        inOrder.verify(stack).push(6);
        inOrder.verify(stack).push(1);
        inOrder.verify(stack).push(5);
        verifyNoMoreInteractions(stack);
    }

    @Test
    public void testEvaluateExpressionInReversePolishNotationLongNumbers() {
        String expr = "228 179 *";
        when(stack.pop()).thenReturn(228, 179, 40812);
        int result = calculator.evaluateExpressionInReversePolishNotation(expr);
        assertEquals(40812, result);

        InOrder inOrder = inOrder(stack);

        verify(stack, times(3)).push(anyInt());
        verify(stack, times(3)).pop();

        inOrder.verify(stack).clear();
        inOrder.verify(stack).push(228);
        inOrder.verify(stack).push(179);
        inOrder.verify(stack).push(40812);
        verifyNoMoreInteractions(stack);
    }

    @Test
    public void testEvaluateExpressionInReversePolishNotationComplex() {
        String expr = "6 1 - 4 * 40 2 / +"; //40 / 2 + (6 - 1) * 4 = 40
        when(stack.pop()).thenReturn(1, 6, 4, 5, 2, 40, 20, 20, 40);
        int result = calculator.evaluateExpressionInReversePolishNotation(expr);
        assertEquals(40, result);

        InOrder inOrder = inOrder(stack);

        verify(stack, times(9)).push(anyInt());
        verify(stack, times(9)).pop();
        inOrder.verify(stack).clear();
        inOrder.verify(stack).push(6);
        inOrder.verify(stack).push(1);
        inOrder.verify(stack).push(5);
        inOrder.verify(stack).push(4);
        inOrder.verify(stack).push(20);
        inOrder.verify(stack).push(40);
        inOrder.verify(stack).push(2);
        inOrder.verify(stack).push(20);
        inOrder.verify(stack).push(40);

        verifyNoMoreInteractions(stack);
    }

    @Test
    public void testToReversedPolishNotation() {
        String expr = "2 * (3 - 1)";

        when(stack.pop()).thenReturn(sub, openBracket, mult, openBracket);
        when(stack.peek()).thenReturn(openBracket, openBracket, sub, openBracket, mult, openBracket);
        String result = calculator.toReversedPolishNotation(expr);
        assertEquals("2 3 1 - * ", result);

        InOrder inOrder = inOrder(stack);

        verify(stack, times(4)).push(anyInt());
        verify(stack, times(4)).pop();
        verify(stack, times(6)).peek();
        verify(stack, atLeast(1)).empty();

        inOrder.verify(stack).clear();
        inOrder.verify(stack).push(openBracket);
        inOrder.verify(stack).push(mult);
        inOrder.verify(stack).push(openBracket);
        inOrder.verify(stack).push(sub);
        verifyNoMoreInteractions(stack);
    }
}