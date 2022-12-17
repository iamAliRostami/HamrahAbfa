package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.IS_FIRST;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.ActivityMainBinding;
import com.leon.hamrah_abfa.utils.toast.CustomToast;


public class MainActivity extends AppCompatActivity implements MotionLayout.TransitionListener {

    private long lastClickTime = 0;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize() {
        binding.motionLayout.setTransitionListener(this);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_bottom_navigation);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }
    }

    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
        binding.container.setVisibility(View.GONE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

    }

    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        if (getApplicationComponent().SharedPreferenceModel().getBoolData(IS_FIRST.getValue(), true)) {
            final Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
        binding.container.setVisibility(View.VISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }

    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

    }

    @Override
    public void onBackPressed() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 2000) super.onBackPressed();
        CustomToast.info(this, getString(R.string.exit_by_press_again)).show();
        lastClickTime = SystemClock.elapsedRealtime();
    }
}