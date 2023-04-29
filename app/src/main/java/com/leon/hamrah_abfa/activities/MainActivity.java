package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.FragmentTags.SUBMIT_INFO;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.IS_FIRST;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;
import static com.leon.toast.RTLToast.info;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.CardPagerAdapter;
import com.leon.hamrah_abfa.databinding.ActivityMainBinding;
import com.leon.hamrah_abfa.fragments.bottom_sheets.SubmitInfoFragment;
import com.leon.hamrah_abfa.fragments.ui.home.HomeFragment;
import com.leon.hamrah_abfa.fragments.ui.services.ServiceFragment;


public class MainActivity extends AppCompatActivity implements Animator.AnimatorListener,
        View.OnClickListener, HomeFragment.ICallback, SubmitInfoFragment.ICallback,
        ServiceFragment.ICallback {
    private long lastClickTime = 0;
    private ActivityMainBinding binding;
    private CardPagerAdapter cardPagerAdapter;

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
//TODO
//         initializeSplash();
// TODO
        initializeBottomSheet();
        binding.floatButtonAdd.setOnClickListener(this);
        final ImageView imageViewSetting = findViewById(R.id.image_view_setting);
        imageViewSetting.setOnClickListener(this);
        final ImageView imageViewNotification = findViewById(R.id.image_view_notification);
        imageViewNotification.setOnClickListener(this);
    }

    private void initializeBottomSheet() {
        final AppBarConfiguration appBar = new AppBarConfiguration.Builder(R.id.navigation_home,
                R.id.navigation_incident, R.id.navigation_services, R.id.navigation_dashboard).build();
        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupActionBarWithNavController(this, navController, appBar);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }

        final MaterialShapeDrawable bottomBarBackground = (MaterialShapeDrawable) binding.bottomAppBar.getBackground();
        bottomBarBackground.setShapeAppearanceModel(
                bottomBarBackground.getShapeAppearanceModel()
                        .toBuilder()
                        .setTopLeftCorner(new RoundedCornerTreatment()).setTopLeftCornerSize(new RelativeCornerSize(0.5f))
                        .setTopRightCorner(new RoundedCornerTreatment()).setTopRightCornerSize(new RelativeCornerSize(0.5f))
                        .build());
    }

    private void initializeSplash() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding.container.setVisibility(View.GONE);
        binding.lottieAnimationView.setVisibility(View.VISIBLE);
        binding.lottieAnimationView.playAnimation();
        binding.lottieAnimationView.addAnimatorListener(this);
    }

    @Override
    public void onAnimationStart(@NonNull Animator animation) {

    }

    @Override
    public void onAnimationEnd(@NonNull Animator animation) {
        binding.lottieAnimationView.setVisibility(View.GONE);
        if (/*  TODO true ||*/getApplicationComponent().SharedPreferenceModel().getBoolData(IS_FIRST.getValue(), true)) {
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
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.float_button_add) {
            ShowFragmentDialogOnce(this, SUBMIT_INFO.getValue(), SubmitInfoFragment.newInstance());
        } else if (id == R.id.image_view_setting) {
            final Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.image_view_notification) {
        }
    }

    @Override
    public void onBackPressed() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 2000) super.onBackPressed();
        info(this, getString(R.string.exit_by_press_again)).show();
        lastClickTime = SystemClock.elapsedRealtime();
    }

    @Override
    public CardPagerAdapter getCardPagerAdapter() {
        return cardPagerAdapter;
    }

    @Override
    public void updateCard() {
        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        if (navHostFragment != null) {
            final NavController navController = navHostFragment.getNavController();
            if (navController.getCurrentDestination() != null) {
                final int i = navController.getCurrentDestination().getId();
                navController.popBackStack(i, true);
                navController.navigate(i);
            }
        }
    }

    @Override
    public void createCardPagerAdapter() {
        cardPagerAdapter = new CardPagerAdapter(this);
    }

    @Override
    public String getCurrentBillId(int position) {
        return cardPagerAdapter.getCurrentBillId(position);
    }
}