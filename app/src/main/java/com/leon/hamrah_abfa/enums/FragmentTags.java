package com.leon.hamrah_abfa.enums;

public enum FragmentTags {

    SUBMIT_INFO("submit info"),
    ASK_YES_NO("ask yes no"),
    SERVICE_LOCATION("service location");


    private final String value;

    FragmentTags(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
