package com.leon.hamrah_abfa.utils;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class CheckSensor {

    public static boolean checkSensor(Context context) {
        return checkGooglePlayServices(context);
    }

    public static boolean checkGooglePlayServices(Context context) {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }
}
