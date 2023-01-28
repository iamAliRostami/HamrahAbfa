package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.helpers.Constants.SPLASH_TIME_OUT;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    Animation topAnimation, bottomAnimation, middleAnimation;
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialize();
    }

    private void initialize() {
        initializeAnimation();
        setAnimations();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIME_OUT);
    }

    private void initializeAnimation() {
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);
    }

    private void setAnimations() {
        binding.firstLine.setAnimation(topAnimation);
        binding.secondLine.setAnimation(topAnimation);
        binding.thirdLine.setAnimation(topAnimation);
        binding.fourthLine.setAnimation(topAnimation);
        binding.fifthLine.setAnimation(topAnimation);
        binding.sixthLine.setAnimation(topAnimation);
        binding.a.setAnimation(middleAnimation);
        binding.tagLine.setAnimation(bottomAnimation);
    }
}