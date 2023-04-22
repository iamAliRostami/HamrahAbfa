package com.leon.hamrah_abfa.fragments.services;

import static com.leon.hamrah_abfa.enums.FragmentTags.SERVICE_LOCATION;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentServiceFormBinding;
import com.leon.hamrah_abfa.fragments.ServicesLocationDialogFragment;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ServicesLocationFragment;

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
        Bitmap bitmap = serviceActivity.getServicesViewModel().getBitmapLocation();
        bitmap = serviceActivity.getBitmap();
        if (serviceActivity.getServicesViewModel().getBitmapLocation() != null)
//            binding.imageViewLocation.setImageBitmap(serviceActivity.getServicesViewModel().getBitmapLocation());
            binding.imageViewLocation.setImageBitmap(serviceActivity.getBitmap());
        binding.buttonSubmit.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
        binding.imageViewLocation.setOnClickListener(this);
        binding.imageViewLocation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ShowFragmentDialogOnce(requireContext(), SERVICE_LOCATION.getValue(), ServicesLocationDialogFragment.newInstance());
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            serviceActivity.submitUserInfo();
        } else if (id == R.id.button_previous) {
            serviceActivity.goServices();
        } else if (id == R.id.image_view_location) {
            ShowFragmentDialogOnce(requireContext(), SERVICE_LOCATION.getValue(), ServicesLocationFragment.newInstance(serviceActivity.getServicesViewModel().getPoint()));
        }
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

        void goServices();

        ServicesViewModel getServicesViewModel();

        Bitmap getBitmap();
    }
}
