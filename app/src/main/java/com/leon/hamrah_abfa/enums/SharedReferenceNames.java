package com.leon.hamrah_abfa.enums;

import com.leon.hamrah_abfa.BuildConfig;

public enum SharedReferenceNames {
    ACCOUNT();

    private final String value;

    SharedReferenceNames() {
        value = BuildConfig.APPLICATION_ID.concat(".account_info");
    }

    public String getValue() {
        return value;
    }
}
