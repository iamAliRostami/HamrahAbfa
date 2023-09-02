package com.leon.hamrah_abfa.fragments.services.after;

import static com.leon.hamrah_abfa.enums.FragmentTags.SERVICE_LOCATION;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.helpers.Constants.SERVICE_FORM_FRAGMENT;
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
import com.leon.hamrah_abfa.databinding.FragmentServiceSubmitInfoBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.fragments.services.ServicesMapFragment;
import com.leon.hamrah_abfa.fragments.services.ServicesViewModel;
import com.leon.hamrah_abfa.requests.services.ServiceASRequest;
import com.leon.hamrah_abfa.requests.services.ServiceAbRequest;

public class ServiceSubmitInformationFragment extends Fragment implements View.OnClickListener {
    private FragmentServiceSubmitInfoBinding binding;
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
        binding = FragmentServiceSubmitInfoBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getServicesViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.imageViewLocation.setImageBitmap(callback.getServicesViewModel().getBitmapLocation());
        binding.buttonConfirm.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
        for (int i = 0; i < callback.getServicesViewModel().getSelectedServicesString().size(); i++) {
            final Chip chip = new Chip(requireContext());
            chip.setCloseIconVisible(false);
            chip.setTextAppearance(R.style.ChipTextAppearance);
            chip.setChipBackgroundColorResource(R.color.light);
            chip.setText(callback.getServicesViewModel().getSelectedServicesString().get(i));
            binding.chipGroupServices.addView(chip);
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_confirm) {
            if (callback.getServicesViewModel().getServiceType() == 1)
                requestASService();
            else if (callback.getServicesViewModel().getServiceType() == 2)
                requestAbService();
        } else if (id == R.id.button_previous) {
            callback.displayView(SERVICE_FORM_FRAGMENT, false);
        } else if (id == R.id.image_view_location) {
            showFragmentDialogOnce(requireContext(), SERVICE_LOCATION.getValue(),
                    ServicesMapFragment.newInstance(callback.getServicesViewModel().getPoint()));
        }
    }

    private void requestASService() {
        progressStatus(new ServiceASRequest(requireContext(), new ServiceASRequest.ICallback() {
            @Override
            public void succeed(ServicesViewModel service) {
                //TODO
                callback.submitInformation(service.getTrackNumber());
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, callback.getServicesViewModel()).request());
    }

    private void requestAbService() {
        progressStatus(new ServiceAbRequest(requireContext(), new ServiceAbRequest.ICallback() {
            @Override
            public void succeed(ServicesViewModel service) {
                //TODO
                callback.submitInformation(service.getTrackNumber());
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, callback.getServicesViewModel()).request());
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

        void displayView(int position, boolean next);
    }
}