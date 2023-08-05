package com.leon.hamrah_abfa.fragments.contact_us;

import android.graphics.Bitmap;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

import java.util.ArrayList;

import okhttp3.MultipartBody;

public class ForbiddenViewModel extends BaseObservable {
    private String description;
    private String postalCode;
    private String preEshterak;
    private String nextEshterak;
    private String x;
    private String y;
    private String tedadVahed;

    public ArrayList<MultipartBody.Part> file = new ArrayList<>();
    private String message;
    private String generationDateTime;
    private int status;
    private boolean isValid;
    public void resetViewModel() {
        setDescription("");
        setPostalCode("");
        setPreEshterak("");
        setNextEshterak("");
        setTedadVahed("");
        file = new ArrayList<>();
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        notifyPropertyChanged(BR.postalCode);
    }

    @Bindable
    public String getPreEshterak() {
        return preEshterak;
    }

    public void setPreEshterak(String preEshterak) {
        this.preEshterak = preEshterak;
        notifyPropertyChanged(BR.preEshterak);
    }

    @Bindable
    public String getNextEshterak() {
        return nextEshterak;
    }

    public void setNextEshterak(String nextEshterak) {
        this.nextEshterak = nextEshterak;
        notifyPropertyChanged(BR.nextEshterak);
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    @Bindable
    public String getTedadVahed() {
        return tedadVahed;
    }

    public void setTedadVahed(String tedadVahed) {
        this.tedadVahed = tedadVahed;
        notifyPropertyChanged(BR.tedadVahed);
    }

    public String getMessage() {
        return message;
    }
}