package com.leon.hamrah_abfa.di.view_model;

import static android.content.Context.MODE_PRIVATE;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.FONT_STYLE;

import android.content.Context;
import android.content.SharedPreferences;

import com.leon.hamrah_abfa.enums.FontStyle;
import com.leon.hamrah_abfa.infrastructure.ISharedPreferenceManager;

import javax.inject.Inject;

public class SharedPreferenceModel implements ISharedPreferenceManager {
    private final SharedPreferences appPrefs;

    @Inject
    public SharedPreferenceModel(Context context, String xml) {
        appPrefs = context.getSharedPreferences(xml, MODE_PRIVATE);
    }

    @Override
    public boolean checkIsNotEmpty(String key) {
        if (appPrefs == null) {
            return false;
        } else if (appPrefs.getString(key, "").length() > 0) {
            return true;
        } else return !appPrefs.getString(key, "").isEmpty();
    }

    @Override
    public boolean checkIsNotEmpty(String key, boolean b) {
        return appPrefs != null;
    }


    @Override
    public void putData(String key, long value) {
        final SharedPreferences.Editor prefsEditor = appPrefs.edit();
        prefsEditor.putLong(key, value);
        prefsEditor.apply();
    }

    @Override
    public void putData(String key, int value) {
        final SharedPreferences.Editor prefsEditor = appPrefs.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }


    @Override
    public void putData(String key, String data) {
        final SharedPreferences.Editor prefsEditor = appPrefs.edit();
        prefsEditor.putString(key, data);
        prefsEditor.apply();
    }

    @Override
    public void putData(String key, boolean value) {
        final SharedPreferences.Editor prefsEditor = appPrefs.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    @Override
    public String getStringData(String key) {
        return appPrefs.getString(key, "");
    }

    @Override
    public int getIntData(String key) {
        return appPrefs.getInt(key, 1);
    }

    @Override
    public long getLongData(String key) {
        return appPrefs.getLong(key, 0);
    }

    @Override
    public int getIntNullData(String key) {
        return appPrefs.getInt(key, 0);
    }

    @Override
    public boolean getBoolData(String key) {
        return appPrefs.getBoolean(key, false);
    }

    @Override
    public boolean getBoolData(String key, boolean b) {
        return appPrefs.getBoolean(key, b);
    }

    @Override
    public void putFontStyle(FontStyle style) {
        final SharedPreferences.Editor prefsEditor = appPrefs.edit();
        prefsEditor.putString(FONT_STYLE.getValue(), style.name());
        prefsEditor.apply();
    }

    @Override
    public FontStyle getFontStyle() {
        return FontStyle.valueOf(appPrefs.getString(FONT_STYLE.getValue(), FontStyle.Medium.name()));
    }
}
