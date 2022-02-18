package com.asterion.webtesting;

import com.asterion.webtesting.utilities.CSVReader;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class main {

    public static void main(String[] args) {
        HashMap<String, Float> scoreMap = new HashMap<>();
        Set<String> niches = new HashSet<>();
        for (List<String> log : getLogs()) {
            String li = convertToLi(log);
            Float percentage = Float.valueOf(log.get(4));
            scoreMap.put(li, percentage);
            niches.add(log.get(2) + " (" + log.get(3) + ")");
        }
        System.out.println(niches.toString());
        for (String niche : niches) {
            System.out.println("<span class=\"niche\">" + niche + "</span>");
        }
//        scoreMap.entrySet()
//                .stream()
//                .sorted(Map.Entry.<String, Float>comparingByValue().reversed())
//                .forEach(e -> System.out.println(e.getKey()));
    }

    private static String convertToLi(List<String> log){
        StringBuilder liBuilder = new StringBuilder();
        liBuilder.append("<li>");
        List<String> spans = getSpans(log);
        for (String span : spans) {
            liBuilder.append(span);
        }
        liBuilder.append("</li>");
       return liBuilder.toString();
    }

    private static List<String> getSpans(List<String> log){
        List<String> spans = new ArrayList<>();
        String embeddingName = log.get(1);
        if (embeddingName.contains("lot_code_descriptions")){
            embeddingName = embeddingName.replace("lot_code_descriptions", "");
            spans.add(getSpan("Lot Codes"));
        }
        if (embeddingName.contains("lot_description_preprocessed")){
            embeddingName = embeddingName.replace("lot_description_preprocessed", "");
            spans.add(getSpan("Lot Description"));
        }
        if (embeddingName.contains("lot_title_preprocessed")){
            embeddingName = embeddingName.replace("lot_title_preprocessed", "");
            spans.add(getSpan("Lot Title"));
        }
        if (embeddingName.contains("publication_code_descriptions")){
            embeddingName = embeddingName.replace("publication_code_descriptions", "");
            spans.add(getSpan("Publication Codes"));
        }
        if (embeddingName.contains("description_preprocessed")){
            embeddingName = embeddingName.replace("description_preprocessed", "");
            spans.add(getSpan("Publication Description"));
        }
        if (embeddingName.contains("title_preprocessed")){
            embeddingName = embeddingName.replace("title_preprocessed", "");
            spans.add(getSpan("Publication Title"));
        }
        String niche = log.get(2);
        spans.add("<span class=\"niche\">" + niche + "</span>");
        String percentage = log.get(4);
        spans.add(getSpan(percentage + "%"));
        Collections.reverse(spans);
        return spans;
    }

    private static String getSpan(String code){
        return "<span " + getClass(code) + ">" + code + "</span>";
    }
    private static String getClass(String code){
        switch (code){
            case "Publication Description": return "class=\"publication-description\"";
            case "Publication Title": return "class=\"publication-title\"";
            case "Publication Codes": return "class=\"publication-codes\"";
            case "Lot Description": return "class=\"lot-description\"";
            case "Lot Title": return "class=\"lot-title\"";
            case "Lot Codes": return "class=\"lot-codes\"";
            case "overall": return "class=\"strategy-overall\"";
            case "average": return "class=\"strategy-average\"";
            case "cluster": return "class=\"strategy-cluster\"";
            default: return "class=\"evaluation-field\"";
        }
    }

    private static List<List<String>> getLogs(){
        List<String> rows = CSVReader.getAllRows(new File("E:\\NLP\\data\\logs\\log_average_results.csv"));
        List<List<String>> values = new ArrayList<>();
        for (String row : rows) {
            values.add(Arrays.stream(row.split(",")).collect(Collectors.toList()));
        } return values;
    }

   }
