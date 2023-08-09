package com.leon.hamrah_abfa.fragments.citizen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentCitizenCompleteBinding;

public class CitizenCompleteFragment extends Fragment {
    private FragmentCitizenCompleteBinding binding;

    public CitizenCompleteFragment() {
    }

    public static CitizenCompleteFragment newInstance() {
        return new CitizenCompleteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCitizenCompleteBinding.inflate(inflater,container,false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}