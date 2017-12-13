package ru.spbau.fedorov.test;

/**
 * Doll which consists of several dolls
 */
public class PartedDoll implements Doll{
    public Doll depa;
    public Doll depb;

    public PartedDoll(RussianDollSmall a, RussianDollMedium b){
        depa = a;
        depb = b;
    }
}
