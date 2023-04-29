package com.leon.hamrah_abfa.helpers;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.THEME;
import static com.leon.hamrah_abfa.enums.SharedReferenceNames.ACCOUNT;
import static com.leon.hamrah_abfa.helpers.Constants.FONT_NAME;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_DARK;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_DEFAULT;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_LIGHT;
import static com.leon.hamrah_abfa.helpers.Constants.TOAST_TEXT_SIZE;
import static com.leon.toast.RTLToast.Config;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.leon.hamrah_abfa.BuildConfig;
import com.leon.hamrah_abfa.di.component.ApplicationComponent;
import com.leon.hamrah_abfa.di.component.DaggerApplicationComponent;
import com.leon.hamrah_abfa.di.module.CustomProgressModule;
import com.leon.hamrah_abfa.di.module.MyDatabaseModule;
import com.leon.hamrah_abfa.di.module.NetworkModule;
import com.leon.hamrah_abfa.di.module.SharedPreferenceModule;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class MyApplication extends Application {
    private static Context appContext;
    private static ApplicationComponent applicationComponent;
    private static MyApplication singleton = null;

    @Override
    public void onCreate() {


        super.onCreate();
        appContext = getApplicationContext();
        Config.getInstance().setToastTypeface(Typeface.createFromAsset(getAssets(), FONT_NAME))
                .setTextSize(TOAST_TEXT_SIZE).apply();
        setApplicationComponent();
        setDefaultTheme();
        if (!BuildConfig.BUILD_TYPE.equals("release")) setupYandex();
//        throw new RuntimeException("Test Exception");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void setDefaultTheme() {
        if (applicationComponent.SharedPreferenceModel().getIntNullData(THEME.getValue()) == THEME_DEFAULT) {
            switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                case Configuration.UI_MODE_NIGHT_YES:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                case Configuration.UI_MODE_NIGHT_UNDEFINED:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
            }
        } else if (applicationComponent.SharedPreferenceModel().getIntNullData(THEME.getValue()) == THEME_LIGHT) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (applicationComponent.SharedPreferenceModel().getIntNullData(THEME.getValue()) == THEME_DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    private void setApplicationComponent() {
        applicationComponent = DaggerApplicationComponent
                .builder().networkModule(new NetworkModule())
                .customProgressModule(new CustomProgressModule())
                .myDatabaseModule(new MyDatabaseModule(appContext))
                .sharedPreferenceModule(new SharedPreferenceModule(appContext, ACCOUNT)).build();
        applicationComponent.inject(getInstance());
    }

    public static MyApplication getInstance() {

        if (singleton == null) {
            singleton = new MyApplication();
        }
        return singleton;
    }

    protected void setupYandex() {
        final String YANDEX_API_KEY = "0f22d2c8-1621-4448-b0db-267ab4131561";
        final YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(YANDEX_API_KEY)
                .withLogs().withAppVersion(BuildConfig.VERSION_NAME).build();
        YandexMetrica.activate(appContext, config);
        YandexMetrica.enableActivityAutoTracking(this);
    }
}