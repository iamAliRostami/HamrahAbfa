package com.leon.hamrah_abfa.helpers;

import static com.leon.hamrah_abfa.enums.SharedReferenceNames.ACCOUNT;
import static com.leon.hamrah_abfa.helpers.Constants.FONT_NAME;
import static com.leon.hamrah_abfa.helpers.Constants.TOAST_TEXT_SIZE;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import androidx.multidex.MultiDex;

import com.leon.hamrah_abfa.BuildConfig;
import com.leon.hamrah_abfa.di.component.ApplicationComponent;
import com.leon.hamrah_abfa.di.component.DaggerApplicationComponent;
import com.leon.hamrah_abfa.di.module.CustomProgressModule;
import com.leon.hamrah_abfa.di.module.MyDatabaseModule;
import com.leon.hamrah_abfa.di.module.NetworkModule;
import com.leon.hamrah_abfa.di.module.SharedPreferenceModule;
import com.leon.toast.RTLToast;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class MyApplication extends Application {
    private static Context appContext;
    private static ApplicationComponent applicationComponent;

    public static Context getAppContext() {
        return appContext;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        RTLToast.Config.getInstance().setToastTypeface(Typeface.createFromAsset(getAssets(),
                FONT_NAME)).setTextSize(TOAST_TEXT_SIZE).apply();
        applicationComponent = DaggerApplicationComponent
                .builder().networkModule(new NetworkModule())
                .customProgressModule(new CustomProgressModule())
                .myDatabaseModule(new MyDatabaseModule(appContext))
                .sharedPreferenceModule(new SharedPreferenceModule(appContext, ACCOUNT)).build();
        applicationComponent.inject(this);
        if (!BuildConfig.BUILD_TYPE.equals("release")) setupYandex();
//        throw new RuntimeException("Test Exception");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    protected void setupYandex() {
        final String YANDEX_API_KEY = "0f22d2c8-1621-4448-b0db-267ab4131561";
        final YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(YANDEX_API_KEY)
                .withLogs().withAppVersion(BuildConfig.VERSION_NAME).build();
        YandexMetrica.activate(appContext, config);
        YandexMetrica.enableActivityAutoTracking(this);
    }
}