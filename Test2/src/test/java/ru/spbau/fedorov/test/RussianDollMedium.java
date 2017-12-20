package ru.spbau.fedorov.test;

public class RussianDollMedium implements IRussianDollMedium {
    public Doll dependency;

    public RussianDollMedium(IRussianDollSmall doll) {
        dependency = doll;
    }
}
