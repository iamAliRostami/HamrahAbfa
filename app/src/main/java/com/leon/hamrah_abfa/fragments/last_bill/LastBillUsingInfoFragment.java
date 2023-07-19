package com.leon.hamrah_abfa.fragments.last_bill;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentLastBillUsingInfoBinding;


public class LastBillUsingInfoFragment extends Fragment {

    private ICallback callback;

    public LastBillUsingInfoFragment() {
    }

    public static LastBillUsingInfoFragment newInstance() {
        return new LastBillUsingInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLastBillUsingInfoBinding binding = FragmentLastBillUsingInfoBinding.inflate(inflater, container, false);
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