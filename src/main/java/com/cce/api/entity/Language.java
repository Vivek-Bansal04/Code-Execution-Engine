package com.cce.api.entity;

public enum Language {
    JAVA("Java");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

