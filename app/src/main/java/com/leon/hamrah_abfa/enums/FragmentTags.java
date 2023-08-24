package com.leon.hamrah_abfa.enums;

public enum FragmentTags {

    SUBMIT_INFO("submit info"),
    ASK_YES_NO("ask yes no"),
    REQUEST_DONE("request done"),
    MESSAGE_DETAIL("message detail"),
    BRANCH_LOCATION("branch location"),
    ACTIVE_SESSION("active session"),
    CHANGE_THEME("change theme"),
    FOLLOW_REQUEST_TRACK("follow request track"),
    FOLLOW_REQUEST_LEVEL("follow request level"),
    FOLLOW_REQUEST_ITEM("follow request item"),
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
