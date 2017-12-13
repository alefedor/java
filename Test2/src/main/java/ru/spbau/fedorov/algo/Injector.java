package ru.spbau.fedorov.algo;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Class that have a static method able to
 * create object by its name and implementations of classes
 */
public class Injector {

    private static Map<Class<?>, Object> objects = new HashMap<>();
    private static Set<Class<?> > triedToConstruct = new HashSet<>();

    /**
     * Constructs an object of needed class by its class name and given implementations of classes.
     * Each class should have only one constructor.
     * @param rootClassName name of class to construct
     * @param classes implementations of classes available to construct needed class.
     * @return an object of rootClass type
     * @throws ImplementationNotFoundException when couldn't find any implementation of class needed to construct rootClass
     * @throws AmbiguousImplementationException when found several implementations of class needed to construct rootClass
     * @throws InjectionCycleException when found a cycle in dependencies
     */
    @NotNull
    public static Object initialize(@NotNull String rootClassName, @NotNull Collection<Class<?>> classes) throws ImplementationNotFoundException, AmbiguousImplementationException, IllegalAccessException, InstantiationException, InvocationTargetException, InjectionCycleException {
        objects.clear();
        triedToConstruct.clear();

        Class<?> rootClass;
        try {
            rootClass = Class.forName(rootClassName);
        } catch (ClassNotFoundException e) {
            throw new ImplementationNotFoundException("Can not find rootClass");
        }

        return getObject(rootClass, classes);
    }

    @NotNull
    private static Object getObject(@NotNull Class<?> wantedClass, @NotNull Collection<Class<?>> classes) throws AmbiguousImplementationException, ImplementationNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, InjectionCycleException {
        if (objects.containsKey(wantedClass))
            return objects.get(wantedClass);

        if (triedToConstruct.contains(wantedClass))
            throw new InjectionCycleException("Cycle found");

        triedToConstruct.add(wantedClass);

        Constructor<?> constructor = wantedClass.getConstructors()[0];

        Class<?>[] paramTypes = constructor.getParameterTypes();
        List<Object> params = new ArrayList<>();

        for (Class<?> paramType : paramTypes) {
            Class<?> candidate = null;

            for (Class<?> c : classes)
                if (paramType.isAssignableFrom(c)) {
                    if (candidate == null)
                        candidate = c;
                    else
                        throw new AmbiguousImplementationException("Several variants for " + paramType.getSimpleName());
                }
            if (candidate == null)
                throw new ImplementationNotFoundException("No implementations for " + paramType.getSimpleName());

            params.add(getObject(candidate, classes));
        }

        objects.put(wantedClass, constructor.newInstance(params.toArray()));
        return objects.get(wantedClass);
    }
}
