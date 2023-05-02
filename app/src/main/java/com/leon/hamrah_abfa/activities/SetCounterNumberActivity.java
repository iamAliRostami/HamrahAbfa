package com.leon.hamrah_abfa.activities;

import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivitySetCounterNumberBinding;
import com.leon.hamrah_abfa.databinding.FragmentCounterBaseBinding;
import com.leon.hamrah_abfa.fragments.counter.CounterBaseFragment;
import com.leon.hamrah_abfa.fragments.incident.IncidentBaseFragment;

public class SetCounterNumberActivity extends BaseActivity {
    private ActivitySetCounterNumberBinding binding;

    @Override
    protected void initialize() {
        binding = ActivitySetCounterNumberBinding.inflate(getLayoutInflater());
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
}