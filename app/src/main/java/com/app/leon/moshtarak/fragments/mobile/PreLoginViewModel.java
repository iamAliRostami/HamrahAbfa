package com.app.leon.moshtarak.fragments.mobile;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.databinding.Bindable;

import com.app.leon.moshtarak.di.view_model.VerificationViewModel;
import com.app.leon.moshtarak.enums.SharedReferenceKeys;
import com.app.leon.moshtarak.helpers.MyApplication;

public class PreLoginViewModel extends VerificationViewModel {
    private boolean result;
    private String token;
    private String failureMessage;

    @SuppressLint("DefaultLocale")
    public PreLoginViewModel() {
        setDeviceInfo(String.format("%s %s", Build.BRAND, Build.MODEL));

        setCustomSerial(String.format("! %s @ %s # %s $ %s ^ %s & %d * %s _ %s +", Build.BRAND, Build.HARDWARE,
                Build.MANUFACTURER, Build.USER, Build.DEVICE, Build.VERSION.SDK_INT, Build.VERSION.RELEASE, Build.MODEL));
        if (MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().checkIsNotEmpty(SharedReferenceKeys.OLD_MOBILE.getValue()))
            setMobile(MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.OLD_MOBILE.getValue()));
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

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }


}
