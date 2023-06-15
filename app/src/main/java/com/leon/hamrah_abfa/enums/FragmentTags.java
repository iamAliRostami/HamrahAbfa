package com.leon.hamrah_abfa.enums;

public enum FragmentTags {

    SUBMIT_INFO("submit info"),
    ASK_YES_NO("ask yes no"),
    REQUEST_DONE("request done"),
    BRANCH_LOCATION("branch location"),
    ACTIVE_SESSION("active session"),
    CHANGE_THEME("change theme"),
    FOLLOW_REQUEST_TRACK("follow request track"),
    FOLLOW_REQUEST_LEVEL("follow request level"),
    WAITING("waiting"),
    SERVICE_LOCATION("service location");


    private final String value;

    FragmentTags(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
