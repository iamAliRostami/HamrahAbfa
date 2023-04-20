package com.leon.hamrah_abfa.fragments.services;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentServiceFormBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

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
        binding.getRoot().post(() -> binding.mapView.getLayoutParams().height = binding.mapView.getMeasuredWidth());
        initializeMap();
        binding.buttonSubmit.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
    }

    private void initializeMap() {
//        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        binding.mapView.getZoomController().
                setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        binding.mapView.setMultiTouchControls(true);
        final IMapController mapController = binding.mapView.getController();
        mapController.setZoom(19.0);
        final GeoPoint startPoint = new GeoPoint(32.65462762641145, 51.67064483950463);
        mapController.setCenter(startPoint);

//        if (getLocationTracker(activity).getCurrentLocation() != null) {
//            final GeoPoint startPoint = new GeoPoint(getLocationTracker(activity).getCurrentLocation().getLatitude(),
//                    getLocationTracker(activity).getCurrentLocation().getLongitude());
//            mapController.setCenter(startPoint);
//        }
//        final MyLocationNewOverlay locationOverlay =
//                new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), binding.mapView);
//        locationOverlay.enableMyLocation();
//        binding.mapView.getOverlays().add(locationOverlay);
        binding.mapView.getOverlays().add(new MapEventsOverlay(this));
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            serviceActivity.submitUserInfo();
        } else if (id == R.id.button_previous) {
            serviceActivity.goServices();
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
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }


    public interface ICallback {
        void submitUserInfo();

        void goServices();

        ServicesViewModel getServicesViewModel();
    }
}
