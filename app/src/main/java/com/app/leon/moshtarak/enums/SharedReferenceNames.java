package com.app.leon.moshtarak.enums;


import com.app.leon.moshtarak.BuildConfig;

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
