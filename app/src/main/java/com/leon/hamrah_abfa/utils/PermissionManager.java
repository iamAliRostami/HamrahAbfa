package com.leon.hamrah_abfa.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class PermissionManager {
    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return (connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting());
    }
}
