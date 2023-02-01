package com.leon.hamrah_abfa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentIncidentsReportingBinding;

public class IncidentsReportingFragment extends Fragment {
    private FragmentIncidentsReportingBinding binding;

    public IncidentsReportingFragment() {
    }

    public static IncidentsReportingFragment newInstance() {
        IncidentsReportingFragment fragment = new IncidentsReportingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIncidentsReportingBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}