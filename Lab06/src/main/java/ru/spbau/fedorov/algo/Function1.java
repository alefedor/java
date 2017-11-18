package ru.spbau.fedorov.algo;

import org.jetbrains.annotations.NotNull;

/**
 * Function with one argument
 * @param <T> type of argument
 * @param <K> type of function to return
 */
public interface Function1<T, K> {
    /**
     * Application of function to argument x
     * @param x argument to apply
     * @return result of Function1
     */
    public abstract K apply(T x);

    /**
     * Returns function f(g(x)), where g(x) is this
     * @param f function to compose with
     * @param <R> type of return value of f
     * @return composition of functions
     */
    @NotNull
    default <R> Function1<T, R> compose(@NotNull Function1<? super K, ? extends R> f) {
        return new Function1<T, R>() {
            @Override
            public R apply(T x) {
                return f.apply(Function1.this.apply(x));
            }
        };
    }
}
