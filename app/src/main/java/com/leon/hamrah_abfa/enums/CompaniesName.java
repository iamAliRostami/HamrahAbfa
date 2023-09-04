package com.leon.hamrah_abfa.enums;

public enum CompaniesName {
    ZONE1(1),
    ZONE2(2),
    ZONE3(3),
    ZONE4(4),
    ZONE5(5),
    ZONE6(6),
    TSW(7),
    TE(8),
    TSE(9),
    TW(10),
    ESF(11),
    DEBUG(12),
    ESF_MAP(13),
    KSH(14);
    private final int value;

    CompaniesName(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }

}
