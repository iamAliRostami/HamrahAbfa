package com.leon.hamrah_abfa.infrastructure;

import android.location.Location;

public interface ILocationTracking {

    Location getLocation();

    double getLatitude();

    double getLongitude();

    double getAccuracy();

    Location getCurrentLocation(/*Context context*/);
}
