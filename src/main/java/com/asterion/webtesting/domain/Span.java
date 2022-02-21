package com.asterion.webtesting.domain;

public class Span {

    private String text;
    private String className;

    public Span(String text, String className) {
        this.text = text;
        this.className = className;
    }

    public String getText() {
        return text;
    }

    public String getClassName() {
        return className;
    }
}
