package ru.spbau.fedorov.algo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class with implemented functional methods for
 * Function1, Function2 and Predicate
 */
public class Collections {
    /**
     * Applies f to every element of Iterable
     * @param f function to apply
     * @param a container to convert
     * @param <T> type of elements in container
     * @param <K> type of result value of function
     * @return list of results of applications
     */
    @NotNull
    public static <T, K> List<K> map(@NotNull Function1<? super T, ? extends K> f, @NotNull Iterable<T> a) {
        List<K> result = new ArrayList<>();

        for (T elem : a)
            result.add(f.apply(elem));

        return result;
    }

    /**
     * Creates list with all elements matching predicate
     * @param p predicate to match
     * @param a container to look throught
     * @param <T> type of elements in container
     * @return list of matched elements
     */
    @NotNull
    public static <T> List<T> filter(@NotNull Predicate<? super T> p, @NotNull Iterable<T> a) {
        List<T> result = new ArrayList<>();

        for (T elem : a)
            if (p.apply(elem))
                result.add(elem);

        return result;
    }

    /**
     * Takes all elements into list from the beginning while matching predicate.
     * Stops when an element is not matched
     * @param p predicate to match
     * @param a container to look throught
     * @param <T> type of elements in container
     * @return list of matched elements
     */
    @NotNull
    public static <T> List<T> takeWhile(@NotNull Predicate<? super T> p, @NotNull Iterable<T> a) {
        List<T> result = new ArrayList<>();

        for (T elem : a)
            if (p.apply(elem))
                result.add(elem);
            else
                break;

        return result;
    }

    /**
     * Takes all elements into list from the beginning while not matching predicate.
     * Stops when an element is matched
     * @param p predicate to match
     * @param a container to look throught
     * @param <T> type of elements in container
     * @return list of not matched elements
     */
    @NotNull
    public static <T> List<T> takeUnless(@NotNull Predicate<? super T> p, @NotNull Iterable<T> a) {
        return takeWhile(p.not(), a);
    }

    /**
     * Reduces collection by applying function to every element from left to right
     * @param f function to apply. First argument is an accumulated value
     * @param accum initial state of accumulated value
     * @param c collection to reduce
     * @param <T> type of result of function
     * @param <K> type of elements in the collection
     * @return reduced value
     */
    @NotNull
    public static <T, K> T foldl(@NotNull Function2<? super T, ? super K, ? extends T> f, T accum, @NotNull Collection<K> c) {
        for (K elem : c)
            accum = f.apply(accum, elem);

        return accum;
    }

    /**
     * Reduces collection by applying function to every element from right to left
     * @param f function to apply. Second argument is an accumulated value
     * @param accum initial state of accumulated value
     * @param c collection to reduce
     * @param <T> type of result of function
     * @param <K> type of elements in the collection
     * @return reduced value
     */
    @NotNull
    public static <T, K> T foldr(@NotNull Function2<? super K, ? super T, ? extends T> f, T accum, @NotNull Collection<K> c) {
        List<K> list = new ArrayList<>();
        list.addAll(c);

        for (int i = list.size() - 1; i >= 0; i--)
            accum = f.apply(list.get(i), accum);

        return accum;
    }

}
