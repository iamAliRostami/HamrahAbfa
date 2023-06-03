package com.leon.hamrah_abfa.fragments.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentCheckoutBillBinding;

public class CheckoutBillFragment extends Fragment {
    private FragmentCheckoutBillBinding binding;

    public CheckoutBillFragment() {
    }

    public static CheckoutBillFragment newInstance() {
        return new CheckoutBillFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutBillBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }

    public interface ICallback {
        String getBillId();
    }
}