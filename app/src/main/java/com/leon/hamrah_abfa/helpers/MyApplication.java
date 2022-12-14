package com.leon.hamrah_abfa.helpers;

import android.app.Application;
import android.content.Context;

import com.leon.hamrah_abfa.BuildConfig;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class MyApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        if (!BuildConfig.BUILD_TYPE.equals("release"))
            setupYandex();
//        throw new RuntimeException("Test Exception");
    }

    protected void setupYandex() {
        final String YANDEX_API_KEY = "0f22d2c8-1621-4448-b0db-267ab4131561";
        final YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(YANDEX_API_KEY)
                .withLogs().withAppVersion(BuildConfig.VERSION_NAME).build();
        YandexMetrica.activate(appContext, config);
        YandexMetrica.enableActivityAutoTracking(this);
    }

    public static Context getAppContext() {
        return appContext;
    }
}
