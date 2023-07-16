package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.helpers.Constants.CHANGE_MOBILE_BASE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CHANGE_MOBILE_VERIFICATION_CODE_FRAGMENT;
import static com.leon.hamrah_abfa.utils.ShowFragment.setFragment;

import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityChangeMobileBinding;
import com.leon.hamrah_abfa.fragments.change_mobile.ChangeMobileBaseFragment;
import com.leon.hamrah_abfa.fragments.change_mobile.ChangeMobileVerificationCodeFragment;
import com.leon.hamrah_abfa.fragments.change_mobile.ChangeMobileViewModel;

public class ChangeMobileActivity extends BaseActivity implements ChangeMobileBaseFragment.ICallback,
        ChangeMobileVerificationCodeFragment.ICallback {

    private ActivityChangeMobileBinding binding;
    private ChangeMobileViewModel viewModel;

    @Override
    protected void initialize() {
        binding = ActivityChangeMobileBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            viewModel = new ChangeMobileViewModel(getIntent().getExtras().getString(BILL_ID.getValue()));
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
        viewModel.setId(id);
        viewModel.setRemainedSeconds(remainedSeconds);
    }

    @Override
    protected String getExitMessage() {
        return getString(R.string.return_by_press_again);
    }

    @Override
    public void onClick(View v) {

    }
}