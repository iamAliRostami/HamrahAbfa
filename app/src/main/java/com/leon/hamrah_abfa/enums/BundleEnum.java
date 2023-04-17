package com.leon.hamrah_abfa.enums;

public enum BundleEnum {
    NICKNAME("nickname"),
    OWNER("owner"),
    BILL_ID("bill_id"),
    SERVICE_TYPE("service_type"),
    DEBT("debt"),
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