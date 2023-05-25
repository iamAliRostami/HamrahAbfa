package com.leon.hamrah_abfa.fragments.contact_us;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentContactPhonebookBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactPhonebookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactPhonebookFragment extends Fragment {
    private FragmentContactPhonebookBinding binding;
    public ContactPhonebookFragment() {
    }

    public static ContactPhonebookFragment newInstance() {
        return new ContactPhonebookFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactPhonebookBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
}