package com.leon.hamrah_abfa.fragments.follow_request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentFollowRequestTrackBinding;

public class FollowRequestTrackFragment extends Fragment {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFollowRequestTrackBinding.inflate(inflater,container,false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}