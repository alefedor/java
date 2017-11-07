package ru.spbau.fedorov.algo;

import org.jetbrains.annotations.NotNull;

/**
 * Function of two arguments
 * @param <T> type of first argument
 * @param <K> type of second argument
 * @param <V> type of result value
 */
public interface Function2<T, K, V> {
    /**
     * Application of function to arguments x and y
     * @param x first argument to apply
     * @param y second argument to apply
     * @return result of Function2
     */
    public abstract V apply(T x, K y);

    /**
     * Returns function f(g(x, y)), where g(x, y) is this
     * @param f function to compose with
     * @param <R> type of return value of function
     * @return composition of functions
     */
    @NotNull
    default <R> Function2<T, K, R> compose(@NotNull Function1<? super V, ? extends  R> f) {
        return new Function2<T, K, R>() {
            @Override
            public R apply(T x, K y) {
                return f.apply(Function2.this.apply(x, y));
            }
        };
    }

    /**
     * returns function f(_, y) where f = this
     * @param x param to bind with
     * @return function which is result of binding
     */
    @NotNull
    default Function1<K, V> bind1(T x) {
        return new Function1<K, V>() {
            @Override
            public V apply(K y) {
                return Function2.this.apply(x, y);
            }
        };
    }

    /**
     * returns function f(x, _) where f = this
     * @param y param to bind with
     * @return function which is result of binding
     */
    @NotNull
    default Function1<T, V> bind2(K y) {
        return new Function1<T, V>() {
            @Override
            public V apply(T x) {
                return Function2.this.apply(x, y);
            }
        };
    }

    /**
     * alias for bind2
     */
    @NotNull
    default Function1<T, V> curry(K y) {
        return bind2(y);
    }
}
