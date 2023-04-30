package com.leon.hamrah_abfa.activities;

import static com.leon.toast.RTLToast.info;

import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.ActivityFollowRequestBinding;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestTrackFragment;

public class FollowRequestActivity extends AppCompatActivity {
    private ActivityFollowRequestBinding binding;

    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFollowRequestBinding.inflate(getLayoutInflater());
        initialize();
        setContentView(binding.getRoot());
    }

    private void initialize() {
        getSupportFragmentManager().beginTransaction().add(binding.fragmentRequest.getId(),
                FollowRequestTrackFragment.newInstance()).commit();
    }

    @Override
    public void onBackPressed() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 2000) super.onBackPressed();
        info(this, getString(R.string.return_by_press_again)).show();
        lastClickTime = SystemClock.elapsedRealtime();
    }
}