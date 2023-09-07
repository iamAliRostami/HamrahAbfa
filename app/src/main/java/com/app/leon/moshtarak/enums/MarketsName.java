package com.app.leon.moshtarak.enums;

public enum MarketsName {
    CAFEBAZAAR(1),
    MYKET(2);
    private final int value;

    MarketsName(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }

}
