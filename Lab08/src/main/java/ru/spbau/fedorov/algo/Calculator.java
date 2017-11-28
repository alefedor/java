package ru.spbau.fedorov.algo;


import org.jetbrains.annotations.NotNull;

/**
 * A class for calculating expressions written in
 * regular or reverse polish notation.
 * Addition, substraction, multiplication, division, brackets and integer numbers are supported.
 */
public class Calculator {
    private Stack<Integer> stack;

    /**
     * Constructor for Calculator.
     * @param s stack needed for functions
     */
    public Calculator(@NotNull Stack<Integer> s) {
        stack = s;
    }

    /**
     * Receives regular expression and converts it to polish notation
     * @param expr string containing expression in regular notation
     * @return string containing expression in polish notation
     */
    @NotNull
    public String toReversedPolishNotation(@NotNull String expr) {
        stack.clear();
        StringBuilder result = new StringBuilder();
        expr = '(' + expr + ')';

        boolean wasDigit = false;

        for (char c: expr.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                continue;
            }

            if (Character.isDigit(c)) {
                result.append(c);
                wasDigit = true;
            } else {
                if (wasDigit)
                    result.append(' ');
                wasDigit = false;

                if (c == '(')
                    stack.push((toInt(c)));
                else if (c == ')') {
                    while (!stack.peek().equals(toInt('('))) {
                        result.append(toChar(stack.pop()));
                        result.append(' ');
                        if (stack.empty())
                            throw new IllegalArgumentException("Not a valid sequence of brackets");
                    }
                    stack.pop();
                } else {
                    int priority = getPriority(c);
                    while (!stack.empty() && getPriority(toChar(stack.peek())) >= priority) {
                        result.append(Character.toChars(stack.pop()));
                        result.append(' ');
                    }
                    stack.push(toInt(c));
                }
            }

        }
        return result.toString();
    }

    /**
     * Calculates the result of expression in reverse polish notation
     * @param expr string containing expression in reverse polish notation
     * @return result of expression
     */
    public int evaluateExpressionInReversePolishNotation(@NotNull String expr) {
        stack.clear();
        int number = 0;
        boolean wasNumber = false;
        for (char c : expr.toCharArray()) {
            if (Character.isDigit(c)){
                number = number * 10 + Character.digit(c, 10);
                wasNumber = true;
                continue;
            }

            if (wasNumber) {
                wasNumber = false;
                stack.push(number);
                number = 0;
            }

            if (Character.isSpaceChar(c))
                continue;

            int a = stack.pop();
            int b = stack.pop();
            int result = apply(a, b, c);
            stack.push(result);

        }

        return stack.pop();
    }

    private int apply(int a, int b, char c) {
        switch (c) {
            case '+':
                return a + b;
            case '-':
                return b - a;
            case '*':
                return a * b;
            case '/':
                return b / a;
            default:
                throw new IllegalArgumentException("No such operation");
        }
    }

    private char toChar(int value) {
        return (char)value;
    }

    private int toInt(char value) {
        return (int)value;
    }

    private static int getPriority(char c) {
        switch (c) {
            case '(' :
                return 0;
            case '+' :
            case '-' :
                return 1;
            case '*' :
            case '/' :
                return 2;
            default:
                throw new IllegalArgumentException("No such character (" + c + ")");
        }
    }
}
