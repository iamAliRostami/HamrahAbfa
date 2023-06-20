package com.leon.hamrah_abfa.activities;

import android.view.View;

import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityUsageHistoryBinding;

public class UsageHistoryActivity extends BaseActivity {
    private ActivityUsageHistoryBinding binding;

    @Override
    protected void initialize() {
        binding = ActivityUsageHistoryBinding.inflate(getLayoutInflater());
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