package com.leon.hamrah_abfa.di.module;

import android.app.Activity;

import com.leon.hamrah_abfa.di.view_model.LocationTrackingGoogle;
import com.leon.hamrah_abfa.di.view_model.LocationTrackingGps;
import com.leon.hamrah_abfa.infrastructure.ILocationTracking;
import com.leon.hamrah_abfa.utils.CheckSensor;

public class LocationTrackingModule {
    private ILocationTracking location;

    public LocationTrackingModule(Activity activity) {
        if (CheckSensor.checkSensor(activity))
            location = LocationTrackingGoogle.getInstance(activity);
        else
            location = LocationTrackingGps.getInstance();
    }

    public ILocationTracking providesLocationTrackingGps() {
        return location;
    }
}
