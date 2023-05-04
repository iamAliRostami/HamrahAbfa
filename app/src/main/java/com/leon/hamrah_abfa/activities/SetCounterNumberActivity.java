package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.helpers.Constants.COUNTER_BASE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.COUNTER_VERIFICATION_CODE_FRAGMENT;

import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivitySetCounterNumberBinding;
import com.leon.hamrah_abfa.fragments.counter.CounterBaseFragment;
import com.leon.hamrah_abfa.fragments.counter.CounterVerificationCodeFragment;
import com.leon.hamrah_abfa.fragments.counter.CounterViewModel;

public class SetCounterNumberActivity extends BaseActivity implements CounterBaseFragment.ICallback,
        CounterVerificationCodeFragment.ICallback {
    private CounterViewModel viewModel;
    private ActivitySetCounterNumberBinding binding;

    @Override
    protected void initialize() {
        binding = ActivitySetCounterNumberBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            viewModel = new CounterViewModel(getIntent().getExtras().getString(BILL_ID.getValue()));
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        displayView(COUNTER_BASE_FRAGMENT);
    }

    @Override
    public void displayView(int position) {
        if (position == COUNTER_BASE_FRAGMENT) {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentSetCounter.getId(),
                    CounterBaseFragment.newInstance()).commit();
        } else if (position == COUNTER_VERIFICATION_CODE_FRAGMENT) {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentSetCounter.getId(),
                    CounterVerificationCodeFragment.newInstance()).commit();
        }
    }

    @Override
    protected String getExitMessage() {
        return getString(R.string.return_by_press_again);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public CounterViewModel getViewModel() {
        return viewModel;
    }

}