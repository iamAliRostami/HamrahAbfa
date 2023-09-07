package com.app.leon.moshtarak.activities;

import static com.app.leon.moshtarak.enums.SharedReferenceKeys.IS_FIRST;
import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.WindowManager;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager2.widget.ViewPager2;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.adapters.fragment_state_adapter.WelcomePagerAdapter;
import com.app.leon.moshtarak.base_items.BaseActivity;
import com.app.leon.moshtarak.databinding.ActivityWelcomeBinding;
import com.app.leon.moshtarak.enums.BundleEnum;

public class WelcomeActivity extends BaseActivity {
    private ActivityWelcomeBinding binding;
    private boolean isFirst = true;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            isFirst = getIntent().getExtras().getBoolean(BundleEnum.IS_FIRST.getValue());
        }
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
            //TODO permanent redirect
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
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
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(IS_FIRST.getValue(), isFirst);
    }
}