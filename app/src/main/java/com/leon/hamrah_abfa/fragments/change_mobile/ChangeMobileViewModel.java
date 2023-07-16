package com.leon.hamrah_abfa.fragments.change_mobile;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.MOBILE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;
import com.leon.hamrah_abfa.di.view_model.VerificationViewModel;

public class ChangeMobileViewModel extends VerificationViewModel {
    private String newMobile;
    private String billId;
    private String billAccountId;

    public ChangeMobileViewModel(String billId, String billAccountId) {
        setMobile(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(MOBILE.getValue()));
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
