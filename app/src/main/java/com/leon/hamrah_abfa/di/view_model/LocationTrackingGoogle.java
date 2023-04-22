package com.leon.hamrah_abfa.di.view_model;

import static com.leon.hamrah_abfa.helpers.Constants.FASTEST_INTERVAL;
import static com.leon.hamrah_abfa.helpers.Constants.MIN_TIME_BW_UPDATES;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.leon.hamrah_abfa.infrastructure.ILocationTracking;

public class LocationTrackingGoogle extends Service implements ILocationTracking {
    private static LocationTrackingGoogle instance;
    private static Location location;
    private static LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;

    public LocationTrackingGoogle(Activity activity) {
        startFusedLocation(activity);
    }

    public static synchronized LocationTrackingGoogle getInstance(Activity activity) {
        if (instance == null) {
            instance = new LocationTrackingGoogle(activity);
        }
        return instance;
    }

    public static LocationTrackingGoogle getInstance() {
        return instance;
    }

    public static void setInstance(LocationTrackingGoogle instance) {
        LocationTrackingGoogle.instance = instance;
    }

    void startFusedLocation(Activity activity) {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(MIN_TIME_BW_UPDATES);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        registerRequestUpdateGoogle(activity);
    }

    void stopFusedLocation() {
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @SuppressLint("MissingPermission")
    void registerRequestUpdateGoogle(Activity activity) {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public Location getCurrentLocation(/*Context context*/) {
        return location;
    }

    @Override
    public double getAccuracy() {
        return location.getAccuracy();
    }

    @Override
    public double getLongitude() {
        return location.getLongitude();
    }

    @Override
    public double getLatitude() {
        return location.getLatitude();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public IBinder onBind(Intent intent) {
        stopFusedLocation();
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopFusedLocation();
    }
}