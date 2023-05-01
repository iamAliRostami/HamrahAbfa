package com.leon.hamrah_abfa.base_items;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.THEME;
import static com.leon.hamrah_abfa.helpers.Constants.HUGE;
import static com.leon.hamrah_abfa.helpers.Constants.LARGE;
import static com.leon.hamrah_abfa.helpers.Constants.MEDIUM;
import static com.leon.hamrah_abfa.helpers.Constants.SMALL;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;
import static com.leon.toast.RTLToast.info;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.leon.hamrah_abfa.R;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private long lastClickTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        initialize();
    }


    protected abstract void initialize();

    protected abstract String getExitMessage();

    private void setTheme() {
        switch (getApplicationComponent().SharedPreferenceModel().getIntNullData(THEME.getValue())) {
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
        }else super.onBackPressed();
    }
}
