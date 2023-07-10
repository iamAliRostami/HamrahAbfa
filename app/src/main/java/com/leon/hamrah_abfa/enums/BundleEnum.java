package com.leon.hamrah_abfa.enums;

public enum BundleEnum {
    ALIAS("alias"),
    OWNER("owner"),
    BILL_ID("bill id"),
    ID("id"),
    LAST_PAGE("last page"),
    SERVICE_TYPE("service type"),
    LATITUDE("latitude"),
    LONGITUDE("longitude"),
    DEBT("debt"),
    MOBILE("mobile"),
    SHOW_PRE_FRAGMENT("show pre fragment"),
    TRACK_NUMBER("track number"),
    FINISHED("finished"),
    TEXT_BUTTON("text button"),
    POSITION("position"),
    TITLE("title"),
    DRAWABLE("drawable"),
    QUESTION("question"),
    YEY("yes"),
    NO("no"),
    CONTENT("content"),
    LOGO("logo"),
    ANIM("anim"),
    BACKGROUND_COLOR("background");

    private final String value;

    BundleEnum(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}