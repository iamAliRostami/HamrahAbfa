package com.leon.hamrah_abfa.fragments.mobile;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;
import com.leon.hamrah_abfa.di.view_model.VerificationViewModel;

public class PreLoginViewModel extends VerificationViewModel {
    private boolean result;
    private String token;
    private String failureMessage;

    @SuppressLint("DefaultLocale")
    public PreLoginViewModel() {
        setCustomSerial(String.format("! %s @ %s # %s $ %s ^ %s & %d * %s _", Build.BRAND, Build.HARDWARE,
                Build.MANUFACTURER, Build.USER, Build.DEVICE, Build.VERSION.SDK_INT, Build.VERSION.RELEASE));
    }

    @Bindable
    public Spanned getTip() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml("<p>با وارد نمودن شماره همراه، یک <b>کد تایید</b> برای شما ارسال خواهد شد</p>", Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml("<p>با وارد نمودن شماره همراه، یک <b>کد تایید</b> برای شما ارسال خواهد شد</p>");
        }
    }

    public String getMessage() {
        return message;
    }

    public boolean isResult() {
        return result;
    }

    public String getToken() {
        return token;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
}
