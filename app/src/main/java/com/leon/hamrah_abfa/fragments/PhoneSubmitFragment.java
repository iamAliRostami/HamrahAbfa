package com.leon.hamrah_abfa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentPhoneSubmitBinding;

public class PhoneSubmitFragment extends Fragment {
    private FragmentPhoneSubmitBinding binding;

    public PhoneSubmitFragment() {
    }

    public static PhoneSubmitFragment newInstance() {
        return new PhoneSubmitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhoneSubmitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}