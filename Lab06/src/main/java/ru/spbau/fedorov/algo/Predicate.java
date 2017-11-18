package ru.spbau.fedorov.algo;

import org.jetbrains.annotations.NotNull;

/**
 * Function of one argument which returns Boolean
 * @param <T> type of argument
 */
public interface Predicate<T> extends Function1<T, Boolean> {
    /**
     * Inverts results of predicate
     * @return predicate which values are inverted
     */
    @NotNull
    default Predicate<T> not() {
        return (T x) -> !apply(x);
    }

    /**
     * Builds predicate this || p
     * @param p predicate to or with
     * @return predicate this || p
     */
    @NotNull
    default Predicate<T> or(@NotNull Predicate<? super T> p) {
        return (T x) -> apply(x) || p.apply(x);
    }

    /**
     * Builds predicate this && p
     * @param p predicate to and with
     * @return predicate this && p
     */
    @NotNull
    default Predicate<T> and(@NotNull Predicate<? super T> p) {
        return (T x) -> apply(x) && p.apply(x);
    }

    Predicate<Object> ALWAYS_TRUE = x -> true;
    Predicate<Object> ALWAYS_FALSE =  x -> false;
}
