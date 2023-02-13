package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.IS_FIRST;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.ActivityMainBinding;
import com.leon.toast.RTLToast;


public class MainActivity extends AppCompatActivity implements Animator.AnimatorListener {

    private long lastClickTime = 0;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize() {
        initializeSplash();
        initializeBottomSheet();
    }

    private void initializeBottomSheet() {
        final AppBarConfiguration appBar = new AppBarConfiguration.Builder(R.id.navigation_home,
                R.id.navigation_dashboard, R.id.navigation_notifications).build();
        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupActionBarWithNavController(this, navController, appBar);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }
    }

    private void initializeSplash() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding.container.setVisibility(View.GONE);
        binding.lottieAnimationView.setVisibility(View.VISIBLE);
        binding.lottieAnimationView.playAnimation();
        binding.lottieAnimationView.addAnimatorListener(this);
        binding.lottieAnimationView.addAnimatorUpdateListener(animation -> {
        });
    }


    @Override
    public void onAnimationStart(@NonNull Animator animation) {

    }

    @Override
    public void onAnimationEnd(@NonNull Animator animation) {
        binding.lottieAnimationView.setVisibility(View.GONE);
        if (true || getApplicationComponent().SharedPreferenceModel().getBoolData(IS_FIRST.getValue(), true)) {
            final Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(intent);
        }
        binding.container.setVisibility(View.VISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onAnimationCancel(@NonNull Animator animation) {

    }

    @Override
    public void onAnimationRepeat(@NonNull Animator animation) {

    }

    @Override
    public void onBackPressed() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 2000) super.onBackPressed();
        RTLToast.info(this, getString(R.string.exit_by_press_again)).show();
        lastClickTime = SystemClock.elapsedRealtime();
    }
}