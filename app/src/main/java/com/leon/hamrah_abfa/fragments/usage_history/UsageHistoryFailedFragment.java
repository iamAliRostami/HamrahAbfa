package com.leon.hamrah_abfa.fragments.usage_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentUsageHistoryFailedBinding;

public class UsageHistoryFailedFragment extends Fragment {
    private FragmentUsageHistoryFailedBinding binding;

    public UsageHistoryFailedFragment() {
    }

    public static UsageHistoryFailedFragment newInstance() {
        return new UsageHistoryFailedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsageHistoryFailedBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}