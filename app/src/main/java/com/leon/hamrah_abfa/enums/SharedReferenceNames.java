package com.leon.hamrah_abfa.enums;

public enum SharedReferenceNames {
    ACCOUNT();

    private final String value;

    SharedReferenceNames() {
        value = "com.app.leon.reading_counter.account_info";
    }

    public String getValue() {
        return value;
    }
}
