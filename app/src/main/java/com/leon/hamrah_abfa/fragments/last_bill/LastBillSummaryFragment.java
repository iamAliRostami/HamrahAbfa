package com.leon.hamrah_abfa.fragments.last_bill;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leon.hamrah_abfa.databinding.FragmentLastBillSummaryBinding;

public class LastBillSummaryFragment extends Fragment {
    private FragmentLastBillSummaryBinding binding;
    public LastBillSummaryFragment() {
    }

    public static LastBillSummaryFragment newInstance() {
        return new LastBillSummaryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  FragmentLastBillSummaryBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}