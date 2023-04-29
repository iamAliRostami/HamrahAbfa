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
import com.leon.hamrah_abfa.databinding.FragmentServiceFormBinding;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ServicesLocationFragment;
import com.leon.hamrah_abfa.fragments.dialog.ServicesLocationDialogFragment;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;
import static com.leon.toast.RTLToast.warning;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;

public class ServiceFormFragment extends Fragment implements View.OnClickListener, MapEventsReceiver {
    private FragmentServiceFormBinding binding;
    private ICallback serviceActivity;

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
        binding.setViewModel(serviceActivity.getServicesViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        if (serviceActivity.getServicesViewModel().getBitmapLocation() != null)
            binding.imageViewLocation.setImageBitmap(serviceActivity.getServicesViewModel().getBitmapLocation());
        binding.buttonNext.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
        binding.imageViewLocation.setOnClickListener(this);
        binding.imageViewLocation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ShowFragmentDialogOnce(requireContext(), SERVICE_LOCATION.getValue(),
                        ServicesLocationDialogFragment.newInstance(serviceActivity.getServicesViewModel().getPoint()));
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_next) {
            if (checkInputs())
                serviceActivity.submitUserInfo();
        } else if (id == R.id.button_previous) {
            serviceActivity.backToServices();
        } else if (id == R.id.image_view_location) {
            ShowFragmentDialogOnce(requireContext(), SERVICE_LOCATION.getValue(),
                    ServicesLocationFragment.newInstance(serviceActivity.getServicesViewModel().getPoint()));
        }
    }

    private boolean checkInputs() {
        if (serviceActivity.getServicesViewModel().getBitmapLocation() == null) {
            warning(requireContext(), R.string.locate_address).show();
            return false;
        }
        if (serviceActivity.getServicesViewModel().getBillId() == null ||
                serviceActivity.getServicesViewModel().getBillId().isEmpty() ||
                serviceActivity.getServicesViewModel().getBillId().length() < 5) {
            warning(requireContext(), R.string.incorrect_bill_id_format).show();
            binding.editTextBillId.setError(getString(R.string.incorrect_bill_id_format));
            binding.editTextBillId.requestFocus();
            return false;
        }
        if (serviceActivity.getServicesViewModel().getMobile() == null ||
                serviceActivity.getServicesViewModel().getMobile().length() < 11 ||
                !serviceActivity.getServicesViewModel().getMobile().substring(0, 2).contains("09")) {
            warning(requireContext(), R.string.incorrect_mobile_format).show();
            binding.editTextMobile.setError(getString(R.string.incorrect_mobile_format));
            binding.editTextMobile.requestFocus();
            return false;
        }
        if (serviceActivity.getServicesViewModel().getAddress() == null ||
                serviceActivity.getServicesViewModel().getAddress().isEmpty()) {
            warning(requireContext(), R.string.fill_in_address).show();
            binding.editTextAddress.setError(getString(R.string.fill_in_address));
            binding.editTextAddress.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) serviceActivity = (ICallback) context;
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
        void submitUserInfo();

        void backToServices();

        ServicesViewModel getServicesViewModel();
    }
}
