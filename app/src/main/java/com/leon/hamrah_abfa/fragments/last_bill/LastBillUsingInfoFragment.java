package com.leon.hamrah_abfa.fragments.last_bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentLastBillUsingInfoBinding;

public class LastBillUsingInfoFragment extends Fragment {
    private FragmentLastBillUsingInfoBinding binding;

    public LastBillUsingInfoFragment() {
    }

    public static LastBillUsingInfoFragment newInstance(String param1, String param2) {
        return new LastBillUsingInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLastBillUsingInfoBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}