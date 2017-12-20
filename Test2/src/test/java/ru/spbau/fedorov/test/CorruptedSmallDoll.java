package ru.spbau.fedorov.test;

/**
 * Doll which for some reason requires larger doll, not smaller
 */
public class CorruptedSmallDoll implements IRussianDollSmall {
    public Doll dependency;

    public CorruptedSmallDoll(RussianDollMedium doll) {
        dependency = doll;
    }
}
