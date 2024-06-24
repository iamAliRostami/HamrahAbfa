package com.app.leon.moshtarak.helpers;

import static com.app.leon.moshtarak.enums.SharedReferenceKeys.THEME_MODE;
import static com.app.leon.moshtarak.enums.SharedReferenceNames.ACCOUNT;
import static com.app.leon.moshtarak.utils.PermissionManager.isNetworkAvailable;
import static com.leon.toast.RTLToast.Config;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.app.leon.moshtarak.BuildConfig;
import com.app.leon.moshtarak.di.component.ApplicationComponent;
import com.app.leon.moshtarak.di.component.DaggerApplicationComponent;
import com.app.leon.moshtarak.di.module.MyDatabaseModule;
import com.app.leon.moshtarak.di.module.NetworkModule;
import com.app.leon.moshtarak.di.module.SharedPreferenceModule;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class MyApplication extends Application {
    private static ApplicationComponent applicationComponent;
//    private static boolean serverPing = true;
    private static MyApplication instance = null;

    public static boolean checkServerConnection(Context context) {
/*        return true;
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
//        return false;
        try {
            URL url = new URL(HOST_P_CHECK_CONNECTION);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.connect();
            return httpURLConnection.getResponseCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

 */
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = connectivityManager.getActiveNetwork();
        NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(activeNetwork);
        if (caps != null)
            return caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
        return false;
    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

//    public static boolean hasServerPing() {
//        return serverPing;
//    }
//
//    public static void setServerPing(boolean serverPing) {
//        MyApplication.serverPing = serverPing;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        setApplicationComponent();
        setDefaultNightMode();
        Config.getInstance().setToastTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_NAME))
                .setTextSize(Constants.TOAST_TEXT_SIZE).apply();
//        if (isNetworkAvailable(this))
//            setServerPing(checkServerConnection(this));
        if (!BuildConfig.BUILD_TYPE.equals("release")) setupYandex();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getInstance());
    }

    private void setDefaultNightMode() {
        switch (applicationComponent.SharedPreferenceModel().getIntNullData(THEME_MODE.getValue())) {
            case Constants.THEME_DEFAULT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case Constants.THEME_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case Constants.THEME_DARK:
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