package com.leon.hamrah_abfa.fragments.mobile;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.OLD_MOBILE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.di.view_model.VerificationViewModel;

public class PreLoginViewModel extends VerificationViewModel {
    private boolean result;
    private String token;
    private String failureMessage;

    @SuppressLint("DefaultLocale")
    public PreLoginViewModel() {
        setDeviceInfo(String.format("%s %s", Build.BRAND, Build.MODEL));

        setCustomSerial(String.format("! %s @ %s # %s $ %s ^ %s & %d * %s _ %s +", Build.BRAND, Build.HARDWARE,
                Build.MANUFACTURER, Build.USER, Build.DEVICE, Build.VERSION.SDK_INT, Build.VERSION.RELEASE, Build.MODEL));
        if (getInstance().getApplicationComponent().SharedPreferenceModel().checkIsNotEmpty(OLD_MOBILE.getValue()))
            setMobile(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(OLD_MOBILE.getValue()));
    }

    @Bindable
    public Spanned getTip() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml("<p>با وارد نمودن شماره همراه، یک <b>کد تایید</b> برای شما ارسال خواهد شد</p>", Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml("<p>با وارد نمودن شماره همراه، یک <b>کد تایید</b> برای شما ارسال خواهد شد</p>");
        }
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
