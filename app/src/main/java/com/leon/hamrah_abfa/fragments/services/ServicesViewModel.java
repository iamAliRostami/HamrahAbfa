package com.leon.hamrah_abfa.fragments.services;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.MOBILE;
import static com.leon.hamrah_abfa.helpers.Constants.POINT;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;
import com.leon.hamrah_abfa.R;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class ServicesViewModel extends BaseObservable {
    private transient String id;
    private String address;
    private String firstName;
    private String sureName;
    private String nationId;
    private String billId;
    private String neighbourBillId;
    private String postalCode;
    private transient ArrayList<String> selectedServices;
    private transient ArrayList<Integer> selectedServicesId;
    private String mobile;
    private transient String title;
    private transient int serviceType;
    private transient TypedArray iconDrawable;
    private transient int srcIcon;
    private transient GeoPoint point;
    private transient Bitmap bitmapLocation;
    private String x;
    private String y;

    private String message;
    private String generationDateTime;
    private String verificationCode;
    private boolean isValid;

    public ServicesViewModel(Context context, int serviceType, String billId, String id) {
        setId(id);
        setBillId(billId);
        setNeighbourBillId(billId);
        setServiceType(serviceType);
        setMobile(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(MOBILE.getValue()));
        setTitle(context.getResources().getStringArray(R.array.services_main_menu)[getServiceType()]);
        setIconDrawable(context.getResources().obtainTypedArray(R.array.services_main_icons));
        setSrcIcon(iconDrawable.getResourceId(getServiceType(), -1));
        setPoint(POINT);
    }

    @Bindable
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
        notifyPropertyChanged(BR.billId);
    }

    @Bindable
    public String getNeighbourBillId() {
        return neighbourBillId;
    }

    public void setNeighbourBillId(String neighbourBillId) {
        this.neighbourBillId = neighbourBillId;
        notifyPropertyChanged(BR.neighbourBillId);
    }

    @Bindable
    public ArrayList<String> getSelectedServices() {
        return selectedServices;
    }

    public void setSelectedServices(ArrayList<String> selectedServices) {
        this.selectedServices = selectedServices;
        notifyPropertyChanged(BR.selectedServices);
    }

    @Bindable
    public ArrayList<Integer> getSelectedServicesId() {
        return selectedServicesId;
    }

    public void setSelectedServicesId(ArrayList<Integer> selectedServicesId) {
        this.selectedServicesId = selectedServicesId;
        notifyPropertyChanged(BR.selectedServicesId);
    }

    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }

    @Bindable
    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
        notifyPropertyChanged(BR.serviceType);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public TypedArray getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(TypedArray iconDrawable) {
        this.iconDrawable = iconDrawable;
        notifyPropertyChanged(BR.iconDrawable);
    }

    @Bindable
    public Drawable getDrawable() {
        return iconDrawable.getDrawable(getServiceType());
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public int getSrcIcon() {
        return srcIcon;
    }

    public void setSrcIcon(int srcIcon) {
        this.srcIcon = srcIcon;
        notifyPropertyChanged(BR.srcIcon);
    }

    @Bindable
    public GeoPoint getPoint() {
        return point;
    }

    public void setPoint(GeoPoint point) {
        this.point = point;
        notifyPropertyChanged(BR.point);
    }

    @Bindable
    public Bitmap getBitmapLocation() {
        return bitmapLocation;
    }

    public void setBitmapLocation(Bitmap bitmapLocation) {
        this.bitmapLocation = bitmapLocation;
        notifyPropertyChanged(BR.bitmapLocation);
    }

    public void setId(String id) {
        this.id = id;
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
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
        notifyPropertyChanged(BR.sureName);
    }

    @Bindable
    public String getNationId() {
        return nationId;
    }

    public void setNationId(String nationId) {
        this.nationId = nationId;
        notifyPropertyChanged(BR.nationId);
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
}
