package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.IS_FIRST;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.MOBILE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager2.widget.ViewPager2;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.WelcomePagerAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends BaseActivity {
    private ActivityWelcomeBinding binding;

    protected void initialize() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeViewPager();
        binding.buttonSkip.setOnClickListener(this);
        binding.buttonDone.setOnClickListener(this);
    }

    private void initializeViewPager() {
        final WelcomePagerAdapter adapter = new WelcomePagerAdapter(this);
        binding.viewPagerWelcome.setAdapter(adapter);
        binding.viewPagerWelcome.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                final ConstraintSet constraintSet = new ConstraintSet();
                if (position == adapter.getItemCount() - 1) {
                    binding.buttonDone.setVisibility(View.VISIBLE);
                    binding.buttonSkip.setVisibility(View.GONE);
                    constraintSet.clone(binding.layoutSkip);
                    constraintSet.connect(R.id.view, ConstraintSet.TOP, R.id.button_done, ConstraintSet.TOP, 0);
                } else {
                    binding.buttonSkip.setVisibility(View.VISIBLE);
                    binding.buttonDone.setVisibility(View.GONE);
                    constraintSet.clone(binding.layoutSkip);
                    constraintSet.connect(R.id.view, ConstraintSet.TOP, R.id.button_skip, ConstraintSet.TOP, 0);
                }
                constraintSet.applyTo(binding.layoutSkip);
                //TODO
                binding.layoutSkip.setBackgroundColor(adapter.getBgColors(position));

            }
        });
        binding.indicator.setupWithViewPager(binding.viewPagerWelcome);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_skip) {
            binding.viewPagerWelcome.setCurrentItem(binding.viewPagerWelcome.getCurrentItem() + 1);
        } else if (id == R.id.button_done) {
            getApplicationComponent().SharedPreferenceModel().putData(IS_FIRST.getValue(), false);
            //TODO permanent redirect
            if (!getApplicationComponent().SharedPreferenceModel().checkIsNotEmpty(MOBILE.getValue())) {
                final Intent intent = new Intent(getApplicationContext(), MobileSubmitActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getApplicationComponent().SharedPreferenceModel().putData(IS_FIRST.getValue(), true);
    }
}