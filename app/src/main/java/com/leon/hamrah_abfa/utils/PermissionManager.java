package com.leon.hamrah_abfa.utils;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.leon.hamrah_abfa.helpers.Constants.CARRIER_PRIVILEGE_STATUS;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

public class PermissionManager {
    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return (connectivityManager.getActiveNetworkInfo() != null &&
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

    public static boolean checkCallPhonePermission(Activity activity) {
        final TelephonyManager tm = (TelephonyManager)
                new ContextWrapper(activity).getSystemService(TELEPHONY_SERVICE);
        final boolean hasCarrier = tm.hasCarrierPrivileges();
//        if (!hasCarrier) {
//            final int hasPermission = ActivityCompat.checkSelfPermission(activity,
//                    "android.permission.READ_PRIVILEGED_PHONE_STATE");
//            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
//                if (!activity.shouldShowRequestPermissionRationale("android.permission.READ_PRIVILEGED_PHONE_STATE")) {
//                    ActivityCompat.requestPermissions(activity, new String[]{
//                            "android.permission.READ_PRIVILEGED_PHONE_STATE"}, CARRIER_PRIVILEGE_STATUS);
//                }
//            }
//        }
        return hasCarrier;
    }
}
