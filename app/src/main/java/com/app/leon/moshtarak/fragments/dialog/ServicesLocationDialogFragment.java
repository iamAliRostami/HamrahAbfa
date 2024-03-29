package com.app.leon.moshtarak.fragments.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.app.leon.moshtarak.databinding.FragmentServicesMapBinding;
import com.app.leon.moshtarak.enums.BundleEnum;
import com.app.leon.moshtarak.R;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class ServicesLocationDialogFragment extends DialogFragment implements View.OnClickListener, MapEventsReceiver {

    private FragmentServicesMapBinding binding;
    private ICallback callback;
    private GeoPoint point;

    public ServicesLocationDialogFragment() {
    }

    public static ServicesLocationDialogFragment newInstance() {
        return new ServicesLocationDialogFragment();
    }

    public static ServicesLocationDialogFragment newInstance(GeoPoint point) {
        ServicesLocationDialogFragment fragment = new ServicesLocationDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(BundleEnum.LATITUDE.getValue(), point.getLatitude());
        bundle.putDouble(BundleEnum.LONGITUDE.getValue(), point.getLongitude());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            point = new GeoPoint(getArguments().getDouble(BundleEnum.LATITUDE.getValue()), getArguments().getDouble(BundleEnum.LONGITUDE.getValue()));
            getArguments().clear();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServicesMapBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeMap();
        final LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                requireContext().getResources().getDisplayMetrics().widthPixels
                /*requireActivity().getWindowManager().getDefaultDisplay().getWidth() / 2*/
        );
        binding.relativeLayoutMap.setLayoutParams(params);
        binding.buttonSubmit.setOnClickListener(this);

    }

    private void initializeMap() {
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        binding.mapView.getZoomController().
                setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        binding.mapView.setMultiTouchControls(true);
        final IMapController mapController = binding.mapView.getController();
        mapController.setZoom(19.0);
        mapController.setCenter(point);
//TODO

        binding.mapView.getOverlays().add(new MapEventsOverlay(this));
    }

    private void addPlace(IGeoPoint p) {
        final GeoPoint geoPoint = new GeoPoint(p.getLatitude(), p.getLongitude());
        final Marker startMarker = new Marker(binding.mapView);
        startMarker.setPosition(geoPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.map_pointer_image));
        binding.mapView.getOverlays().add(startMarker);
    }


    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            point = new GeoPoint(binding.mapView.getMapCenter().getLatitude(), binding.mapView.getMapCenter().getLongitude());
            callback.setLocation(convertMapToBitmap(), point);
            dismiss();
        }
    }

    private Bitmap convertMapToBitmap() {
        addPlace();
        binding.mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        binding.mapView.setDrawingCacheEnabled(true);
        return binding.mapView.getDrawingCache(true);
    }

    private void addPlace() {
        final Marker startMarker = new Marker(binding.mapView);
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.map_pointer_image));
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
        if (context instanceof Activity) callback = (ICallback) context;
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

    public interface ICallback {
        void setLocation(Bitmap location, GeoPoint point);
    }
}
