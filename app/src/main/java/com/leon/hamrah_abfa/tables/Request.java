package com.leon.hamrah_abfa.tables;

import android.content.Context;

import com.leon.hamrah_abfa.R;

public class Request {
    private int id;
    private int serviceType;
    private String title;
    private String trackNumber;
    private String date;

    public Request(Context context, int serviceType, String trackNumber, String date) {
        this.date = date;
        this.trackNumber = trackNumber;
        this.serviceType = serviceType;
        setTitle(context.getResources().getStringArray(R.array.services_main_menu)[serviceType]);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
