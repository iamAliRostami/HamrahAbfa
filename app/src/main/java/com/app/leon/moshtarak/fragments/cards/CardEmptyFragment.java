package com.app.leon.moshtarak.fragments.cards;

import static com.app.leon.moshtarak.enums.FragmentTags.SUBMIT_INFO;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.databinding.FragmentCardEmptyBinding;
import com.app.leon.moshtarak.fragments.bottom_sheets.SubmitInfoFragment;

public class CardEmptyFragment extends Fragment implements View.OnClickListener {
    private FragmentCardEmptyBinding binding;

    public CardEmptyFragment() {
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
        binding.linearLayoutParent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.linear_layout_parent) {
            showFragmentDialogOnce(requireContext(), SUBMIT_INFO.getValue(), SubmitInfoFragment.newInstance());
        }
    }
}