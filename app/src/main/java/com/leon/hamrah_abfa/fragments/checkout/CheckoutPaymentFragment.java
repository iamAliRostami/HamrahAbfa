package com.leon.hamrah_abfa.fragments.checkout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentCheckoutPaymentBinding;
import com.leon.hamrah_abfa.fragments.notifications.NotificationFragment;

public class CheckoutPaymentFragment extends Fragment {
    private FragmentCheckoutPaymentBinding binding;
    private ICallback callback;

    public CheckoutPaymentFragment() {
    }

    public static CheckoutPaymentFragment newInstance() {
        return new CheckoutPaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutPaymentBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public interface ICallback {
        String getBillId();
    }
}