package com.leon.hamrah_abfa.fragments.follow_request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentFollowRequestListBinding;

public class FollowRequestListFragment extends Fragment {
    private FragmentFollowRequestListBinding binding;

    public FollowRequestListFragment() {
    }

    public static FollowRequestListFragment newInstance() {
        return new FollowRequestListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFollowRequestListBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}