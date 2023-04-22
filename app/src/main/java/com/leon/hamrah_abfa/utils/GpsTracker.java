package com.leon.hamrah_abfa.utils;

import static com.leon.hamrah_abfa.enums.FragmentTags.ASK_YES_NO;
import static com.leon.hamrah_abfa.helpers.Constants.MIN_DISTANCE_CHANGE_FOR_UPDATES;
import static com.leon.hamrah_abfa.helpers.Constants.MIN_TIME_BW_UPDATES;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.fragments.dialog.YesNoFragment;

public class GpsTracker extends Service implements LocationListener {
    private final Context context;
    private boolean canGetLocation = false;
    private Location location;
    private double latitude;
    private double longitude;
    protected LocationManager locationManager;

    public GpsTracker(Context context) {
        this.context = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            final boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            final boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPSEnabled || isNetworkEnabled) {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                        }
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GpsTracker.this);
        }
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//        alertDialog.setTitle("GPS is settings");
//        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
//        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                context.startActivity(intent);
//            }
//        });
//        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        alertDialog.show();
        ShowFragmentDialogOnce(context, ASK_YES_NO.getValue(),
                YesNoFragment.newInstance(R.drawable.raw_map, "GPS is settings",
                        "GPS is not enabled. Do you want to go to settings menu?", "Setting"
                        , "Cancel", new YesNoFragment.IClickListener() {
                            @Override
                            public void yes(DialogFragment fragment) {
                                final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                context.startActivity(intent);
                                fragment.dismiss();
                            }

                            @Override
                            public void no(DialogFragment fragment) {
                                fragment.dismiss();
                            }
                        })
        );
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}