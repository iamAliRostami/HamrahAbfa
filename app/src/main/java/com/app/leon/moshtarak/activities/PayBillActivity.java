package com.app.leon.moshtarak.activities;

import static com.app.leon.moshtarak.helpers.Constants.PAY_BILL_BASE_FRAGMENT;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;

import com.app.leon.moshtarak.base_items.BaseActivity;
import com.app.leon.moshtarak.databinding.ActivityPayBillBinding;
import com.app.leon.moshtarak.fragments.pay_bill.PayBillBaseFragment;

public class PayBillActivity extends BaseActivity {
    private ActivityPayBillBinding binding;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityPayBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        displayView(PAY_BILL_BASE_FRAGMENT);
    }

    public void displayView(int position) {
        if (position == PAY_BILL_BASE_FRAGMENT) {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentPayBill.getId(),
                    PayBillBaseFragment.newInstance()).commit();
        }
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

    @Override
    public void onClick(View v) {
    }
}