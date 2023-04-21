package com.leon.hamrah_abfa.fragments.bottom_sheets;

import static com.leon.hamrah_abfa.enums.BundleEnum.WIDTH;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentServicesLocationBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class ServicesLocationFragment extends BottomSheetDialogFragment implements View.OnClickListener, MapEventsReceiver {
    private FragmentServicesLocationBinding binding;
    private ICallback serviceActivity;

    public ServicesLocationFragment() {
    }

    public static ServicesLocationFragment newInstance() {
        return new ServicesLocationFragment();
    }

    public static ServicesLocationFragment newInstance(int width) {
        ServicesLocationFragment fragment = new ServicesLocationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(WIDTH.getValue(), width);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServicesLocationBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
//        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                requireActivity().getWindowManager().getDefaultDisplay().getWidth()
//        );
        final LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                requireActivity().getWindowManager().getDefaultDisplay().getWidth()
        );
//        binding.mapView.setLayoutParams(params);
        binding.relativeLayoutMap.setLayoutParams(params);
        initializeMap();
        binding.buttonSubmit.setOnClickListener(this);

    }

    private void initializeMap() {
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        binding.mapView.getZoomController().
                setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        binding.mapView.setMultiTouchControls(true);
        final GeoPoint startPoint = new GeoPoint(32.65462762641145, 51.67064483950463);
        final IMapController mapController = binding.mapView.getController();
        mapController.setZoom(19.0);
        mapController.setCenter(startPoint);

//TODO

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
            final GeoPoint point = new GeoPoint(binding.mapView.getMapCenter().getLatitude(), binding.mapView.getMapCenter().getLongitude());
            serviceActivity.setLocation(convertMapToBitmap(point), point);
            dismiss();
        }
    }

    private Bitmap convertMapToBitmap(GeoPoint point) {
//        binding.mapView.destroyDrawingCache();
        binding.mapView.setDrawingCacheEnabled(true);
        addPlace(point);
//        Bitmap bitmap = binding.mapView.getDrawingCache(true);
        return binding.mapView.getDrawingCache(true);
    }

    private void addPlace(GeoPoint point) {
        final Marker startMarker = new Marker(binding.mapView);
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.map_pointer));
        binding.mapView.getOverlays().add(startMarker);
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
        if (context instanceof Activity) serviceActivity = (ICallback) context;
    }

    public interface ICallback {
        void setLocation(Bitmap location, GeoPoint point);
    }

    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }
}