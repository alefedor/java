package ru.spbau.fedorov.test;

public class RussianDollLarge implements IRussianDollLarge {
    public Doll dependency;

    public RussianDollLarge(IRussianDollMedium doll) {
        dependency = doll;
    }
}
