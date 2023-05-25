package com.leon.hamrah_abfa.fragments.contact_us;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentContactSuggestionBinding;

public class ContactSuggestionFragment extends Fragment {
    private FragmentContactSuggestionBinding binding;

    public ContactSuggestionFragment() {
    }

    public static ContactSuggestionFragment newInstance() {
        return new ContactSuggestionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactSuggestionBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}