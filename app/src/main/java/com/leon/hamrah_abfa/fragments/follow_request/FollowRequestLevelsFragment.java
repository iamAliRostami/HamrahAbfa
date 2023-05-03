package com.leon.hamrah_abfa.fragments.follow_request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentFollowRequestLevelsBinding;

public class FollowRequestLevelsFragment extends Fragment {
    private FragmentFollowRequestLevelsBinding binding;

    public FollowRequestLevelsFragment() {
    }

    public static FollowRequestLevelsFragment newInstance() {
        return new FollowRequestLevelsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFollowRequestLevelsBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}