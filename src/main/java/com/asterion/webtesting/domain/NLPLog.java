package com.asterion.webtesting.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NLPLog {

    private String strategy;
    private String embedding;
    private String niche;
    private int nicheSize;
    private float accuracy;

    public NLPLog(String strategy, String embedding, String niche, int nicheSize, float accuracy) {
        this.strategy = strategy;
        this.embedding = embedding;
        this.niche = niche;
        this.nicheSize = nicheSize;
        this.accuracy = accuracy;
    }

    public String getStrategy() {
        return strategy;
    }

    public String getEmbedding() {
        return embedding;
    }

    public String getNiche() {
        return niche;
    }

    public String getNicheWithSize(){
        return getNiche() + " (" + getNicheSize() + ")";
    }

    public int getNicheSize() {
        return nicheSize;
    }

    public float getAccuracy() {
        return accuracy;
    }

    @Override
    public String toString() {
        return "NLPLog{" +
                "strategy='" + strategy + '\'' +
                ", embedding='" + embedding + '\'' +
                ", niche='" + niche + '\'' +
                ", nicheSize=" + nicheSize +
                ", accuracy=" + accuracy +
                '}';
    }

    public List<Span> getSpans(){
        List<Span> spans = new ArrayList<>();
        String embeddingName = getEmbedding();
        if (embeddingName.contains("lot_code_descriptions")){
            embeddingName = embeddingName.replace("lot_code_descriptions", "");
            spans.add(new Span("Lot Codes", "lot-codes"));
        }
        if (embeddingName.contains("lot_description_preprocessed")){
            embeddingName = embeddingName.replace("lot_description_preprocessed", "");
            spans.add(new Span("Lot Description", "lot-description"));
        }
        if (embeddingName.contains("lot_title_preprocessed")){
            embeddingName = embeddingName.replace("lot_title_preprocessed", "");
            spans.add(new Span("Lot Title", "lot-title"));
        }
        if (embeddingName.contains("publication_code_descriptions")){
            embeddingName = embeddingName.replace("publication_code_descriptions", "");
            spans.add(new Span("Publication Codes", "publication-codes"));
        }
        if (embeddingName.contains("description_preprocessed")){
            embeddingName = embeddingName.replace("description_preprocessed", "");
            spans.add(new Span("Publication Description", "publication-description"));
        }
        if (embeddingName.contains("title_preprocessed")){
            embeddingName = embeddingName.replace("title_preprocessed", "");
            spans.add(new Span("Publication Title", "publication-title"));
        }
        spans.add(new Span(getStrategy(), "strategy-" + getStrategy().toLowerCase()));
        spans.add(new Span(String.format("%.2f", getAccuracy()) + "%", "evaluation-field"));
        Collections.reverse(spans);
        return spans;
    }
}
