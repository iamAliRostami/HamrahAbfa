package com.leon.hamrah_abfa.fragments.citizen;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

import java.util.ArrayList;

import okhttp3.MultipartBody;

public class ForbiddenViewModel extends BaseObservable {
    public ArrayList<MultipartBody.Part> file = new ArrayList<>();
    private String description;
    private String postalCode;
    private String preEshterak;
    private String nextEshterak;
    private String x;
    private String y;
    private String tedadVahed;
    private transient String parentType;
    private String type;
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
        setType("");
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

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
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

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }
}