package com.asterion.webtesting.controllers;

import com.asterion.webtesting.domain.NLPLog;
import com.asterion.webtesting.utilities.CSVReader;
import com.asterion.webtesting.utilities.FolderHandler;
import com.asterion.webtesting.utilities.Statistics;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("nlp")
public class NLPController
{

    @GetMapping
    public ModelAndView nlp()
    {
        ModelAndView modelAndView = new ModelAndView("nlp");
        List<NLPLog> logs = getLogs();
        List<String> niches = logs.stream()
                .map(NLPLog::getNiche)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        List<String> nichesWithSize = logs.stream()
                .map(NLPLog::getNicheWithSize)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        TreeMap<String, Float> embeddingStatistics = getAverageEmbeddingAccuracies(logs);
        modelAndView.addObject("logs", logs);
        modelAndView.addObject("logsPerNiche", logsPerNiche(logs, niches));
        modelAndView.addObject("niches", niches);
        modelAndView.addObject("nichesWithSize", nichesWithSize);
        modelAndView.addObject("nicheStatistics", getNicheStatistics(logs, niches));
        return modelAndView;
    }

    private TreeMap<String, List<String>> getNicheStatistics(List<NLPLog> logs, List<String> niches){
        TreeMap<String, List<String>> map = new TreeMap<>();
        for (String niche : niches) {
            List<NLPLog> logsForNiche = logs.stream()
                    .filter(l -> l.getNiche().equals(niche))
                    .collect(Collectors.toList());
            List<Float> accuracies = logsForNiche.stream().map(NLPLog::getAccuracy).collect(Collectors.toList());
            List<String> stats = new ArrayList<>();
            stats.add(String.format("%.2f", Collections.max(accuracies)) + "% max\n");
            stats.add(String.format("%.2f", Statistics.average(accuracies)) + "% avg\n");
            stats.add(String.format("%.2f", Collections.min(accuracies)) + "% min\n");
            map.put(niche, stats);
        } return map;
    }

    private TreeMap<String, Float> getAverageEmbeddingAccuracies(List<NLPLog> logs){
        TreeMap<String, Float> map = new TreeMap<>();
        List<String> embeddings = logs.stream().map(NLPLog::getEmbedding).distinct().sorted().collect(Collectors.toList());
        for (String embedding : embeddings) {
            List<NLPLog> logsForEmbedding = logs.stream().filter(l -> l.getEmbedding().equals(embedding)).collect(Collectors.toList());
            List<Float> accuracies = logsForEmbedding.stream().map(NLPLog::getAccuracy).collect(Collectors.toList());
            map.put(embedding, Statistics.average(accuracies));
        }
        Float maxAccuracy = Collections.max(map.values());
        String embeddingWithHighestAverage = map.entrySet().stream()
                .filter(e -> Objects.equals(e.getValue(), maxAccuracy))
                .map(Map.Entry::getKey).findAny().orElse("NONE");
        System.out.println("Embedding with highest average: " + embeddingWithHighestAverage + " " + maxAccuracy);
        return map;
    }


    private TreeMap<String, List<NLPLog>> logsPerNiche(List<NLPLog> logs, List<String> niches){
        TreeMap<String, List<NLPLog>> map = new TreeMap<>();
        for (String niche : niches) {
            List<NLPLog> logsForNiche = logs.stream()
                    .filter(l -> l.getNiche().equals(niche))
                    .sorted(Comparator.comparing(NLPLog::getAccuracy).reversed())
                    .collect(Collectors.toList());
//                    .subList(0,20); //remove sublist for all logs
            map.put(niche, logsForNiche);
        } return map;
    }

    private List<NLPLog> getLogs(){
        List<NLPLog> logs = new ArrayList<>();
        Set<File> logFiles = FolderHandler.getFolderContent(new File("E:\\NLP\\data\\logs\\evaluationLogs"));
        for (File logFile : logFiles) {
//            if (!logFile.getName().contains("cluster")) continue;
            for (String row : CSVReader.getAllRows(logFile)) {
                String[] parts = row.split(",");
                logs.add(new NLPLog(parts[0], parts[1], translateNicheName(parts[2]), Integer.parseInt(parts[3]), Float.parseFloat(parts[4])));
            }
        }
        return logs;
    }

    private String translateNicheName(String nicheName){
        switch (nicheName){
            case "noticedAgency": return "Noticed Agency";
            case "bewaking": return "Bewaking";
            case "SocialeWoningbouwArchitectuur ": return "Sociale Woningbouw Architectuur";
            case "Beeldschermen en Touchsreens ": return "Beeldschermen en Touchscreens ";
            case "Archeologisch onderzoek": return "Archeologisch Onderzoek";
            default: return nicheName;
        }
    }

}
