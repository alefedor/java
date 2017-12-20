package ru.spbau.fedorov.algo;

/**
 * Exception thrown when couldn't find any implementation of class needed to construct rootClass in Injector
 */
public class ImplementationNotFoundException extends Exception {
    /**
     * Construct ImplementationNotFoundException
     * @param message string to be stored in Exception
     */
    public ImplementationNotFoundException(String message) {
        super(message);
    }
}
