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

public class PreLoginViewModel extends BaseObservable {
    private String mobile;
    private String customSerial;
    private int counterVisibility;
    private int tryAgainVisibility;
    private int arrowVisibility;
    private String id;
    private long remainedSeconds;
    private int status;
    private String message;
    private String generationDateTime;
    private boolean isValid;
    private String verificationCode;
    private String submitCode;
    private boolean result;
    private String token;
    private String failureMessage;
    private CountDownTimer countDownTimer;

    @SuppressLint("DefaultLocale")
    public PreLoginViewModel() {
        setCustomSerial(String.format("! %s @ %s # %s $ %s ^ %s & %d * %s _", Build.BRAND, Build.HARDWARE,
                Build.MANUFACTURER, Build.USER, Build.DEVICE, Build.VERSION.SDK_INT, Build.VERSION.RELEASE));
    }

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

    @Bindable
    public Spanned getTip() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml("<p>با وارد نمودن شماره همراه، یک <b>کد تایید</b> برای شما ارسال خواهد شد</p>", Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml("<p>با وارد نمودن شماره همراه، یک <b>کد تایید</b> برای شما ارسال خواهد شد</p>");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomSerial() {
        return customSerial;
    }

    public void setCustomSerial(String customSerial) {
        this.customSerial = customSerial;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isValid() {
        return isValid;
    }


    @Bindable
    public int getCounterVisibility() {
        return counterVisibility;
    }

    public void setCounterVisibility(int counterVisibility) {
        this.counterVisibility = counterVisibility;
        notifyPropertyChanged(BR.counterVisibility);
    }

    @Bindable
    public int getTryAgainVisibility() {
        return tryAgainVisibility;
    }

    public void setTryAgainVisibility(int tryAgainVisibility) {
        this.tryAgainVisibility = tryAgainVisibility;
        notifyPropertyChanged(BR.tryAgainVisibility);
    }

    @Bindable
    public int getArrowVisibility() {
        return arrowVisibility;
    }

    public void setArrowVisibility(int arrowVisibility) {
        this.arrowVisibility = arrowVisibility;
        notifyPropertyChanged(BR.arrowVisibility);
    }

    @Bindable
    public String getRemainedSecondsString() {
        return String.valueOf(remainedSeconds);
    }

    @Bindable
    public long getRemainedSeconds() {
        return remainedSeconds;
    }

    public void setRemainedSeconds(long remainedSeconds) {
        this.remainedSeconds = remainedSeconds;
        notifyPropertyChanged(BR.remainedSeconds);
        notifyPropertyChanged(BR.remainedSecondsString);
    }


    public void startCounter() {
        setCounterVisibility(View.VISIBLE);
        setTryAgainVisibility(View.GONE);
        setArrowVisibility(View.GONE);
        countDownTimer = new CountDownTimer(getRemainedSeconds() * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                setRemainedSeconds(millisUntilFinished / 1000);
            }

            public void onFinish() {
                setCounterVisibility(View.GONE);
                setTryAgainVisibility(View.VISIBLE);
                setArrowVisibility(View.VISIBLE);
            }

        };
        countDownTimer.start();
    }

    public void cancelCounter() {
        if (countDownTimer!=null) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
    }

    public String getSubmitCode() {
        return submitCode;
    }

    public void setSubmitCode(String submitCode) {
        this.submitCode = submitCode;
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
