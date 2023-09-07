package com.app.leon.moshtarak.fragments.last_bill;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.databinding.FragmentLastBillEnsheabInfoBinding;

public class LastBillEnsheabInfoFragment extends Fragment {
    private ICallback callback;

    public LastBillEnsheabInfoFragment() {
    }

    public static LastBillEnsheabInfoFragment newInstance() {
        return new LastBillEnsheabInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLastBillEnsheabInfoBinding binding = FragmentLastBillEnsheabInfoBinding.inflate(inflater, container, false);
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