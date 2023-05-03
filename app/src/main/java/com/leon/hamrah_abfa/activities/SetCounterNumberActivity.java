package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;

import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivitySetCounterNumberBinding;
import com.leon.hamrah_abfa.fragments.counter.CounterBaseFragment;
import com.leon.hamrah_abfa.fragments.counter.CounterViewModel;

public class SetCounterNumberActivity extends BaseActivity implements CounterBaseFragment.ICallback {
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
        getSupportFragmentManager().beginTransaction().add(binding.fragmentSetCounter.getId(),
                CounterBaseFragment.newInstance()).commit();
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

    @Override
    public void goToMessagePage() {
        //TODO
    }
}