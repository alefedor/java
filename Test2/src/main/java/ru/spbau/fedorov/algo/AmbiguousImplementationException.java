package ru.spbau.fedorov.algo;

/**
 * Exception thrown when found several implementations of class needed to construct rootClass in Injector
 */
public class AmbiguousImplementationException extends Exception {
    /**
     * Construct AmbiguousImplementationException
     * @param message string to be stored in Exception
     */
    public AmbiguousImplementationException(String message) {
        super(message);
    }
}
