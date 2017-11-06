package ru.spbau.fedorov.algo;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/**
 * Container which can hold one or zero elements
 * @param <T> type of element to hold
 */
public class Maybe<T> {
    private T data = null;

    private Maybe (T t) {
        data = t;
    }

    /**
     *Constructs Maybe object which contain T
     * @param t object to contain
     * @param <T> type of object to contain
     * @return constructed Maybe object
     */
    public static @NotNull <T> Maybe<T> just(T t) {
        return new Maybe<T>(t);
    }

    /**
     *Construct Maybe object which contain nothing
     * @param <T> type of object supposed to be contained
     * @return constructed Maybe object
     */
    public static @NotNull <T> Maybe<T> nothing() {
        return new Maybe<T>(null);
    }

    /**
     *Checks whether there is an element in container
     * @return true, if there is object of class T and false, otherwise
     */
    public boolean isPresent() {
        return (data != null);
    }

    /**
     * Get object in Maybe container
     * @return Object held in container
     * @throws NullStoredValueException if there is no objects in container
     */
    public @NotNull  T get() throws NullStoredValueException{
        if (!isPresent())
            throw new NullStoredValueException("Tried to get an element while there are no elements in container"); // to do
        return data;
    }

    /**
     * Constructs a new Maybe object with the result of an applied
     * function to an object in Maybe container (does nothing if there is no such)
     * @param mapper function to apply
     * @param <U> type of object returned by mapper
     * @return Constructed Maybe object
     */
    public @NotNull <U> Maybe<U> map(@NotNull Function<T, U> mapper) {
        if (isPresent()) {
            return new Maybe<U> (mapper.apply(data));
        } else {
            return new Maybe<U>(null);
        }
    }

}
