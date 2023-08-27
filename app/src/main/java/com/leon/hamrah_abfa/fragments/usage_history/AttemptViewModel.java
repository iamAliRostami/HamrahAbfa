package com.leon.hamrah_abfa.fragments.usage_history;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class AttemptViewModel extends BaseObservable {

    private String counterNumber;
    private String jalaliDay;
    private long amount;
    private String status;

    @Bindable
    public String getCounterNumber() {
        return counterNumber;
    }

    public void setCounterNumber(String counterNumber) {
        this.counterNumber = counterNumber;
        notifyPropertyChanged(BR.counterNumber);
    }

    @Bindable
    public String getJalaliDay() {
        return jalaliDay;
    }

    public void setJalaliDay(String jalaliDay) {
        this.jalaliDay = jalaliDay;
        notifyPropertyChanged(BR.jalaliDay);
    }

    public long getAmount() {
        return amount;
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }
}
