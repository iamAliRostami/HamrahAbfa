package com.app.leon.moshtarak.fragments.cut_off;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.leon.moshtarak.enums.SharedReferenceKeys;
import com.app.leon.moshtarak.helpers.MyApplication;
import com.app.leon.moshtarak.BR;

public class CutOffViewModel extends BaseObservable {
    private String billId;
    private String mobile;
    private String description;

    public CutOffViewModel(String billId) {
        this.billId = billId;
        setMobile(MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.MOBILE.getValue()));

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
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }
}
