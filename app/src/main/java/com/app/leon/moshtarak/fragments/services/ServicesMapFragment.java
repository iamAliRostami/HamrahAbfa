package com.app.leon.moshtarak.fragments.services;

import static com.app.leon.moshtarak.enums.BundleEnum.LATITUDE;
import static com.app.leon.moshtarak.enums.BundleEnum.LONGITUDE;
import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.base_items.BaseBottomSheetFragment;
import com.app.leon.moshtarak.databinding.FragmentServicesMapBinding;
import com.app.leon.moshtarak.utils.GpsTracker;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class ServicesMapFragment extends BaseBottomSheetFragment implements MapEventsReceiver {
    private FragmentServicesMapBinding binding;
    private ICallback callback;
    private GeoPoint point;

    public ServicesMapFragment() {
    }

    public static ServicesMapFragment newInstance() {
        return new ServicesMapFragment();
    }

    public static ServicesMapFragment newInstance(GeoPoint point) {
        final ServicesMapFragment fragment = new ServicesMapFragment();
        final Bundle bundle = new Bundle();
        bundle.putDouble(LATITUDE.getValue(), point.getLatitude());
        bundle.putDouble(LONGITUDE.getValue(), point.getLongitude());
        fragment.setCancelable(false);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            point = new GeoPoint(getArguments().getDouble(LATITUDE.getValue()),
                    getArguments().getDouble(LONGITUDE.getValue()));
            getArguments().clear();
        }
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentServicesMapBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeMap();
        final LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                requireContext().getResources().getDisplayMetrics().widthPixels
                /*requireActivity().getWindowManager().getDefaultDisplay().getWidth() / 2*/);
        binding.relativeLayoutMap.setLayoutParams(params);
        binding.buttonSubmit.setOnClickListener(this);
        binding.imageViewCurrentLocation.setOnClickListener(this);
        binding.imageViewArrowDown.setOnClickListener(this);

    }

    private void initializeMap() {
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        OnlineTileSourceBase onlineTileSourceBase = new OnlineTileSourceBase("USGS Topo", 2, 22, 256, "png",
                new String[]{
                        "https://a.tile.opentopomap.org/",
                        "https://b.tile.opentopomap.org/",
                        "https://c.tile.opentopomap.org/"
                }) {

            @Override
            public String getTileURLString(long pMapTileIndex) {
                return getBaseUrl()
                        + MapTileIndex.getZoom(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + "/" + MapTileIndex.getY(pMapTileIndex)
                        + "." + mImageFilenameEnding;
            }
        };
        binding.mapView.setTileSource(onlineTileSourceBase);
        binding.mapView.getZoomController().
                setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        binding.mapView.setMultiTouchControls(true);
        final IMapController mapController = binding.mapView.getController();
//        mapController.setZoom(19.0);
        mapController.setZoom(16.5);
        mapController.setCenter(point);
        binding.mapView.getOverlays().add(new MapEventsOverlay(this));
    }


    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            point = new GeoPoint(binding.mapView.getMapCenter().getLatitude(), binding.mapView.getMapCenter().getLongitude());
            callback.setLocation(convertMapToBitmap(), point);
            dismiss();
        } else if (id == R.id.image_view_current_location) {
            showCurrentLocation();
        } else if (id == R.id.image_view_arrow_down) {
            dismiss();
        }
    }

    private void showCurrentLocation() {
        final GpsTracker gpsTracker = new GpsTracker(requireContext());
        if (!gpsTracker.canGetLocation()) {
            gpsTracker.showSettingsAlert();
        } else if (gpsTracker.getLocation() != null) {
            final IMapController mapController = binding.mapView.getController();
            mapController.setZoom(19.5);
            point = new GeoPoint(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            mapController.setCenter(point);
        } else {
            warning(requireContext(), R.string.make_sure_internet_is_connected).show();
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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View bottomSheet = view.findViewById(R.id.linear_layout_container);
        if (bottomSheet != null) {
            final DisplayMetrics displayMetrics = requireActivity().getResources().getDisplayMetrics();
            BottomSheetBehavior.from(bottomSheet).setPeekHeight((int) (displayMetrics.heightPixels * 0.99));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog serviceLocationDialog = super.onCreateDialog(savedInstanceState);
        serviceLocationDialog.setOnShowListener(dialog -> {
            final BottomSheetDialog bottomDialog = (BottomSheetDialog) dialog;
            final FrameLayout bottomSheet = bottomDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                final DisplayMetrics displayMetrics = requireActivity().getResources().getDisplayMetrics();
                BottomSheetBehavior.from(bottomSheet).setPeekHeight((int) (displayMetrics.heightPixels * 0.99));
            }
        });
        return serviceLocationDialog;
    }

    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        void setLocation(Bitmap location, GeoPoint point);
    }
}