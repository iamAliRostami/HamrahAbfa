package com.leon.hamrah_abfa.activities;

import android.view.View;

import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityCheckoutBinding;
import com.leon.hamrah_abfa.fragments.checkout.CheckoutBillFragment;
import com.leon.hamrah_abfa.fragments.checkout.CheckoutPaymentFragment;

public class CheckoutActivity extends BaseActivity implements CheckoutBillFragment.ICallback,
        CheckoutPaymentFragment.ICallback {
    private ActivityCheckoutBinding binding;

    @Override
    protected void initialize() {
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}