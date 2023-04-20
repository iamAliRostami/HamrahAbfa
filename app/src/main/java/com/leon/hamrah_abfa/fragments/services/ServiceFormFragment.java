package com.leon.hamrah_abfa.fragments.services;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.SERVICE_TYPE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentServiceFormBinding;

public class ServiceFormFragment extends Fragment implements View.OnClickListener {
    private FragmentServiceFormBinding binding;
    private ServicesFormViewModel viewModel;
    private ICallback serviceActivity;

    public ServiceFormFragment() {
    }

    public static ServiceFormFragment newInstance(String billId, int serviceType) {
        final ServiceFormFragment fragment = new ServiceFormFragment();
        final Bundle args = new Bundle();
        args.putString(BILL_ID.getValue(), billId);
        args.putInt(SERVICE_TYPE.getValue(), serviceType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel = new ServicesFormViewModel(requireContext(), getArguments().getInt(SERVICE_TYPE.getValue()),
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
        binding.imageViewIcon.setImageDrawable(viewModel.getDrawable());
        binding.buttonSubmit.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            serviceActivity.submitUserInfo();
        } else if (id == R.id.button_previous) {
            serviceActivity.goServices();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) serviceActivity = (ICallback) context;
    }

    public interface ICallback {
        void submitUserInfo();

        void goServices();
    }
}
