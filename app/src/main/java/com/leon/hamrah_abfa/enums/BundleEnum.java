package com.leon.hamrah_abfa.enums;

public enum BundleEnum {
    NICKNAME("nickname"),
    OWNER("owner"),
    BILL_ID("bill id"),
    LAST_PAGE("last page"),
    SERVICE_TYPE("service type"),
    LATITUDE("latitude"),
    LONGITUDE("longitude"),
    DEBT("debt"),
    MOBILE("mobile"),
    SHOW_PRE_FRAGMENT("show pre fragment"),
    TRACK_NUMBER("track number"),
    TEXT_BUTTON("text button"),
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