package com.app.leon.moshtarak.fragments.services.buy;

import static com.app.leon.moshtarak.helpers.Constants.SERVICE_INTRODUCTION_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.SERVICE_LOCATION_FRAGMENT;
import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.databinding.FragmentServicePersonalBinding;
import com.app.leon.moshtarak.fragments.services.ServicesViewModel;
import com.app.leon.moshtarak.helpers.Constants;
import com.app.leon.moshtarak.R;

public class ServicePersonalFragment extends Fragment implements View.OnClickListener {
    private ICallback callback;
    private FragmentServicePersonalBinding binding;

    public ServicePersonalFragment() {
    }

    public static ServicePersonalFragment newInstance() {
        return new ServicePersonalFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServicePersonalBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getServicesViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

        binding.buttonNext.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if (id == R.id.button_next) {
            if (checkInputs())
                callback.displayView(SERVICE_LOCATION_FRAGMENT);
        } else if (id == R.id.button_previous) {
            callback.displayView(SERVICE_INTRODUCTION_FRAGMENT);
        }
    }

    private boolean checkInputs() {
        if (callback.getServicesViewModel().getFirstName() == null ||
                callback.getServicesViewModel().getFirstName().trim().isEmpty()) {
            warning(requireContext(), R.string.fill_in_all).show();
            binding.editTextFirstName.setError(getString(R.string.fill_in_first_name));
            binding.editTextFirstName.requestFocus();
            return false;
        }
        if (callback.getServicesViewModel().getSureName() == null ||
                callback.getServicesViewModel().getSureName().trim().isEmpty()) {
            warning(requireContext(), R.string.fill_in_all).show();
            binding.editTextSureName.setError(getString(R.string.fill_in_sure_name));
            binding.editTextSureName.requestFocus();
            return false;
        }
        if (callback.getServicesViewModel().getNationalId() == null ||
                callback.getServicesViewModel().getNationalId().isEmpty() ||
                callback.getServicesViewModel().getNationalId().length() < 10) {
            warning(requireContext(), R.string.incorrect_nation_code_format).show();
            binding.editTextNationCode.setError(getString(R.string.incorrect_nation_code_format));
            binding.editTextNationCode.requestFocus();
            return false;
        }
        if (callback.getServicesViewModel().getMobile() == null ||
                callback.getServicesViewModel().getMobile().length() < 11 ||
                !callback.getServicesViewModel().getMobile().substring(0, 2).contains("09")) {
            warning(requireContext(), R.string.incorrect_mobile_format).show();
            binding.editTextMobile.setError(getString(R.string.incorrect_mobile_format));
            binding.editTextMobile.requestFocus();
            return false;
        }
        if (callback.getServicesViewModel().getNeighbourBillId() == null ||
                callback.getServicesViewModel().getNeighbourBillId().isEmpty() ||
                callback.getServicesViewModel().getNeighbourBillId().length() < 5) {
            warning(requireContext(), R.string.incorrect_bill_id_format).show();
            binding.editTextBillId.setError(getString(R.string.incorrect_bill_id_format));
            binding.editTextBillId.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        ServicesViewModel getServicesViewModel();

        void displayView(int position);
    }
}