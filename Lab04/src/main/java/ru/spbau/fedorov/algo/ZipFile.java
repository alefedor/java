package ru.spbau.fedorov.algo;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.nio.file.NotDirectoryException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class with method main which is able to unzip all files in a specific directory matching regex
 */
public class ZipFile {

    private static final String USAGE = "Usage: <path to file or directory to zip> <regex to match>";

    /**
     * Method main, which finds all zip archives in directory and unzips all files
     * in them matching regex
     * @param args array of Strings which should consist of two elements {path to search in, regex to match}
     * @throws IOException if an error occurred while working with files
     */
    public static void main(String[] args) throws IOException{
        if (args.length != 2){
            System.out.println(USAGE);
            System.exit(0);
        }

        final String path = args[0];
        final String regex = args[1];
        for (String zipName : getZipFiles(path))
            unzipMatchingRegex(zipName, regex, path);
    }

    @NotNull
    private static ArrayList<String> getZipFiles(@NotNull String path) throws IOException{
        File dir = new File(path);

        if (!dir.isDirectory())
            throw new NotDirectoryException(path);

        ArrayList<String> result = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files == null)
            return result;
        for (File file : files)
            if (isZipFile(file)){
                result.add(file.getName());
            }

        return result;
    }

    private static boolean isZipFile(File file) throws IOException {
        if (file.isDirectory()) {
            return false;
        }
        int magicBytes;
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            magicBytes = in.readInt();
            in.close();
        }
        return (magicBytes == 0x504b0304) || (magicBytes == 0x504b0506) || (magicBytes == 0x504b0708);
    }

    private static void unzipMatchingRegex(@NotNull String zipName, @NotNull String regex, @NotNull String path) throws IOException{
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipName))) {
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(fileName);
                if (Pattern.matches(regex, newFile.getName())) {
                    File parentDir = newFile.getParentFile();
                    if (parentDir != null)
                        parentDir.mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                ze = zis.getNextEntry();
            }
        }
    }
}
