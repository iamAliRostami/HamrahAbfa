package com.leon.hamrah_abfa.fragments.usage_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentUsageHistorySuccessfulBinding;

public class UsageHistorySuccessfulFragment extends Fragment {
    private FragmentUsageHistorySuccessfulBinding binding;

    public UsageHistorySuccessfulFragment() {
    }

    public static UsageHistorySuccessfulFragment newInstance() {
        return new UsageHistorySuccessfulFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsageHistorySuccessfulBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}