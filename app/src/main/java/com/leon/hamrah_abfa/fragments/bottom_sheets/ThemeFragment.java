package com.leon.hamrah_abfa.fragments.bottom_sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentThemeBinding;

public class ThemeFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private FragmentThemeBinding binding;

    public ThemeFragment() {
    }

    public static ThemeFragment newInstance() {
        return new ThemeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThemeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {

        }
    }
}