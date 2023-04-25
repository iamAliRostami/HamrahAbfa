package com.leon.hamrah_abfa.fragments.ui.incident;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.activities.IncidentActivity;
import com.leon.hamrah_abfa.databinding.FragmentIncidentBinding;

public class IncidentFragment extends Fragment implements View.OnClickListener {
    private FragmentIncidentBinding binding;

    public IncidentFragment() {
    }

    public static IncidentFragment newInstance() {
        return new IncidentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIncidentBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_start) {
            final Intent intent = new Intent(requireContext(), IncidentActivity.class);
            startActivity(intent);
        }
    }
}