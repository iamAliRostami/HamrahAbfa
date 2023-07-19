package com.leon.hamrah_abfa.fragments.services.buy;

import static com.leon.hamrah_abfa.enums.FragmentTags.SERVICE_LOCATION;
import static com.leon.hamrah_abfa.helpers.Constants.SERVICE_PERSONAL_INFORMATION_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.SERVICE_SUBMIT_INFORMATION_FRAGMENT;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentServiceLocationBinding;
import com.leon.hamrah_abfa.fragments.services.ServicesMapFragment;
import com.leon.hamrah_abfa.fragments.services.ServicesViewModel;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;

public class ServiceLocationFragment extends Fragment implements View.OnClickListener, MapEventsReceiver {
    private ICallback callback;
    private FragmentServiceLocationBinding binding;

    public ServiceLocationFragment() {
    }

    public static ServiceLocationFragment newInstance() {
        return new ServiceLocationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceLocationBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getServicesViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
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
            //TODO
            if (checkInputs())
                callback.displayView(SERVICE_SUBMIT_INFORMATION_FRAGMENT);
        } else if (id == R.id.button_previous) {
            callback.displayView(SERVICE_PERSONAL_INFORMATION_FRAGMENT);
        } else if (id == R.id.image_view_location) {
            showFragmentDialogOnce(requireContext(), SERVICE_LOCATION.getValue(),
                    ServicesMapFragment.newInstance(callback.getServicesViewModel().getPoint()));
        }
    }

    private boolean checkInputs() {
        if (callback.getServicesViewModel().getPostalCode() == null ||
                callback.getServicesViewModel().getPostalCode().isEmpty() ||
                callback.getServicesViewModel().getPostalCode().length() < 10) {
            warning(requireContext(), R.string.incorrect_postal_code_format).show();
            binding.editTextPostalCode.setError(getString(R.string.incorrect_postal_code_format));
            binding.editTextPostalCode.requestFocus();
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
        return true;
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        return false;
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