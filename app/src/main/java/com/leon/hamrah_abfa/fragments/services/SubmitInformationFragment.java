package com.leon.hamrah_abfa.fragments.services;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentSubmitInformationBinding;

public class SubmitInformationFragment extends Fragment {
    private FragmentSubmitInformationBinding binding;
    private ICallback serviceActivity;

    public SubmitInformationFragment() {
    }

    public static SubmitInformationFragment newInstance() {
        return new SubmitInformationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSubmitInformationBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) serviceActivity = (ICallback) context;
    }

    public interface ICallback {
        ServicesViewModel getServicesViewModel();
    }
}