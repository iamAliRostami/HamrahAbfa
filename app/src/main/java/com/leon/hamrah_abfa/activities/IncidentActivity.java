package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.FragmentTags.REQUEST_DONE;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;

import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.ImageViewAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityIncidentBinding;
import com.leon.hamrah_abfa.fragments.dialog.RequestDoneFragment;
import com.leon.hamrah_abfa.fragments.incident.IncidentBaseFragment;
import com.leon.hamrah_abfa.fragments.incident.IncidentCompleteFragment;
import com.leon.hamrah_abfa.fragments.incident.IncidentViewModel;

public class IncidentActivity extends BaseActivity implements IncidentBaseFragment.ICallback,
        IncidentCompleteFragment.ICallback {
    private ActivityIncidentBinding binding;
    private ImageViewAdapter adapter;
    private final IncidentViewModel viewModel = new IncidentViewModel();

    @Override
    protected void initialize() {
        binding = ActivityIncidentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().add(binding.fragmentIncident.getId(),
                IncidentBaseFragment.newInstance()).commit();
    }

    @Override
    protected String getExitMessage() {
        return getString(R.string.return_by_press_again);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public IncidentViewModel getIncidentViewModel() {
        return viewModel;
    }

    @Override
    public void confirm() {
        ShowFragmentDialogOnce(this, REQUEST_DONE.getValue(),
                RequestDoneFragment.newInstance("123456"));
    }

    @Override
    public void nextPage() {
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentIncident.getId(),
                IncidentCompleteFragment.newInstance()).commit();
    }

    @Override
    public void returnToBase() {
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentIncident.getId(),
                IncidentBaseFragment.newInstance()).commit();
    }

    @Override
    public ImageViewAdapter getImageViewAdapter() {
        return adapter;
    }

    @Override
    public void setImageViewAdapter(ImageViewAdapter adapter) {
        this.adapter = adapter;
    }

}