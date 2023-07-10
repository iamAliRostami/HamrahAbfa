package com.leon.hamrah_abfa.fragments.cards;

import static com.leon.hamrah_abfa.enums.BundleEnum.ALIAS;
import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.DEBT;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentCardBinding;


public class CardFragment extends Fragment {
    private FragmentCardBinding binding;
    private BillCardViewModel viewModel;

    public CardFragment() {
    }

    public static CardFragment newInstance(BillCardViewModel bill) {
        final CardFragment fragment = new CardFragment();
        final Bundle args = new Bundle();
        args.putString(ID.getValue(), bill.getId());
        args.putString(BILL_ID.getValue(), bill.getBillId());
        args.putString(ALIAS.getValue(), bill.getAlias());
        args.putString(DEBT.getValue(), bill.getDebtString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel = new BillCardViewModel(getArguments().getString(ID.getValue()),
                    getArguments().getString(BILL_ID.getValue()),getArguments().getString(ALIAS.getValue()),
                    getArguments().getString(DEBT.getValue()));
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