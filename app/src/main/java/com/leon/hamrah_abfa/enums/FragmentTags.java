package com.leon.hamrah_abfa.enums;

public enum FragmentTags {

    SUBMIT_INFO("submit info"),
    SERVICE_LOCATION("service location");


    private final String value;

    FragmentTags(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
