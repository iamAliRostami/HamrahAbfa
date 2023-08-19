package com.leon.hamrah_abfa.fragments.counter;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.MOBILE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;
import com.leon.hamrah_abfa.di.view_model.VerificationViewModel;

public class CounterViewModel extends VerificationViewModel {
    private String billId;
    private String counterClaim;
    private String billAccountId;

    @SuppressLint("DefaultLocale")
    public CounterViewModel(String billId, String billAccountId) {
        setMobile(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(MOBILE.getValue()));
        setCustomSerial(String.format("! %s @ %s # %s $ %s ^ %s & %d * %s _", Build.BRAND, Build.HARDWARE,
                Build.MANUFACTURER, Build.USER, Build.DEVICE, Build.VERSION.SDK_INT, Build.VERSION.RELEASE));
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
    public String getCounterClaim() {
        return counterClaim;
    }

    public void setCounterClaim(String counterClaim) {
        this.counterClaim = counterClaim;
        notifyPropertyChanged(BR.counterClaim);
    }

    public String getBillAccountId() {
        return billAccountId;
    }

    public void setBillAccountId(String billAccountId) {
        this.billAccountId = billAccountId;
    }
}
