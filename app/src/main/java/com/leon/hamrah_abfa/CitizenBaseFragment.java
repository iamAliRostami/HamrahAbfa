package com.leon.hamrah_abfa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentCitizenBaseBinding;


public class CitizenBaseFragment extends Fragment {
    private FragmentCitizenBaseBinding binding;
    public CitizenBaseFragment() {
    }

    public static CitizenBaseFragment newInstance() {
        return new CitizenBaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCitizenBaseBinding.inflate(inflater,container,false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}