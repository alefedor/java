package ru.spbau.fedorov.algo;

import org.jetbrains.annotations.NotNull;

/**
 * Class with main method only.
 * Gets a string expression, converts it to reversed polish notation and evaluates
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: <expression>");
            System.exit(0);
        }

        Stack<Integer> stack = new Stack<>();
        Calculator calculator = new Calculator(stack);
        try {
            String polishNotation = calculator.toReversedPolishNotation(args[0]);
            int result = calculator.evaluateExpressionInReversePolishNotation(polishNotation);
            System.out.println("Result is " + result);
        } catch(IllegalArgumentException | EmptyStackException e) {
            System.out.println("Invalid expression");
        }
    }
}
