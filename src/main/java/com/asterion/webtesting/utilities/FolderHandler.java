package com.asterion.webtesting.utilities;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class FolderHandler {

    public static final char S = File.separatorChar;

    public static File createOrReturnFolder(File folder) {
        if (!fileExists(folder)) folder.mkdirs();
        return folder;
    }

    public static void emptyAndDeleteFolder(File folder) {
        checkIfFolderExistsAndIsDirectory(folder);
        if(!isFolderEmpty(folder)) emptyFolder(folder);
        folder.delete();
    }

    public static void emptyFolder(File folder) {
        checkIfFolderExistsAndIsDirectory(folder);
        if (!isFolderEmpty(folder)) {
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    emptyFolder(file);
                }
                boolean deleted = file.delete();
            }
        }
    }

    public static Set<File> getFolderContent(File folder) {
        checkIfFolderExistsAndIsDirectory(folder);
        Set<File> folderContent = new HashSet<>();
        if (!isFolderEmpty(folder)) {
            folderContent.addAll(Arrays.asList(folder.listFiles()));
        }
        return folderContent;
    }

    public static Set<File> getSubFolders(File folder){
        checkIfFolderExistsAndIsDirectory(folder);
        return getFolderContent(folder).stream()
                .filter(File::isDirectory)
                .collect(Collectors.toSet());
    }

    public static Set<File> getFilesWithExtension(File folder, String extension){
        checkIfFolderExistsAndIsDirectory(folder);
        if(!extension.startsWith(".")) throw new RuntimeException("Illegal extension: " + extension);
        return getFolderContent(folder).stream()
                .filter(file -> file.getName().endsWith(extension))
                .collect(Collectors.toSet());
    }

    public static Set<File> getFolderContentRecursively(File folder) {
        Set<File> folderContent = getFolderContent(folder);
        for(File file : folderContent){
            if (file.isDirectory()) {
                folderContent.addAll(getFolderContentRecursively(file));
            }
        }
        return folderContent;
    }

    public static boolean doesFolderContainFolder(File folder) {
        checkIfFolderExistsAndIsDirectory(folder);
        if (!isFolderEmpty(folder)) {
            for (File file : getFolderContentRecursively(folder)) {
                if (isDirectory(file)) return true;
            }
        }
        return false;
    }

    public static void copyFolderAndContent(File originalFolder, File destinationFolder){
        File copiedFolder = createOrReturnFolder(new File(destinationFolder.getAbsolutePath() + S + originalFolder.getName()));
        for (File file : getFolderContent(originalFolder)) {
            if(!isDirectory(file)){
                FileHandler.copyFile(file, new File(copiedFolder.getAbsolutePath() + S + file.getName()));
            }
            else copyFolderAndContent(file, copiedFolder);
        }
    }

    public static boolean isFolderEmpty(File folder) {
        checkIfFolderExistsAndIsDirectory(folder);
        return folder.list().length == 0;
    }

    private static void checkIfFolderExistsAndIsDirectory(File folder) {
        if(folder == null) throw new RuntimeException("folder does not exist: " + folder.getAbsolutePath());
        if (!fileExists(folder)) throw new RuntimeException("folder does not exist: " + folder.getAbsolutePath());
        if (!isDirectory(folder)) throw new RuntimeException("folder is not a directory: " + folder.getAbsolutePath());
    }

    public static boolean fileExists(File file) {
        return file.exists();
    }

    public static boolean isDirectory(File file) {
        return file.isDirectory();
    }

    public static boolean folderExists(File file){
        return fileExists(file) && isDirectory(file);
    }

}