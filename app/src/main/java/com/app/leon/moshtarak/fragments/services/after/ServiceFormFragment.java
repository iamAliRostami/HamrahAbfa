package com.app.leon.moshtarak.fragments.services.after;

import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.databinding.FragmentServiceFormBinding;
import com.app.leon.moshtarak.enums.FragmentTags;
import com.app.leon.moshtarak.fragments.services.ServicesMapFragment;
import com.app.leon.moshtarak.fragments.services.ServicesViewModel;
import com.app.leon.moshtarak.helpers.Constants;
import com.app.leon.moshtarak.utils.ShowFragment;
import com.app.leon.moshtarak.R;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;

public class ServiceFormFragment extends Fragment implements View.OnClickListener, MapEventsReceiver {
    private FragmentServiceFormBinding binding;
    private ICallback callback;

    public ServiceFormFragment() {
    }

    public static ServiceFormFragment newInstance() {
        return new ServiceFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceFormBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getServicesViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        if (callback.getServicesViewModel().getSelectedServices().get(0) ==
                getResources().getIntArray(R.array.services_ab_baha_id)[4]) {
            binding.textLayoutEmpty.setVisibility(View.VISIBLE);
        }
        if (callback.getServicesViewModel().getSelectedServices().get(0) ==
                getResources().getIntArray(R.array.services_ab_baha_id)[5]) {
            binding.textLayoutCounterNumber.setVisibility(View.VISIBLE);
        }

        if (callback.getServicesViewModel().getBitmapLocation() != null)
            binding.imageViewLocation.setImageBitmap(callback.getServicesViewModel().getBitmapLocation());
        binding.buttonNext.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
        binding.imageViewLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_next) {
            if (checkInputs())
                callback.displayView(Constants.SERVICE_SUBMIT_INFORMATION_FRAGMENT, true);
        } else if (id == R.id.button_previous) {
            callback.displayView(Constants.SERVICE_INTRODUCTION_FRAGMENT, false);
        } else if (id == R.id.image_view_location) {
            ShowFragment.showFragmentDialogOnce(requireContext(), FragmentTags.SERVICE_LOCATION.getValue(),
                    ServicesMapFragment.newInstance(callback.getServicesViewModel().getPoint()));
        }
    }

    private boolean checkInputs() {
        if (callback.getServicesViewModel().getMobile() == null ||
                callback.getServicesViewModel().getMobile().length() < 11 ||
                !callback.getServicesViewModel().getMobile().substring(0, 2).contains("09")) {
            warning(requireContext(), R.string.incorrect_mobile_format).show();
            binding.editTextMobile.setError(getString(R.string.incorrect_mobile_format));
            binding.editTextMobile.requestFocus();
            return false;
        }
        if (callback.getServicesViewModel().getBillId() == null ||
                callback.getServicesViewModel().getBillId().isEmpty() ||
                callback.getServicesViewModel().getBillId().length() < 5) {
            warning(requireContext(), R.string.incorrect_bill_id_format).show();
            binding.editTextBillId.setError(getString(R.string.incorrect_bill_id_format));
            binding.editTextBillId.requestFocus();
            return false;
        }
        if (callback.getServicesViewModel().getAddress() == null ||
                callback.getServicesViewModel().getAddress().isEmpty()) {
            warning(requireContext(), R.string.fill_in_address).show();
            binding.editTextAddress.setError(getString(R.string.fill_in_address));
            binding.editTextAddress.requestFocus();
            return false;
        }
        if (callback.getServicesViewModel().getBitmapLocation() == null) {
            warning(requireContext(), R.string.locate_address).show();
            return false;
        }
        return checkExceptions();
    }

    private boolean checkExceptions() {
        if (callback.getServicesViewModel().getSelectedServices().get(0) ==
                getResources().getIntArray(R.array.services_ab_baha_id)[4]) {
            if (callback.getServicesViewModel().getEmpty() == null ||
                    callback.getServicesViewModel().getEmpty().isEmpty()) {
                warning(requireContext(), R.string.fill_in_empty).show();
                binding.editTextEmpty.setError(getString(R.string.fill_in_empty));
                binding.editTextEmpty.requestFocus();
                return false;
            }
        }

        if (callback.getServicesViewModel().getSelectedServices().get(0) ==
                getResources().getIntArray(R.array.services_ab_baha_id)[5]) {
            if (callback.getServicesViewModel().getCounterNumber() == null ||
                    callback.getServicesViewModel().getCounterNumber().isEmpty()) {
                warning(requireContext(), R.string.enter_counter_number).show();
                binding.editTextCounterNumber.setError(getString(R.string.enter_counter_number));
                binding.editTextCounterNumber.requestFocus();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        return false;
    }

    public interface ICallback {
        ServicesViewModel getServicesViewModel();

        void displayView(int position, boolean next);
    }
}
