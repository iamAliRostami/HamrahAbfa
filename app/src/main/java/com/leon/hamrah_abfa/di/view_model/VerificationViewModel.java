package com.leon.hamrah_abfa.di.view_model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class VerificationViewModel extends BaseObservable {
    String verificationId;
    long remainedSeconds;
    String mobile;
    String customSerial;
    String verificationCode;
    int status;
    public String message;
    String generationDateTime;
    private String submitCode;
    boolean isValid;


    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }

    @Bindable
    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        notifyPropertyChanged(BR.verificationCode);
    }

    public String getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    public long getRemainedSeconds() {
        return remainedSeconds;
    }

    public void setRemainedSeconds(long remainedSeconds) {
        this.remainedSeconds = remainedSeconds;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGenerationDateTime() {
        return generationDateTime;
    }

    public void setGenerationDateTime(String generationDateTime) {
        this.generationDateTime = generationDateTime;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getCustomSerial() {
        return customSerial;
    }

    public void setCustomSerial(String customSerial) {
        this.customSerial = customSerial;
    }

    public String getSubmitCode() {
        return submitCode;
    }

    public void setSubmitCode(String submitCode) {
        this.submitCode = submitCode;
    }
}
