package com.leon.hamrah_abfa.fragments.ui.cards;

import static com.leon.hamrah_abfa.enums.FragmentTags.SUBMIT_INFO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentCardEmptyBinding;
import com.leon.hamrah_abfa.fragments.bottom_sheets.SubmitInfoFragment;

public class CardEmptyFragment extends Fragment implements View.OnClickListener {
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
        binding.imageViewAdd.setOnClickListener(this);
        binding.textViewEnterAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.image_view_add) {
            SubmitInfoFragment.newInstance().show(getParentFragmentManager(), SUBMIT_INFO.getValue());
        }
    }
}