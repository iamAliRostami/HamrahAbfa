package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.FragmentTags.REQUEST_DONE;
import static com.leon.hamrah_abfa.helpers.Constants.INCIDENT_BASE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.INCIDENT_COMPLETE_FRAGMENT;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;

import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.ImageViewAdapter;
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
        displayView(INCIDENT_BASE_FRAGMENT);
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
        ShowFragmentDialogOnce(this, REQUEST_DONE.getValue(), RequestDoneFragment.newInstance("123456",
                getString(R.string.main_page), new RequestDoneFragment.IClickListener() {
                    @Override
                    public void yes(DialogFragment dialogFragment) {
                        finish();
                    }

                    @Override
                    public void no(DialogFragment dialogFragment) {

                    }
                }));
    }

    @Override
    public void displayView(int position) {
        if (position == INCIDENT_BASE_FRAGMENT) {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentIncident.getId(),
                    IncidentBaseFragment.newInstance()).commit();
        } else if (position == INCIDENT_COMPLETE_FRAGMENT) {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentIncident.getId(),
                    IncidentCompleteFragment.newInstance()).commit();
        }
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