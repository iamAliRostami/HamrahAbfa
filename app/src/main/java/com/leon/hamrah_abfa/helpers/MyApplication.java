package com.leon.hamrah_abfa.helpers;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.THEME_MODE;
import static com.leon.hamrah_abfa.enums.SharedReferenceNames.ACCOUNT;
import static com.leon.hamrah_abfa.helpers.Constants.FONT_NAME;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_DARK;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_DEFAULT;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_LIGHT;
import static com.leon.hamrah_abfa.helpers.Constants.TOAST_TEXT_SIZE;
import static com.leon.toast.RTLToast.Config;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.leon.hamrah_abfa.BuildConfig;
import com.leon.hamrah_abfa.di.component.ApplicationComponent;
import com.leon.hamrah_abfa.di.component.DaggerApplicationComponent;
import com.leon.hamrah_abfa.di.module.MyDatabaseModule;
import com.leon.hamrah_abfa.di.module.NetworkModule;
import com.leon.hamrah_abfa.di.module.SharedPreferenceModule;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class MyApplication extends Application {
    private static ApplicationComponent applicationComponent;
    private static MyApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        setApplicationComponent();
        setDefaultNightMode();
        Config.getInstance().setToastTypeface(Typeface.createFromAsset(getAssets(), FONT_NAME))
                .setTextSize(TOAST_TEXT_SIZE).apply();
        if (!BuildConfig.BUILD_TYPE.equals("release")) setupYandex();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getInstance());
    }

    private void setDefaultNightMode() {
        switch (applicationComponent.SharedPreferenceModel().getIntNullData(THEME_MODE.getValue())) {
            case THEME_DEFAULT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case THEME_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case THEME_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
    }

    private void setApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule())
                .myDatabaseModule(new MyDatabaseModule(this))
                .sharedPreferenceModule(new SharedPreferenceModule(this, ACCOUNT)).build();
        applicationComponent.inject(getInstance());
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    protected void setupYandex() {
        final String YANDEX_API_KEY = "0f22d2c8-1621-4448-b0db-267ab4131561";
        final YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(YANDEX_API_KEY)
                .withLogs().withAppVersion(BuildConfig.VERSION_NAME).build();
        YandexMetrica.activate(this, config);
        YandexMetrica.enableActivityAutoTracking(getInstance());
    }
}