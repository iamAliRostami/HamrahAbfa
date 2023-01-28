package com.leon.hamrah_abfa.fragments.bottom_sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.databinding.FragmentVoiceRecorderBinding;

public class VoiceRecorderFragment extends BottomSheetDialogFragment {
    private FragmentVoiceRecorderBinding binding;

    public VoiceRecorderFragment() {
    }

    public static VoiceRecorderFragment newInstance() {
        return new VoiceRecorderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVoiceRecorderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}