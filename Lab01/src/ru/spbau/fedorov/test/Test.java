package ru.spbau.fedorov.test;

import ru.spbau.fedorov.algo.HashTable;

/**
 * Class with basic tests for HashTable
 * Contains method main
 */
public class Test {

    private static void newAssert(boolean statement) {
        if (!statement)
            System.exit(-1);
    }

    public static void main(String[] args) {
        HashTable ht = new HashTable();
        newAssert (ht.size() == 0);

        String s = "a", v = "v";
        for (int i = 0; i < 10; i++) {
            newAssert (ht.put(s, v) == null);
            newAssert(ht.size() == i + 1);
            s = s + "a";
            v = v + (char)('a' + i);
        }
        newAssert(ht.get("ret") == null);
        newAssert(ht.remove("ret") == null);

        s = "a";
        v = "v";
        for (int i = 0; i < 10; i++) {
            newAssert(ht.contains(s));
            newAssert(ht.put(s, v).equals(v));
            newAssert(ht.get(s).equals(v));
            s = s + "a";
            v = v + (char)('a' + i);
        }

        s = "a";
        v = "v";
        for (int i = 0; i < 10; i++) {
            newAssert(ht.remove(s).equals(v));
            newAssert(ht.size() == 9 - i);
            s = s + "a";
            v = v + (char)('a' + i);
        }

        s = "a";
        v = "v";
        for (int i = 0; i < 10; i++) {
            newAssert (ht.put(s, v) == null);
            newAssert(ht.size() == i + 1);
            s = s + "a";
            v = v + (char)('a' + i);
        }

        ht.clear();
        newAssert(ht.size() == 0);

        System.out.println("All is fine");
    }
}
