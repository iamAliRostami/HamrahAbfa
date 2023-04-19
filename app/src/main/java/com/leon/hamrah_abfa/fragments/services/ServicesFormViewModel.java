package com.leon.hamrah_abfa.fragments.services;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.MOBILE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class ServicesFormViewModel extends BaseObservable {
    private String billId;
    private String selectedServices;
    private String mobile;

    public ServicesFormViewModel() {
        setMobile(getApplicationComponent().SharedPreferenceModel().getStringData(MOBILE.getValue()));
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
}
