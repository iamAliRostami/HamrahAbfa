package com.leon.hamrah_abfa.enums;

public enum BundleEnum {
    NICKNAME("nickname"),
    OWNER("owner"),
    BILL_ID("bill_id"),
    SERVICE_TYPE("service_type"),
    LATITUDE("latitude"),
    LONGITUDE("longitude"),
    DEBT("debt"),
    MOBILE("mobile"),
    TRACK_NUMBER("track_number"),
    POSITION("position"),
    TITLE("title"),
    DRAWABLE("drawable"),
    QUESTION("question"),
    YEY("yes"),
    NO("no"),
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