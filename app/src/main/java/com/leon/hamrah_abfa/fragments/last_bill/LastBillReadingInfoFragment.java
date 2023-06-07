package com.leon.hamrah_abfa.fragments.last_bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentLastBillReadingInfoBinding;

public class LastBillReadingInfoFragment extends Fragment {
    private FragmentLastBillReadingInfoBinding binding;

    public LastBillReadingInfoFragment() {
    }

    public static LastBillReadingInfoFragment newInstance() {
        return new LastBillReadingInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLastBillReadingInfoBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
    }
}