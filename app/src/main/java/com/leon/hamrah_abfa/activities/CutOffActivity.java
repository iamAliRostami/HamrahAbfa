package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.helpers.Constants.CUT_OFF_BASE_FRAGMENT;
import static com.leon.hamrah_abfa.utils.ShowFragment.setFragment;

import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityCutOffBinding;
import com.leon.hamrah_abfa.fragments.cut_off.CutOffBaseFragment;
import com.leon.hamrah_abfa.fragments.cut_off.CutOffViewModel;

public class CutOffActivity extends BaseActivity implements CutOffBaseFragment.ICallback {
    private ActivityCutOffBinding binding;
    private CutOffViewModel viewModel;

    @Override
    protected void initialize() {
        binding = ActivityCutOffBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            viewModel = new CutOffViewModel(getIntent().getExtras().getString(BILL_ID.getValue()));
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        displayView(CUT_OFF_BASE_FRAGMENT);
    }

    public void displayView(int position) {
        if (position == CUT_OFF_BASE_FRAGMENT) {
            setFragment(this, binding.fragmentCutOff.getId(), CutOffBaseFragment.newInstance());
        }
    }

    @Override
    protected String getExitMessage() {
        return getString(R.string.return_by_press_again);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public CutOffViewModel getViewModel() {
        return viewModel;
    }
}