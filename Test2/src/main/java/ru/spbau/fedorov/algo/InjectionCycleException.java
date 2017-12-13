package ru.spbau.fedorov.algo;

/**
 * Exception thrown when when found a cycle in dependencies in Injector
 */
public class InjectionCycleException extends Exception {
    /**
     * Construct InjectionCycleException
     * @param message string to be stored in Exception
     */
    public InjectionCycleException(String message) {
        super(message);
    }
}
