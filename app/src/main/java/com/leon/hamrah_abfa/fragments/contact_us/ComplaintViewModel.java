package com.leon.hamrah_abfa.fragments.contact_us;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class ComplaintViewModel extends BaseObservable {
    private String description;
    private String complaintType;

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
        notifyPropertyChanged(BR.complaintType);
    }
}