package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.helpers.Constants.CHANGE_MOBILE_BASE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CHANGE_MOBILE_VERIFICATION_CODE_FRAGMENT;

import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityChangeMobileBinding;
import com.leon.hamrah_abfa.fragments.change_mobile.ChangeMobileBaseFragment;
import com.leon.hamrah_abfa.fragments.change_mobile.ChangeMobileVerificationCodeFragment;
import com.leon.hamrah_abfa.fragments.change_mobile.ChangeMobileViewModel;

public class ChangeMobileActivity extends BaseActivity implements ChangeMobileBaseFragment.ICallback {

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
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentChangeNumber.getId(),
                    ChangeMobileBaseFragment.newInstance()).commit();
        } else if (position == CHANGE_MOBILE_VERIFICATION_CODE_FRAGMENT) {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentChangeNumber.getId(),
                    ChangeMobileVerificationCodeFragment.newInstance()).commit();
        }
    }

    @Override
    public ChangeMobileViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected String getExitMessage() {
        return getString(R.string.return_by_press_again);
    }

    @Override
    public void onClick(View v) {

    }
}