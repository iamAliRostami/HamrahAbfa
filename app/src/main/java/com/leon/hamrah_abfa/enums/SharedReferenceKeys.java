package com.leon.hamrah_abfa.enums;

public enum SharedReferenceKeys {
    ACCOUNT("account"),
    MOBILE("mobile_111111111111111111111111111111"),
    OLD_MOBILE("old_mobile_1111111111111111111111111111111"),
    BILL_ID("bill_id_88888888888888888888888888"),
    ID("id_88888888888888888888888888"),
    ALIAS("alias_88888888888888888888888888"),
    DEBT("debt_88888888888888888888888888"),
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
