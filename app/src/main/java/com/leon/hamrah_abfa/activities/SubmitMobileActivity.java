package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.helpers.Constants.SUBMIT_PHONE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.VERIFICATION_FRAGMENT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivitySubmitMobileBinding;
import com.leon.hamrah_abfa.fragments.mobile.PreLoginViewModel;
import com.leon.hamrah_abfa.fragments.mobile.SubmitMobileFragment;
import com.leon.hamrah_abfa.fragments.mobile.VerificationMobileFragment;

public class SubmitMobileActivity extends BaseActivity implements SubmitMobileFragment.ICallback,
        VerificationMobileFragment.ICallback {
    private final PreLoginViewModel viewModel = new PreLoginViewModel();
    private ActivitySubmitMobileBinding binding;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySubmitMobileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        displayView(SUBMIT_PHONE_FRAGMENT);
    }

    @Override
    public void displayView(int position) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(binding.containerBody.getId(), getFragment(position), Integer.toString(position));
        if (position != SUBMIT_PHONE_FRAGMENT) {
            ft.addToBackStack(null);
        }
        ft.commitAllowingStateLoss();
        runOnUiThread(() -> getSupportFragmentManager().executePendingTransactions());

    }

    @Override
    public PreLoginViewModel getViewModel() {
        return viewModel;
    }

    private Fragment getFragment(int position) {
        switch (position) {
            case VERIFICATION_FRAGMENT:
                return VerificationMobileFragment.newInstance();
            case SUBMIT_PHONE_FRAGMENT:
            default:
                return SubmitMobileFragment.newInstance();
        }
    }

    @Override
    public void editPreLoginViewModel(String id, long remainedSeconds) {
        viewModel.setVerificationId(id);
        viewModel.setRemainedSeconds(remainedSeconds);
    }

    @Override
    public void editPreLoginViewModel(String token, String failureMessage, boolean result) {
        viewModel.setToken(token);
        viewModel.setFailureMessage(failureMessage);
        viewModel.setResult(result);
    }

    @Override
    public void setResult() {
        final Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}