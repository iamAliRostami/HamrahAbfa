package com.leon.hamrah_abfa.fragments.services;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.MOBILE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;
import com.leon.hamrah_abfa.R;

public class ServicesFormViewModel extends BaseObservable {
    private String billId;
    private String selectedServices;
    private String mobile;
    private String title;
    private int serviceType;
    private int iconDrawable;

    public ServicesFormViewModel(Context context, int serviceType, String billId) {
        setServiceType(serviceType);
        setBillId(billId);
        setMobile(getApplicationComponent().SharedPreferenceModel().getStringData(MOBILE.getValue()));
        setTitle(context.getResources().getStringArray(R.array.services_main_menu)[getServiceType()]);
        setIconDrawable(context.getResources().getIntArray(R.array.services_main_icons)[(getServiceType())]);
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
    public String getSelectedServices() {
        return selectedServices;
    }

    public void setSelectedServices(String selectedServices) {
        this.selectedServices = selectedServices;
        notifyPropertyChanged(BR.selectedServices);
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
    public int getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(int iconDrawable) {
        this.iconDrawable = iconDrawable;
        notifyPropertyChanged(BR.iconDrawable);
    }
}
