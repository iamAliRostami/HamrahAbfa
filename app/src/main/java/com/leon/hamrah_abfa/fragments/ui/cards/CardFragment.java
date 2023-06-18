package com.leon.hamrah_abfa.fragments.ui.cards;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.DEBT;
import static com.leon.hamrah_abfa.enums.BundleEnum.NICKNAME;
import static com.leon.hamrah_abfa.enums.BundleEnum.OWNER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentCardBinding;


public class CardFragment extends Fragment {
    private FragmentCardBinding binding;
    private CardViewModel viewModel;

    public CardFragment() {
    }

    public static CardFragment newInstance(String billId, String nickname, String owner) {
        final CardFragment fragment = new CardFragment();
        final Bundle args = new Bundle();
        args.putString(BILL_ID.getValue(), billId);
        args.putString(NICKNAME.getValue(), nickname);
        args.putString(OWNER.getValue(), owner);
        //TODO
        args.putInt(DEBT.getValue(), 987654);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel = new CardViewModel(getArguments().getString(OWNER.getValue()),
                    getArguments().getString(NICKNAME.getValue()),
                    getArguments().getString(BILL_ID.getValue()),
                    getArguments().getInt(DEBT.getValue()));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCardBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
    }
}