package com.leon.hamrah_abfa.enums;

public enum SharedReferenceKeys {
    ACCOUNT("account"),
    MOBILE("mobile_11111111111111111111111111111111111111"),
    FONT_STYLE("font_style_1111111111111111111"),
    TOKEN("token"),
    BILL_ID("bill_id_1111111111111111111111111111"),
    THEME_MODE("theme_mode_1111111111111111111111111111"),
    THEME("theme_1111111111111111111111"),
    OWNER("owner_1111111111111111111111111111"),
    ALIAS("nickname_1111111111111111111111111111"),
    DEBT("debt_1111111111111111111111111111"),
    DEFAULT_BILL_ID("default_bill_id"),
    IS_FIRST("is_first_111111111111111");


    private final String value;

    SharedReferenceKeys(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
}
