package com.asterion.webtesting.utilities;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public final class FileHandler {

    public static void writeFile(File file, String content) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Exception thrown while writing file: " + e.toString());
        }
    }

    public static void appendToFile(File file, String content){
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append(content);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Exception thrown while writing file: " + e.toString());
        }
    }

    public static String readFile(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Exception thrown while reading file: " + e.toString());
        }
    }

    public static void copyFile(File originalFile, File destinationFile) {
        try {
            Files.copy(originalFile.toPath(), destinationFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Exception thrown while copying file: " + e.toString());
        }
    }

    public static void moveFile(File originalFile, File destinationFile){
        try {
            FileUtils.moveFile(originalFile, destinationFile);
        } catch (IOException e) {
            throw new RuntimeException("Exception thrown while copying file: " + e.toString());
        }
    }

    public static void deleteFile(File localFile) {
        try {
            Files.delete(localFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Exception thrown while deleting file: " + e.toString());
        }
    }

    public static Optional<String> findExtension(File file) {
        String fileName = file.getName();
        if (fileName.contains(".")) {
            return Optional.of(fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase());
        }
        return Optional.empty();
    }
}
