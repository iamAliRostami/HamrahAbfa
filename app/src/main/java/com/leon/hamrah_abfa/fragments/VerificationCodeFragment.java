package com.leon.hamrah_abfa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentVerificationCodeBinding;

public class VerificationCodeFragment extends Fragment {
    private FragmentVerificationCodeBinding binding;

    public VerificationCodeFragment() {
    }

    public static VerificationCodeFragment newInstance() {
        return new VerificationCodeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerificationCodeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}