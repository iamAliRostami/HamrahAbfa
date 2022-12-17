package com.leon.hamrah_abfa.enums;

public enum SharedReferenceKeys {
    ACCOUNT("account"),
    IS_FIRST("is_first");

    private final String value;

    SharedReferenceKeys(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
