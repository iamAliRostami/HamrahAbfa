package com.app.leon.moshtarak.fragments.citizen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.databinding.FragmentNotFoundBinding;

public class NotFoundFragment extends Fragment {
    public NotFoundFragment() {
    }

    public static NotFoundFragment newInstance() {
        return new NotFoundFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNotFoundBinding binding = FragmentNotFoundBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}