package com.leon.hamrah_abfa.enums;

public enum BundleEnum {
    MOBILE("mobile"),
    POSITION("position"),
    TITLE("title"),
    CONTENT("content"),
    LOGO("logo"),
    BACKGROUND_COLOR("background");

    private final String value;

    BundleEnum(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}