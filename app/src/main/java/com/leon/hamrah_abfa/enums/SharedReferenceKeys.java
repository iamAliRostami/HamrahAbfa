package com.leon.hamrah_abfa.enums;

public enum SharedReferenceKeys {
    ACCOUNT("account"),
    MOBILE("mobile_11111111111111111111111111111111111"),
    BILL_ID("bill_id_99999999999999999999999"),
    ID("id_99999999999999999999999"),
    ALIAS("alias_99999999999999999999999"),
    DEBT("debt_99999999999999999999999"),
    DEFAULT_BILL_ID("default_bill_id"),
    FONT_STYLE("font_style_1111111111111111111"),
    TOKEN("token"),
    THEME_MODE("theme_mode_1111111111111111111111111111"),
    THEME("theme_1111111111111111111111"),
    IS_FIRST("is_first_111111111111111");


    private final String value;

    SharedReferenceKeys(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
