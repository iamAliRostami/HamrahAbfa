package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.IS_FIRST;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.ViewPagerAdapter;
import com.leon.hamrah_abfa.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize() {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        binding.viewPagerWelcome.setAdapter(adapter);
        binding.viewPagerWelcome.setRotationY(180);
        binding.viewPagerWelcome.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == adapter.getItemCount() - 1) {
                    binding.buttonDone.setVisibility(View.VISIBLE);
                    binding.buttonSkip.setVisibility(View.GONE);
                } else {
                    binding.buttonDone.setVisibility(View.GONE);
                    binding.buttonSkip.setVisibility(View.VISIBLE);
                }
                binding.layoutSkip.setBackgroundColor(adapter.getBgColors()[position]);

            }
        });
        binding.indicator.attachTo(binding.viewPagerWelcome);
        binding.buttonSkip.setOnClickListener(this);
        binding.buttonDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_skip) {
            binding.viewPagerWelcome.setCurrentItem(binding.viewPagerWelcome.getCurrentItem() + 1);
        } else if (id == R.id.button_done) {
            getApplicationComponent().SharedPreferenceModel().putData(IS_FIRST.getValue(), false);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getApplicationComponent().SharedPreferenceModel().putData(IS_FIRST.getValue(), false);
    }
}