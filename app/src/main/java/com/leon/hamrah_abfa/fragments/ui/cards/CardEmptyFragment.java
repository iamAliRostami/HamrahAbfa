package com.leon.hamrah_abfa.fragments.ui.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentCardEmptyBinding;

public class CardEmptyFragment extends Fragment {
    private FragmentCardEmptyBinding binding;

    public CardEmptyFragment() {
        // Required empty public constructor
    }

    public static CardEmptyFragment newInstance() {
        return new CardEmptyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCardEmptyBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
    }
}