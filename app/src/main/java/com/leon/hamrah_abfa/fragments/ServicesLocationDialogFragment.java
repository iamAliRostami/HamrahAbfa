package com.leon.hamrah_abfa.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentServicesLocationBinding;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ServicesLocationFragment;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class ServicesLocationDialogFragment extends DialogFragment implements View.OnClickListener, MapEventsReceiver {

    private FragmentServicesLocationBinding binding;

    public ServicesLocationDialogFragment() {
    }

    public static ServicesLocationDialogFragment newInstance() {
        return new ServicesLocationDialogFragment();
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
//        binding.getRoot().post(() -> binding.mapView.getLayoutParams().height = binding.mapView.getMeasuredWidth());
//        binding.getRoot().post(() -> binding.relativeLayoutMap.getLayoutParams().height = binding.relativeLayoutMap.getMeasuredWidth());
        initializeMap();
        binding.buttonSubmit.setOnClickListener(this);

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
            convertMapToBitmap();
        }
    }

    private Bitmap convertMapToBitmap() {
        binding.mapView.destroyDrawingCache();
        binding.mapView.setDrawingCacheEnabled(true);
        addPlace(binding.mapView.getMapCenter());
        Bitmap bitmap = binding.mapView.getDrawingCache(true);
        return binding.mapView.getDrawingCache(true);
    }

    private void addPlace(IGeoPoint p) {
        final GeoPoint geoPoint = new GeoPoint(p.getLatitude(), p.getLongitude());
        final Marker startMarker = new Marker(binding.mapView);
        startMarker.setPosition(geoPoint);
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

    public interface ICallback {
        void setLocation(Bitmap location, GeoPoint point);
    }


    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onResume() {
        if (getDialog() != null) {
            final WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes(params);
        }
        super.onResume();
        binding.mapView.onResume();
    }
}
