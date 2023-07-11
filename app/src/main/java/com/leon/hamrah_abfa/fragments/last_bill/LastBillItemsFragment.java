package com.leon.hamrah_abfa.fragments.last_bill;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentLastBillItemsBinding;

public class LastBillItemsFragment extends Fragment {
    private ICallback callback;

    public LastBillItemsFragment() {
    }

    public static LastBillItemsFragment newInstance() {
        return new LastBillItemsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLastBillItemsBinding binding = FragmentLastBillItemsBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getBillViewModel());
        return binding.getRoot();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }
    public interface ICallback {
        BillViewModel getBillViewModel();
    }
}