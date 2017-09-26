package ru.spbau.fedorov.algo;

import java.util.Arrays;
import java.util.Comparator;

public class Spiral {
    private int[][] data;

    private static int[] dx = {1, 0, -1, 0};
    private static int[] dy = {0, 1, 0, -1};

    /**
     * Constructs a member of Spiral type using a matrix
     * If matrix is incorrect, throws IllegalArgumentException
     * @param mat -- matrix nxn of int where n should be odd
     */
    Spiral(int[][] mat) {
        boolean correct = true;
        if (mat == null || mat.length % 2 != 1)
            correct = false;
        else {
            for (int i = 0; i < mat.length; i++)
                if (mat[i] == null || mat[i].length != mat.length)
                    correct = false;
        }

        if (!correct)
            throw new IllegalArgumentException("Not valid matrix");

        data = new int[mat.length][mat.length];
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                data[j][i] = mat[i][j];
    }

    private boolean correctPosition(int x, int y) {
        return (0 <= x && x < data.length && 0 <= y && y < data.length);
    }

    /**
     * Prints elements of contained matrix on the screen in spiral order
     * Start is the center
     */
    public void printSpiral() {
        int posX = data.length / 2;
        int posY = data.length / 2;
        int direction = 0;

        for (int pathLen = 1; correctPosition(posX, posY); pathLen++) {
            for (int step = 0; step < 2; step++) {
                for (int i = 0; i < pathLen; i++) {
                    if (correctPosition(posX, posY)) {
                        System.out.print(data[posX][posY]);
                        System.out.print(" ");
                        posX += dx[direction];
                        posY += dy[direction];
                    } else
                        break;
                }
                direction = (direction + 1) % 4;
            }
        }
    }

    /**
     * Prints elements of matrix on the screen
     */
    public void printMatrix() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                System.out.print(data[j][i]);
                if (j + 1 < data.length)
                    System.out.print(" ");
                else
                if (i + 1 < data.length)
                    System.out.print("\n");
            }
        }
    }

    /**
     * Sorts columns of matrix by their first elements
     */
    public void sortColumns() {
        Arrays.sort(data, new Spiral.ColumnComparator());
    }

    private class ColumnComparator implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b){
            return Integer.compare(a[0], b[0]);
        }
    }
}
