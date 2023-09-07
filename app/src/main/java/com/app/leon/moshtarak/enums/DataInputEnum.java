package com.app.leon.moshtarak.enums;

public enum DataInputEnum {
    HAS_RETRY_ONCE("has_retried_once"),
    BACKOFF_DELAY("backoff_delay"),
    ATTEMPT_COUNT("attempt_count");
    private final String value;

    DataInputEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
