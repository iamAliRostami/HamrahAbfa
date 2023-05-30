package com.leon.hamrah_abfa.fragments.contact_us;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class ForbiddenViewModel extends BaseObservable {
    private String description;
    private String postalCode;
    private String preBillId;
    private String nextBillId;

    public void resetViewModel() {
        setDescription("");
        setPostalCode("");
        setPreBillId("");
        setNextBillId("");
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
    public String getPreBillId() {
        return preBillId;
    }

    public void setPreBillId(String preBillId) {
        this.preBillId = preBillId;
        notifyPropertyChanged(BR.preBillId);
    }

    @Bindable
    public String getNextBillId() {
        return nextBillId;
    }

    public void setNextBillId(String nextBillId) {
        this.nextBillId = nextBillId;
        notifyPropertyChanged(BR.nextBillId);
    }
}