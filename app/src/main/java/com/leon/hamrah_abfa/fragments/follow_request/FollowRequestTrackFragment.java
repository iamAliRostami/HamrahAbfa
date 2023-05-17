package com.leon.hamrah_abfa.fragments.follow_request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leon.hamrah_abfa.base_items.BaseBottomSheetFragment;
import com.leon.hamrah_abfa.databinding.FragmentFollowRequestTrackBinding;

public class FollowRequestTrackFragment extends BaseBottomSheetFragment {
    private FragmentFollowRequestTrackBinding binding;

    public FollowRequestTrackFragment() {
    }

    public static FollowRequestTrackFragment newInstance() {
        return new FollowRequestTrackFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentFollowRequestTrackBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}