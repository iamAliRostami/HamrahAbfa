package com.app.leon.moshtarak.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;

import androidx.core.app.ActivityCompat;

public class PermissionManager {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return (connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting());
    }

    public static boolean checkRecorderPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkCameraPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkCallPhonePermission(Context context) {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }
}
