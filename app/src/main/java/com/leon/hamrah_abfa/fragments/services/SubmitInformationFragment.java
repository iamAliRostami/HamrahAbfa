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

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentSubmitInformationBinding;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ServicesLocationFragment;

public class SubmitInformationFragment extends Fragment implements View.OnClickListener {
    private FragmentSubmitInformationBinding binding;
    private ICallback serviceActivity;

    public SubmitInformationFragment() {
    }

    public static SubmitInformationFragment newInstance() {
        return new SubmitInformationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSubmitInformationBinding.inflate(inflater, container, false);
        binding.setViewModel(serviceActivity.getServicesViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.imageViewLocation.setImageBitmap(serviceActivity.getServicesViewModel().getBitmapLocation());
        binding.buttonSubmit.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            //TODO
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
    }
}