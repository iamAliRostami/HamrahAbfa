package com.leon.hamrah_abfa.fragments.services.buy;

import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.helpers.Constants.SERVICE_LOCATION_FRAGMENT;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentServiceSubmitBuyInfoBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.fragments.services.ServicesViewModel;
import com.leon.hamrah_abfa.requests.services.ServiceNewRequest;

public class ServiceSubmitInformationFragment extends Fragment implements View.OnClickListener {
    private FragmentServiceSubmitBuyInfoBinding binding;
    private ICallback callback;
    private DialogFragment fragment;

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
        binding = FragmentServiceSubmitBuyInfoBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getServicesViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.imageViewLocation.setImageBitmap(callback.getServicesViewModel().getBitmapLocation());
        binding.buttonConfirm.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
        for (int i = 0; i < callback.getServicesViewModel().getSelectedServices().size(); i++) {
            final Chip chip = new Chip(requireContext());
            chip.setCloseIconVisible(false);
            chip.setTextAppearance(R.style.ChipTextAppearance);
            chip.setChipBackgroundColorResource(R.color.light);
            chip.setText(callback.getServicesViewModel().getSelectedServices().get(i));
            binding.chipGroupServices.addView(chip);
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_confirm) {
            requestNewService();
        } else if (id == R.id.button_previous) {
            callback.displayView(SERVICE_LOCATION_FRAGMENT);
        }
    }

    private void requestNewService() {

        boolean isOnline = new ServiceNewRequest(requireContext(), new ServiceNewRequest.ICallback() {
            @Override
            public void succeed(ServicesViewModel service) {

            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, callback.getServicesViewModel()).request();
        progressStatus(isOnline);
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                showFragmentDialogOnce(requireContext(), WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        ServicesViewModel getServicesViewModel();//

        void submitInformation(String trackNumber);

        void displayView(int position);
    }
}