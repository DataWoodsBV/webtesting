package com.asterion.webtesting.utilities;

import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<HashMap<String, String>> map(File csvFile){
        List<HashMap<String, String>> maps = new ArrayList<>();
        try{
            FileReader filereader = new FileReader(csvFile);
            com.opencsv.CSVReader csvReader = new com.opencsv.CSVReader(filereader);
            List<String[]> lines = csvReader.readAll();
            List<String> headers = Arrays.stream(lines.get(0)).collect(Collectors.toList());
            List<String[]> valueRows = lines.subList(1, lines.size());
            for (String[] valueRow : valueRows) {
                HashMap<String, String> rowMap = new HashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    rowMap.put(headers.get(i), valueRow[i]);
                } maps.add(rowMap);
            } return maps;
        }catch (IOException | CsvException e){
            e.printStackTrace();
            return maps;
        }
    }
}
