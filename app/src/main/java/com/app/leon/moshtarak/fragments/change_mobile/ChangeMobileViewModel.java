package com.app.leon.moshtarak.fragments.change_mobile;

import androidx.databinding.Bindable;

import com.app.leon.moshtarak.di.view_model.VerificationViewModel;
import com.app.leon.moshtarak.enums.SharedReferenceKeys;
import com.app.leon.moshtarak.helpers.MyApplication;
import com.app.leon.moshtarak.BR;

public class ChangeMobileViewModel extends VerificationViewModel {
    private String newMobile;
    private String billId;
    private String billAccountId;

    public ChangeMobileViewModel(String billId, String billAccountId) {
        setMobile(MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.MOBILE.getValue()));
        setBillId(billId);
        setBillAccountId(billAccountId);
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
    public String getNewMobile() {
        return newMobile;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
        notifyPropertyChanged(BR.newMobile);
    }

    public String getBillAccountId() {
        return billAccountId;
    }

    public void setBillAccountId(String billAccountId) {
        this.billAccountId = billAccountId;
    }
}
