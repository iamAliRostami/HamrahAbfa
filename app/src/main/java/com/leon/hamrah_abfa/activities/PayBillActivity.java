package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.helpers.Constants.PAY_BILL_BASE_FRAGMENT;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityPayBillBinding;
import com.leon.hamrah_abfa.fragments.pay_bill.PayBillBaseFragment;

public class PayBillActivity extends BaseActivity implements PayBillBaseFragment.ICallback {
    private ActivityPayBillBinding binding;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityPayBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        displayView(PAY_BILL_BASE_FRAGMENT);
    }

    @Override
    public void displayView(int position) {
        if (position == PAY_BILL_BASE_FRAGMENT) {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentPayBill.getId(),
                    PayBillBaseFragment.newInstance()).commit();
        }
    }

    @Override
    protected String getExitMessage() {
        return getString(R.string.return_by_press_again);
    }

    @Override
    public void onClick(View v) {
    }
}