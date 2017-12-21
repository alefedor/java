package ru.spbau.fedorov.test;

import org.junit.Test;
import ru.spbau.fedorov.algo.SecondPartTasks;

import com.google.common.collect.ImmutableMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SecondPartTasksTest {

    private static final String[] quotes = {
            "rains like cats and dogs",
            "javadog  wow",
            "javadoc",
            "hotdog is out there",
            "trust no dog",
            "what a wonderful day"
    };

    private static final String[] answer = {
            "rains like cats and dogs",
            "javadog  wow",
            "hotdog is out there",
            "trust no dog"
    };

    @Test
    public void testFindQuotes() throws IOException {
        List<String> paths = new ArrayList<>();

        for (int i = 0; i < quotes.length; i += 2){
            File temp = File.createTempFile("file", null);
            paths.add(temp.getPath());
            try (FileWriter fw = new FileWriter(temp)){
                fw.write(quotes[i]);
                fw.write('\n');
                fw.write(quotes[i + 1]);
            }
        }

        List<String> result = SecondPartTasks.findQuotes(paths, "dog");
        System.out.println(result);
        assertEquals(answer.length, result.size());
        for (int i = 0; i < answer.length; i++)
            assertEquals(answer[i], result.get(i));
    }

    @Test
    public void testPiDividedBy4() {
        assertEquals(Math.PI / 4, SecondPartTasks.piDividedBy4(), 0.001);
        assertEquals(Math.PI / 4, SecondPartTasks.piDividedBy4(), 0.001);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> compositions = ImmutableMap.of(
                "a1", Arrays.asList(quotes[0], quotes[1]),
                "a2", Arrays.asList(quotes[2], quotes[3]),
                "a3", Arrays.asList(quotes[4], quotes[5])
        );
        assertEquals("a1", SecondPartTasks.findPrinter(compositions));
    }

    @Test
    public void testCalculateGlobalOrder() {
        Map<String, Integer> cplusplus = ImmutableMap.of(
                "shortness", 1,
                "libraries", 2,
                "safety", 4
        );

        Map<String, Integer> python = ImmutableMap.of(
                "optimization", 10
        );

        Map<String, Integer> java = ImmutableMap.of(
                "shortness", 10,
                "optimization", 5,
                "readability", 10,
                "safety", 1
        );

        Map<String, Integer> toDoList = ImmutableMap.of(
                "shortness", 11,
                "libraries", 2,
                "safety", 5,
                "optimization", 15,
                "readability", 10
        );

        assertEquals(toDoList, SecondPartTasks.calculateGlobalOrder(Arrays.asList(cplusplus, python, java)));
    }
}