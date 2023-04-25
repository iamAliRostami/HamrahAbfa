package com.leon.hamrah_abfa.activities;

import static com.leon.toast.RTLToast.info;

import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.ActivityIncidentBinding;
import com.leon.hamrah_abfa.fragments.incident.IncidentCompleteFragment;

public class IncidentActivity extends AppCompatActivity {
    private ActivityIncidentBinding binding;
    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncidentBinding.inflate(getLayoutInflater());
        initialize();
        setContentView(binding.getRoot());
    }

    private void initialize() {
        getSupportFragmentManager().beginTransaction().add(binding.fragmentIncident.getId(),
                IncidentCompleteFragment.newInstance()/*IncidentBaseFragment.newInstance()*/).commit();
    }

    @Override
    public void onBackPressed() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 2000) super.onBackPressed();
        info(this, getString(R.string.return_by_press_again)).show();
        lastClickTime = SystemClock.elapsedRealtime();
    }
}