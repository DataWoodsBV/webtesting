package com.asterion.webtesting.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class CSVReader {

    public static ArrayList<String> get(File file, String splitter, int index){
        String line = "";
        ArrayList<String> stringList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                stringList.add(line.split(splitter)[index]) ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }

    public static ArrayList<ArrayList<String>> get(File file, String splitter, Integer[] indices){
        String line = "";
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                ArrayList<String> strings = new ArrayList<>();
                for (Integer index : indices) {
                    try {
                        strings.add(line.split(splitter)[index]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        strings.add("");
                    }
                }
                arrayList.add(strings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<String> getAllRows(File file){
        String line = "";
        ArrayList<String> arrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                arrayList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static String getAllText(File file){
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void write(File csvFile, List<String> rows){
        try (PrintWriter pw = new PrintWriter(csvFile)) {
            for (String row : rows) {
                pw.println(row);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void append(File csvFile, List<String> rows){
        try (PrintWriter pw = new PrintWriter(csvFile)) {
            for (String row : rows) {
                pw.append("\n").append(row);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
