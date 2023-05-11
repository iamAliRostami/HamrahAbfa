package com.leon.hamrah_abfa.activities;

import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityFollowRequestBinding;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestListFragment;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestTrackFragment;

public class FollowRequestActivity extends BaseActivity {
    private ActivityFollowRequestBinding binding;


    @Override
    protected void initialize() {
        binding = ActivityFollowRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().add(binding.fragmentRequest.getId(),
                FollowRequestListFragment.newInstance()).commit();
    }

    @Override
    protected String getExitMessage() {
        return getString(R.string.return_by_press_again);
    }

    @Override
    public void onClick(View v) {

    }
}