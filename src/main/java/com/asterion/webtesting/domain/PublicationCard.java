package com.asterion.webtesting.domain;

import com.asterion.webtesting.utilities.DateHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PublicationCard {

    private String saltedId;
    private Long id;
    private String source;
    private LocalDate date;
    private String title;
    private String description;
    private List<String> lotTitles;

    public PublicationCard(String saltedId, Long id, String source, String date, String title, String description, List<String> lotTitles) {
        this.saltedId = saltedId;
        this.id = id;
        this.source = source;
        this.date = DateHelper.getLocalDate(date, "yyyy-MM-dd");
        this.title = title;
        this.description = description;
        this.lotTitles = lotTitles.stream().filter(t -> !t.equals("0: nan")).collect(Collectors.toList());
    }

    public String getSaltedId() {
        return saltedId;
    }

    public Long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public boolean isEnot(){
        return getSource().equals("TED_ESENDERS_ENOT");
    }

    public boolean isTned(){
        return getSource().equals("TED_ESENDERS_TNED");
    }

    public boolean isEu(){
        return !isEnot() && !isTned();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getLotTitles() {
        return lotTitles;
    }

    public void addLotTitle(String title){
        this.lotTitles.add(title);
    }

    public String getUrl(){
        return "https://tenderwolf.com/app/tender/" + getSaltedId() + "NL";
    }
}
