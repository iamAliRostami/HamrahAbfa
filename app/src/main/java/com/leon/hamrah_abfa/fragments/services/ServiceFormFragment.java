package com.leon.hamrah_abfa.fragments.services;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.SERVICE_TYPE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentServiceFormBinding;

public class ServiceFormFragment extends Fragment {
    private FragmentServiceFormBinding binding;
    private ServicesFormViewModel viewModel;

    public ServiceFormFragment() {
    }

    public static ServiceFormFragment newInstance(String billId) {
        final ServiceFormFragment fragment = new ServiceFormFragment();
        final Bundle args = new Bundle();
        args.putString(BILL_ID.getValue(), billId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel = new ServicesFormViewModel(requireContext(),getArguments().getInt(SERVICE_TYPE.getValue()),
                    getArguments().getString(BILL_ID.getValue()));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceFormBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
    }
}
