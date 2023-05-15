package com.leon.hamrah_abfa.fragments.change_mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentChangeMobileVerificationCodeBinding;

public class ChangeMobileVerificationCodeFragment extends Fragment {
    private FragmentChangeMobileVerificationCodeBinding binding;

    public ChangeMobileVerificationCodeFragment() {
    }

    public static ChangeMobileVerificationCodeFragment newInstance() {
        return new ChangeMobileVerificationCodeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeMobileVerificationCodeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}