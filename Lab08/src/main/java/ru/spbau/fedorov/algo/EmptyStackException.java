package ru.spbau.fedorov.algo;


/**
 * Exception which is returned when tried to
 * get element from empty stack.
 */
public class EmptyStackException extends RuntimeException{
    /**
     * Creates NullStoredValueException
     * @param message string to be stored in Exception
     */
    public EmptyStackException(String message) {
        super(message);
    }
}