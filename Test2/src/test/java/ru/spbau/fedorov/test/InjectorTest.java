package ru.spbau.fedorov.test;

import org.junit.Test;
import ru.spbau.fedorov.algo.AmbiguousImplementationException;
import ru.spbau.fedorov.algo.ImplementationNotFoundException;
import ru.spbau.fedorov.algo.InjectionCycleException;
import ru.spbau.fedorov.algo.Injector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class InjectorTest {
    @Test
    public void testShouldInitializeClassWithoutDependencies() throws Exception {
        Object object = Injector.initialize(RussianDollSmall.class.getCanonicalName(), Collections.emptyList());
        assertTrue(object instanceof RussianDollSmall);
    }

    @Test
    public void testShouldInitializeClassSimpleDependency() throws Exception {
        List<Class<?>> classes = Collections.singletonList(RussianDollSmall.class);
        Object object = Injector.initialize(RussianDollMedium.class.getCanonicalName(), classes);
        assertTrue(object instanceof RussianDollMedium);

        RussianDollMedium instance = (RussianDollMedium) object;
        assertTrue(instance.dependency != null);
        assertTrue(instance.dependency instanceof RussianDollSmall);
    }

    @Test
    public void testShouldInitializeClassWithDependency() throws Exception {
        List<Class<?>> classes = new ArrayList<>(Arrays.asList(RussianDollSmall.class, RussianDollMedium.class));
        Object object = Injector.initialize(RussianDollLarge.class.getCanonicalName(), classes);
        assertTrue(object instanceof RussianDollLarge);

        RussianDollLarge instance = (RussianDollLarge) object;
        assertTrue(instance.dependency != null);
        assertTrue(instance.dependency instanceof RussianDollMedium);
        assertTrue(((RussianDollMedium)instance.dependency).dependency != null);
        assertTrue(((RussianDollMedium)instance.dependency).dependency instanceof RussianDollSmall);
    }

    @Test
    public void testShouldInitializeClassSeveralDependencies() throws Exception {
        List<Class<?>> classes = new ArrayList<>(Arrays.asList(RussianDollSmall.class, RussianDollMedium.class));
        Object object = Injector.initialize(PartedDoll.class.getCanonicalName(), classes);
        assertTrue(object instanceof PartedDoll);

        PartedDoll instance = (PartedDoll) object;
        assertTrue(instance.depa != null);
        assertTrue(instance.depa instanceof RussianDollSmall);

        assertTrue(instance.depb != null);
        assertTrue(instance.depb instanceof RussianDollMedium);
    }

    @Test (expected = InjectionCycleException.class)
    public void testShouldThrowInjectionCycleException() throws Exception {
        List<Class<?>> classes = new ArrayList<>(Arrays.asList(RussianDollMedium.class, CorruptedSmallDoll.class));
        Object object = Injector.initialize(CorruptedSmallDoll.class.getCanonicalName(), classes);
    }

    @Test (expected = AmbiguousImplementationException.class)
    public void testShouldThrowAmbiguousImplementationException() throws Exception {
        List<Class<?>> classes = new ArrayList<>(Arrays.asList(RussianDollSmall.class, CorruptedSmallDoll.class));
        Object object = Injector.initialize(RussianDollMedium.class.getCanonicalName(), classes);
    }

    @Test (expected = ImplementationNotFoundException.class)
    public void testShouldThrowImplementationNotFoundException() throws Exception {
        List<Class<?>> classes = Collections.emptyList();
        Object object = Injector.initialize(RussianDollMedium.class.getCanonicalName(), classes);
    }
}
