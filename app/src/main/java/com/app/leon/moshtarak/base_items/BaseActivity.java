package com.app.leon.moshtarak.base_items;

import static com.app.leon.moshtarak.enums.SharedReferenceKeys.THEME;
import static com.app.leon.moshtarak.helpers.Constants.HUGE;
import static com.app.leon.moshtarak.helpers.Constants.LARGE;
import static com.app.leon.moshtarak.helpers.Constants.MEDIUM;
import static com.app.leon.moshtarak.helpers.Constants.SMALL;
import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.leon.toast.RTLToast.info;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leon.moshtarak.R;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private long lastClickTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme();
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        initialize();
    }


    protected abstract void initialize();

    protected abstract String getExitMessage();

    private void setTheme() {
        switch (getInstance().getApplicationComponent().SharedPreferenceModel().getIntNullData(THEME.getValue())) {
            case SMALL:
                setTheme(R.style.Theme_HamrahAbfa_Small);
                break;
            case LARGE:
                setTheme(R.style.Theme_HamrahAbfa_Large);
                break;
            case HUGE:
                setTheme(R.style.Theme_HamrahAbfa_Huge);
                break;
            case MEDIUM:
                setTheme(R.style.Theme_HamrahAbfa);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getExitMessage() != null) {
            if (SystemClock.elapsedRealtime() - lastClickTime < 2000) super.onBackPressed();
            info(this, getExitMessage()).show();
            lastClickTime = SystemClock.elapsedRealtime();
        } else super.onBackPressed();
    }
}