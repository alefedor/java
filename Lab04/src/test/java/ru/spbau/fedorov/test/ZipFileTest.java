package ru.spbau.fedorov.test;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;
import ru.spbau.fedorov.algo.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileTest {
    private static final byte DATA[][] ={ {0, 1, 2, 3, 4, 5, 6},
                                          {1, 2, 3, 4, 5},
                                          {2, 3, 4}};

    private void addFile(ZipOutputStream zos, String fileName, byte[] data) throws IOException {
        zos.putNextEntry(new ZipEntry(fileName));
        zos.write(data, 0, data.length);
        zos.closeEntry();
    }

    private String getParentDirectory(String file) throws IOException {
        File f = new File("");
        return f.getCanonicalPath();
    }

    private boolean checkFile(String fileName, byte[] data) throws IOException{
        Path path = Paths.get(fileName);
        return Arrays.equals(data, Files.readAllBytes(path));
    }

    private void deleteFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Files.delete(path);
    }

    private boolean fileExists(String fileName) throws IOException {
        File f = new File(fileName);
        return f.exists();
    }

    @Test
    public void testOneZipOneFile() throws IOException {
        final String zipName = "1.zip";
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipName))) {
            addFile(zos, "txt", DATA[0]);
        }

        ZipFile.main(new String[] {getParentDirectory(zipName), "txt"});
        Assert.assertEquals(true, checkFile("txt", DATA[0]));
        deleteFile("txt");
        deleteFile(zipName);
    }

    @Test
    public void testSeveralZip() throws IOException {
        final String zip1 = "1.zip";
        final String zip2 = "2.zip";
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip1))) {
            addFile(zos, "1txt", DATA[0]);
        }
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip2))) {
            addFile(zos, "2txt", DATA[1]);
        }

        ZipFile.main(new String[] {getParentDirectory(zip1), "[1-2]txt"});
        Assert.assertEquals(true, checkFile("1txt", DATA[0]));
        Assert.assertEquals(true, checkFile("2txt", DATA[1]));
        deleteFile("1txt");
        deleteFile("2txt");
        deleteFile(zip1);
        deleteFile(zip2);
    }

    @Test
    public void testManyFileRegexMatching() throws IOException {
        final String zipName = "1.zip";
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipName))) {
            addFile(zos, "1txt", DATA[0]);
            addFile(zos, "2txt", DATA[1]);
            addFile(zos, "3txt", DATA[2]);
        }

        ZipFile.main(new String[] {getParentDirectory(zipName), "[1-2]txt"});

        Assert.assertEquals(true, checkFile("1txt", DATA[0]));
        Assert.assertEquals(true, checkFile("2txt", DATA[1]));
        Assert.assertEquals(false, fileExists("3.txt"));

        deleteFile("1txt");
        deleteFile("2txt");
        deleteFile(zipName);
    }

    @Test
    public void testWithDirectories() throws IOException {
        final String zipName = "test.zip";
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipName))) {
            addFile(zos, "dir1/txt1", DATA[0]);
            addFile(zos, "dir1/txt2", DATA[1]);
            addFile(zos, "dir2/txt3", DATA[2]);
        }

        ZipFile.main(new String[] {getParentDirectory(zipName), "txt[1-3]"});
        Assert.assertEquals(true, checkFile("dir1/txt1", DATA[0]));
        Assert.assertEquals(true, checkFile("dir1/txt2", DATA[1]));
        Assert.assertEquals(true, checkFile("dir2/txt3", DATA[2]));

        FileUtils.deleteDirectory(new File("dir1"));
        FileUtils.deleteDirectory(new File("dir2"));
        deleteFile(zipName);
    }
}
