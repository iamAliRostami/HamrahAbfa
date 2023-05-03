package com.leon.hamrah_abfa.fragments.services;

import static com.leon.hamrah_abfa.enums.FragmentTags.SERVICE_LOCATION;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentServiceSubmitInformationBinding;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ServicesLocationFragment;

public class ServiceSubmitInformationFragment extends Fragment implements View.OnClickListener {
    private FragmentServiceSubmitInformationBinding binding;
    private ICallback serviceActivity;

    public ServiceSubmitInformationFragment() {
    }

    public static ServiceSubmitInformationFragment newInstance() {
        return new ServiceSubmitInformationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceSubmitInformationBinding.inflate(inflater, container, false);
        binding.setViewModel(serviceActivity.getServicesViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.imageViewLocation.setImageBitmap(serviceActivity.getServicesViewModel().getBitmapLocation());
        binding.buttonConfirm.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
        for (int i = 0; i < serviceActivity.getServicesViewModel().getSelectedServices().size(); i++) {
            final Chip chip = new Chip(requireContext());
            chip.setCloseIconVisible(false);
            chip.setTextAppearance(R.style.ChipTextAppearance);
            chip.setChipBackgroundColorResource(R.color.light);
            chip.setText(serviceActivity.getServicesViewModel().getSelectedServices().get(i));
            binding.chipGroupServices.addView(chip);
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_confirm) {
            serviceActivity.submitInformation();
        } else if (id == R.id.button_previous) {
            serviceActivity.backToServiceForm();
        } else if (id == R.id.image_view_location) {
            ShowFragmentDialogOnce(requireContext(), SERVICE_LOCATION.getValue(),
                    ServicesLocationFragment.newInstance(serviceActivity.getServicesViewModel().getPoint()));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) serviceActivity = (ICallback) context;
    }

    public interface ICallback {
        ServicesViewModel getServicesViewModel();

        void backToServiceForm();

        void submitInformation();
    }
}