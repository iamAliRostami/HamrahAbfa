package com.leon.hamrah_abfa.enums;

public enum SharedReferenceKeys {
    ACCOUNT("account"),
    BILL_ID("bill_id"),
    IS_FIRST("is_first_111111111111111111");

    private final String value;

    SharedReferenceKeys(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
