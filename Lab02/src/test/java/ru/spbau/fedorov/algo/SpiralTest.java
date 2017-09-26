package ru.spbau.fedorov.algo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SpiralTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void createMockOutput() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void removeMockOutput() {
        System.setOut(null);
    }

    @Test
    public void testConstructionOneElement(){
        Spiral sp = new Spiral(new int[][] {{1}});
    }

    @Test
    public void testConstruction(){
        Spiral sp = new Spiral(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
    }

    @Test
    public void testPrintMatrixOneElement(){
        Spiral sp = new Spiral(new int[][] {{1}});
        sp.printMatrix();
        Assert.assertEquals("1", outContent.toString());
    }

    @Test
    public void testPrintMatrix(){
        Spiral sp = new Spiral(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        sp.printMatrix();
        Assert.assertEquals("1 2 3\n4 5 6\n7 8 9", outContent.toString());
    }

    @Test
    public void testPrintSpiralOneElement(){
        Spiral sp = new Spiral(new int[][] {{1}});
        sp.printSpiral();
        Assert.assertEquals("1 ", outContent.toString());
    }

    @Test
    public void testPrintSpiral(){
        Spiral sp = new Spiral(new int[][] {{7, 8, 9}, {6, 1, 2}, {5, 4, 3}});
        sp.printSpiral();
        Assert.assertEquals("1 2 3 4 5 6 7 8 9 ", outContent.toString());
    }

    @Test
    public void testSortColumnsOneElement(){
        Spiral sp = new Spiral(new int[][] {{1}});
        sp.sortColumns();
        sp.printMatrix();
        Assert.assertEquals("1", outContent.toString());
    }

    @Test
    public void testSortColumnsAlreadySorted(){
        Spiral sp = new Spiral(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        sp.sortColumns();
        sp.printMatrix();
        Assert.assertEquals("1 2 3\n4 5 6\n7 8 9", outContent.toString());
    }

    @Test
    public void testSortColumns(){
        Spiral sp = new Spiral(new int[][] {{9, 8, 2}, {4, 7, 1}, {5, 3, 6}});
        sp.sortColumns();
        sp.printMatrix();
        Assert.assertEquals("2 8 9\n1 7 4\n6 3 5", outContent.toString());
    }

}