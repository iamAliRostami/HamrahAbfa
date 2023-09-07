package com.app.leon.moshtarak.activities;

import static com.app.leon.moshtarak.enums.FragmentTags.REQUEST_DONE;
import static com.app.leon.moshtarak.helpers.Constants.INCIDENT_BASE_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.INCIDENT_COMPLETE_FRAGMENT;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.adapters.base_adapter.ImageViewAdapter;
import com.app.leon.moshtarak.base_items.BaseActivity;
import com.app.leon.moshtarak.databinding.ActivityIncidentBinding;
import com.app.leon.moshtarak.fragments.dialog.TrackDoneRequestFragment;
import com.app.leon.moshtarak.fragments.incident.IncidentBaseFragment;
import com.app.leon.moshtarak.fragments.incident.IncidentCompleteFragment;
import com.app.leon.moshtarak.fragments.incident.IncidentViewModel;

public class IncidentActivity extends BaseActivity implements IncidentBaseFragment.ICallback,
        IncidentCompleteFragment.ICallback {
    private final IncidentViewModel viewModel = new IncidentViewModel();
    private ActivityIncidentBinding binding;
    private ImageViewAdapter adapter;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        showFragmentDialogOnce(this, REQUEST_DONE.getValue(), TrackDoneRequestFragment.newInstance("123456",
                getString(R.string.main_page), new TrackDoneRequestFragment.IClickListener() {
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