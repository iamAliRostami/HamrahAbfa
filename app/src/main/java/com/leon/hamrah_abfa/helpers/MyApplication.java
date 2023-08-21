package com.leon.hamrah_abfa.helpers;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.THEME_MODE;
import static com.leon.hamrah_abfa.enums.SharedReferenceNames.ACCOUNT;
import static com.leon.hamrah_abfa.helpers.Constants.FONT_NAME;
import static com.leon.hamrah_abfa.helpers.Constants.HOST_CHECK_CONNECTION;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_DARK;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_DEFAULT;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_LIGHT;
import static com.leon.hamrah_abfa.helpers.Constants.TOAST_TEXT_SIZE;
import static com.leon.toast.RTLToast.Config;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.StrictMode;

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

import java.net.InetAddress;

public class MyApplication extends Application {
    private static ApplicationComponent applicationComponent;
    private static boolean serverPing;
    private static MyApplication instance = null;

    public static boolean checkServerConnection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        int timeout = 1000;
        InetAddress[] addresses;
        try {
            addresses = InetAddress.getAllByName(HOST_CHECK_CONNECTION);
            for (InetAddress address : addresses) {
                if (address.isReachable(timeout)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    public static boolean hasServerPing() {
        return serverPing;
    }

    public static void setServerPing(boolean serverPing) {
        MyApplication.serverPing = serverPing;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setApplicationComponent();
        setDefaultNightMode();
        Config.getInstance().setToastTypeface(Typeface.createFromAsset(getAssets(), FONT_NAME))
                .setTextSize(TOAST_TEXT_SIZE).apply();
        setServerPing(checkServerConnection());
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

    protected void setupYandex() {
        final String YANDEX_API_KEY = "0f22d2c8-1621-4448-b0db-267ab4131561";
        final YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(YANDEX_API_KEY)
                .withLogs().withAppVersion(BuildConfig.VERSION_NAME).build();
        YandexMetrica.activate(this, config);
        YandexMetrica.enableActivityAutoTracking(getInstance());
    }
}