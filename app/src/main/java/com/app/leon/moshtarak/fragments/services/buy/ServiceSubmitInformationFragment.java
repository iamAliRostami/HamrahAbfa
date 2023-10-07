package com.app.leon.moshtarak.fragments.services.buy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.databinding.FragmentServiceSubmitBuyInfoBinding;
import com.app.leon.moshtarak.enums.FragmentTags;
import com.app.leon.moshtarak.fragments.dialog.WaitingFragment;
import com.app.leon.moshtarak.fragments.services.ServicesViewModel;
import com.app.leon.moshtarak.helpers.Constants;
import com.app.leon.moshtarak.requests.services.ServiceNewRequest;
import com.app.leon.moshtarak.utils.ShowFragment;
import com.google.android.material.chip.Chip;
import com.app.leon.moshtarak.R;

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
            requestNewService();
        } else if (id == R.id.button_previous) {
            callback.displayView(Constants.SERVICE_LOCATION_FRAGMENT);
        }
    }

    private void requestNewService() {
        progressStatus(new ServiceNewRequest(requireContext(), new ServiceNewRequest.ICallback() {
            @Override
            public void succeed(ServicesViewModel service) {
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
                ShowFragment.showFragmentDialogOnce(requireContext(), FragmentTags.WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null && fragment.getShowsDialog()) {
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