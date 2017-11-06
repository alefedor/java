package ru.spbau.fedorov.algo;


/**
 * Exception which is returned when get method is
 * called while there is no elements in Maybe container
 */
public class NullStoredValueException extends Exception{
    /**
     * Creates NullStoredValueException
     * @param message string to be stored in Exception
     */
    public NullStoredValueException(String message) {
        super(message);
    }
}
