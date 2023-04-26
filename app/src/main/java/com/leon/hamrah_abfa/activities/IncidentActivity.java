package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.FragmentTags.REQUEST_DONE;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;
import static com.leon.toast.RTLToast.info;

import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.ImageViewAdapter;
import com.leon.hamrah_abfa.databinding.ActivityIncidentBinding;
import com.leon.hamrah_abfa.fragments.dialog.RequestDoneFragment;
import com.leon.hamrah_abfa.fragments.incident.IncidentBaseFragment;
import com.leon.hamrah_abfa.fragments.incident.IncidentCompleteFragment;
import com.leon.hamrah_abfa.fragments.incident.IncidentViewModel;

public class IncidentActivity extends AppCompatActivity implements IncidentBaseFragment.ICallback, IncidentCompleteFragment.ICallback {
    private ActivityIncidentBinding binding;
    private long lastClickTime;
    private ImageViewAdapter adapter;
    private final IncidentViewModel viewModel = new IncidentViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncidentBinding.inflate(getLayoutInflater());
        initialize();
        setContentView(binding.getRoot());
    }

    private void initialize() {
        getSupportFragmentManager().beginTransaction().add(binding.fragmentIncident.getId(),
                IncidentBaseFragment.newInstance()).commit();
    }

    @Override
    public void onBackPressed() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 2000) super.onBackPressed();
        info(this, getString(R.string.return_by_press_again)).show();
        lastClickTime = SystemClock.elapsedRealtime();
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