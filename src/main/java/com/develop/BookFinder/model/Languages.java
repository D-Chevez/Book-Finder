package com.develop.BookFinder.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Languages {

    ARABE("ar"),
    CHINO("zh"),
    CASTELLANO("es"),
    FRANCES("fr"),
    INGLES("en"),
    JAPONES("ja"),
    PORTUGUES("pt"),
    TAILANDES("th"),
    UNKNOWN("Unknown");

    private final String code;

    Languages(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static Languages fromString(String code) {
        for (Languages language : Languages.values()) {
            if (language.getCode().equalsIgnoreCase(code)) {
                return language;
            }
        }
        return UNKNOWN;
    }



    @Override
    public String toString() {
        String lower = name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}
