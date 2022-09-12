package com.leon.hamrah_abfa.helpers;

import static com.leon.hamrah_abfa.helpers.XmlHelper.setRString;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.leon.hamrah_abfa.BuildConfig;
import com.leon.hamrah_abfa.R;
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
        setCustomXml();
        Log.e("app name", getString(R.string.app_name));
        Log.e("app name 1", getString(R.string.app_name_1));
//        throw new RuntimeException("Test Exception");
    }

    protected void setupYandex() {
        final String YANDEX_API_KEY = "0f22d2c8-1621-4448-b0db-267ab4131561";
        final YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(YANDEX_API_KEY)
                .withLogs().withAppVersion(BuildConfig.VERSION_NAME).build();
        YandexMetrica.activate(appContext, config);
        YandexMetrica.enableActivityAutoTracking(this);
    }

    private void setCustomXml() {
        setRString(R.class, "app_name", R.string.app_name_1);
    }

    public static Context getAppContext() {
        return appContext;
    }
}
