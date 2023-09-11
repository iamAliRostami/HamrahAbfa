package com.app.leon.moshtarak.fragments.services.buy;

import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.databinding.FragmentServiceLocationBinding;
import com.app.leon.moshtarak.enums.FragmentTags;
import com.app.leon.moshtarak.fragments.services.ServicesMapFragment;
import com.app.leon.moshtarak.fragments.services.ServicesViewModel;
import com.app.leon.moshtarak.helpers.Constants;
import com.app.leon.moshtarak.utils.ShowFragment;

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
                callback.displayView(Constants.SERVICE_SUBMIT_INFORMATION_FRAGMENT);
        } else if (id == R.id.button_previous) {
            callback.displayView(Constants.SERVICE_PERSONAL_INFORMATION_FRAGMENT);
        } else if (id == R.id.image_view_location) {
            ShowFragment.showFragmentDialogOnce(requireContext(), FragmentTags.SERVICE_LOCATION.getValue(),
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
                callback.getServicesViewModel().getAddress().trim().isEmpty()) {
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