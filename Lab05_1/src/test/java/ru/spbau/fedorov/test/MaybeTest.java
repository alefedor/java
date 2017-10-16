package ru.spbau.fedorov.test;

import org.junit.Test;
import org.junit.Assert.*;
import ru.spbau.fedorov.algo.Maybe;
import ru.spbau.fedorov.algo.NullStoredValueException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class MaybeTest {
    @Test
    public void testConstructionNothing() {
        Maybe<Integer> m = Maybe.nothing();
    }

    @Test
    public void testConstructionJust() throws Exception{
        Maybe<Integer> m = Maybe.just(1);
        assertEquals(1, (int)m.get());
    }

    @Test(expected = NullStoredValueException.class)
    public void testNothingException() throws NullStoredValueException{
        Maybe<Integer> m = Maybe.nothing();
        m.get();
    }

    @Test
    public void testIsPresentTrue() {
        assertEquals(true, Maybe.just(1).isPresent());
    }

    @Test
    public void testIsPresentFalse() {
        assertEquals(false, Maybe.nothing().isPresent());
    }

    @Test
    public void testMapNothing() {
        Maybe.nothing().map(x -> x);
    }

    @Test
    public void testMap() throws Exception{
        Maybe<Integer> m = Maybe.just(1);
        assertEquals(2, (int)m.map(x -> x * 2).get());
    }

    /**
     * Reads Integers line by line from file and wrap with Maybe.
     * If there if no Integers in line Maybe contains nothing.
     * @param filename name of file to read from
     * @return list of Maybe with Integers read or null if something went wrong
     */
    private List<Maybe<Integer>> readIntegers(String filename) {
        List<Maybe<Integer>> result = new ArrayList<Maybe<Integer>>();
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = in.readLine()) != null) {
                Integer read;
                try {
                    read = Integer.parseInt(line);
                    result.add(Maybe.just(read));
                } catch (NumberFormatException e) {
                    result.add(Maybe.nothing());
                }
            }
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    /**
     * Writes Integers to file line by line.
     * Write "null" if there is nothing in Maybe.
     * @param list list of Maybe with Integers to write
     * @param filename name of file to read to
     * @return true, if operation is successful and false, otherwise
     */
    private boolean writeIntegers(List<Maybe<Integer>> list, String filename) {
        try (FileWriter out = new FileWriter(filename)) {
            for (Maybe<Integer> element : list) {
                if (element.isPresent())
                    out.write(element.get() + "\n");
                else
                    out.write("null\n");
            }
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    @Test
    public void testReadWriteIntegersWithMaybe() {
        List<Maybe<Integer>> list = readIntegers("input.txt");
        assertNotEquals(null, list);

        List<Maybe<Integer>> mappedList = new ArrayList<>();


        for (Maybe<Integer> element : list)
             mappedList.add(element.map(x -> x * x));

        assertNotEquals(false, writeIntegers(mappedList, "output.txt"));

        String[] expectedLines = {"51984",
                                  "32041",
                                  "10000",
                                  "null",
                                  "null",
                                  "null"};
        List<Maybe<Integer>> writtenList = new ArrayList<Maybe<Integer>>();
        try (BufferedReader in = new BufferedReader(new FileReader("output.txt"))) {
            String line;
            int cnt = 0;
            while ((line = in.readLine()) != null) {
                if (cnt >= expectedLines.length)
                    fail();
                assertEquals(expectedLines[cnt], line);
                cnt++;
            }
            if (cnt != expectedLines.length)
                fail();
        } catch (Exception e) {
            fail();
        }
    }

}
