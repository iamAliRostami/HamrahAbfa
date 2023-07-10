package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.helpers.Constants.SUBMIT_PHONE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.VERIFICATION_FRAGMENT;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityMobileSubmitBinding;
import com.leon.hamrah_abfa.fragments.mobile.MobileSubmitFragment;
import com.leon.hamrah_abfa.fragments.mobile.MobileVerificationFragment;
import com.leon.hamrah_abfa.fragments.mobile.PreLoginViewModel;

public class MobileSubmitActivity extends BaseActivity implements MobileSubmitFragment.ICallback,
        MobileVerificationFragment.ICallback {
    private final PreLoginViewModel viewModel = new PreLoginViewModel();
    private ActivityMobileSubmitBinding binding;

    @Override
    protected void initialize() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMobileSubmitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        displayView(SUBMIT_PHONE_FRAGMENT);
    }

    @Override
    public void displayView(int position) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.setCustomAnimations(R.animator.enter, R.animator.exit,
//                R.animator.pop_enter, R.animator.pop_exit);
        ft.replace(binding.containerBody.getId(), getFragment(position), Integer.toString(position));
        if (position != SUBMIT_PHONE_FRAGMENT) {
            ft.addToBackStack(null);
        }
        ft.commitAllowingStateLoss();
        runOnUiThread(() -> getFragmentManager().executePendingTransactions());
    }

    @Override
    public PreLoginViewModel getViewModel() {
        return viewModel;
    }

    private Fragment getFragment(int position) {
        switch (position) {
            case VERIFICATION_FRAGMENT:
                return MobileVerificationFragment.newInstance();
            case SUBMIT_PHONE_FRAGMENT:
            default:
                return MobileSubmitFragment.newInstance();
        }
    }

    @Override
    public void editPreLoginViewModel(String id, long remainedSeconds) {
        viewModel.setId(id);
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