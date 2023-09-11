package com.app.leon.moshtarak.activities;

import static com.app.leon.moshtarak.enums.BundleEnum.BILL_ID;
import static com.app.leon.moshtarak.enums.BundleEnum.UUID;
import static com.app.leon.moshtarak.helpers.Constants.CHANGE_MOBILE_BASE_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CHANGE_MOBILE_VERIFICATION_CODE_FRAGMENT;
import static com.app.leon.moshtarak.utils.ShowFragment.setFragment;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;

import com.app.leon.moshtarak.base_items.BaseActivity;
import com.app.leon.moshtarak.databinding.ActivityChangeMobileBinding;
import com.app.leon.moshtarak.fragments.change_mobile.ChangeMobileBaseFragment;
import com.app.leon.moshtarak.fragments.change_mobile.ChangeMobileVerificationCodeFragment;
import com.app.leon.moshtarak.fragments.change_mobile.ChangeMobileViewModel;

public class ChangeMobileActivity extends BaseActivity implements ChangeMobileBaseFragment.ICallback,
        ChangeMobileVerificationCodeFragment.ICallback {
    private ActivityChangeMobileBinding binding;
    private ChangeMobileViewModel viewModel;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityChangeMobileBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            viewModel = new ChangeMobileViewModel(getIntent().getExtras().getString(BILL_ID.getValue()),
                    getIntent().getExtras().getString(UUID.getValue()));
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        displayView(CHANGE_MOBILE_BASE_FRAGMENT);
    }

    @Override
    public void displayView(int position) {
        if (position == CHANGE_MOBILE_BASE_FRAGMENT) {
            setFragment(this, binding.fragmentChangeNumber.getId(), ChangeMobileBaseFragment.newInstance());
        } else if (position == CHANGE_MOBILE_VERIFICATION_CODE_FRAGMENT) {
            setFragment(this, binding.fragmentChangeNumber.getId(), ChangeMobileVerificationCodeFragment.newInstance());
        }
    }

    @Override
    public ChangeMobileViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void editViewModel(String id, long remainedSeconds) {
        viewModel.setVerificationId(id);
        viewModel.setRemainedSeconds(remainedSeconds);
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

    @Override
    public void onClick(View v) {
    }
}