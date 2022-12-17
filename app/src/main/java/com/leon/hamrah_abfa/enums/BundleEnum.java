package com.leon.hamrah_abfa.enums;

public enum BundleEnum {
    POSITION("position"),
    BACKGROUND_COLOR("background");

    private final String value;

    BundleEnum(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}