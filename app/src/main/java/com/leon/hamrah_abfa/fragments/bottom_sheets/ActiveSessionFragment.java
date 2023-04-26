package com.leon.hamrah_abfa.fragments.bottom_sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentActiveSessionBinding;

public class ActiveSessionFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private FragmentActiveSessionBinding binding;

    public ActiveSessionFragment() {
    }

    public static ActiveSessionFragment newInstance() {
        return new ActiveSessionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentActiveSessionBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {

        } else if (id == R.id.image_view_arrow_down) {
            dismiss();
        }
    }
}