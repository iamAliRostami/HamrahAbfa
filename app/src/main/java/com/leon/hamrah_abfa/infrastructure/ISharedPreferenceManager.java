package com.leon.hamrah_abfa.infrastructure;

import com.leon.hamrah_abfa.enums.FontStyle;

public interface ISharedPreferenceManager {
    void putData(String key, String value);

    void putData(String key, int value);
    void putData(String key, long value);

    void putData(String key, boolean value);

    String getStringData(String key);

    int getIntData(String key);

    int getIntNullData(String key);

    boolean getBoolData(String key);

    boolean getBoolData(String key, boolean b);

    boolean checkIsNotEmpty(String key);

    boolean checkIsNotEmpty(String key, boolean b);

    long getLongData(String key);

    void putFontStyle(FontStyle style);
    FontStyle getFontStyle();
}
