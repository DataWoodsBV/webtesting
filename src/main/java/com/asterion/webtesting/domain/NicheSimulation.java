package com.asterion.webtesting.domain;

import com.asterion.webtesting.utilities.CSVReader;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class NicheSimulation {

    private HashMap<String, String> information;
    private List<PublicationCard> nicheCards;
    private List<PublicationCard> simulationCards;

    public NicheSimulation(File simulationFolder) {
        this.information = CSVReader.map(new File(simulationFolder.getAbsolutePath() + "\\information.csv")).get(0);
        this.nicheCards = getPublicationCards(new File(simulationFolder.getAbsolutePath() + "\\niche.csv"));
        this.simulationCards = getPublicationCards(new File(simulationFolder.getAbsolutePath() + "\\simulation.csv"));
    }

    public HashMap<String, String> getInformation() {
        return information;
    }

    public List<String> getInformationList(){
        List<String> infoList = new ArrayList<>();
        for (String key : getInformation().keySet()) {
            infoList.add(key + ": " + getInformation().get(key));
        } return infoList;
    }

    public List<PublicationCard> getNicheCards() {
        return nicheCards;
    }

    public List<PublicationCard> getSimulationCards() {
        return simulationCards;
    }

    private List<PublicationCard> getPublicationCards(File csvFile){
        HashMap<String, PublicationCard> cardsMap = new HashMap<>();
        for (HashMap<String, String> rowMap : CSVReader.map(csvFile)) {
            String key = rowMap.get("salted_id");
            if (cardsMap.containsKey(key)) {
                getLotTitle(rowMap).ifPresent(t -> cardsMap.get(key).addLotTitle(t));
            }
            else {
                List<String> lotTitles = new ArrayList<>();
                getLotTitle(rowMap).ifPresent(lotTitles::add);
                cardsMap.put(key, new PublicationCard(
                        rowMap.get("salted_id"),
                        Long.valueOf(rowMap.get("id")),
                        rowMap.get("source"),
                        rowMap.get("publication_date"),
                        rowMap.get("title"),
                        rowMap.get("description"),
                        lotTitles));
            }
        } return cardsMap.values().stream().sorted(Comparator.comparing(PublicationCard::getDate)).collect(Collectors.toList());
    }

    private Optional<String> getLotTitle(HashMap<String, String> rowMap){
        String title = rowMap.get("lot_title");
        if (title.isBlank()) return Optional.empty();
//        String number = rowMap.get("lot_number");
        return Optional.of(title);
    }
}
