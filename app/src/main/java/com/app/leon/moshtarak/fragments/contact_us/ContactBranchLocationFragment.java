package com.app.leon.moshtarak.fragments.contact_us;

import static com.app.leon.moshtarak.enums.BundleEnum.LATITUDE;
import static com.app.leon.moshtarak.enums.BundleEnum.LONGITUDE;
import static com.leon.toast.RTLToast.warning;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.app.leon.moshtarak.databinding.FragmentContactBranchLocationBinding;
import com.app.leon.moshtarak.utils.GpsTracker;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.Locale;

public class ContactBranchLocationFragment extends BaseBottomSheetFragment implements View.OnClickListener {
    private FragmentContactBranchLocationBinding binding;
    private GeoPoint point;

    public ContactBranchLocationFragment() {
    }

    public static ContactBranchLocationFragment newInstance(double x, double y) {
        final ContactBranchLocationFragment fragment = new ContactBranchLocationFragment();
        final Bundle args = new Bundle();
        args.putDouble(LONGITUDE.getValue(), x);
        args.putDouble(LATITUDE.getValue(), y);
        fragment.setArguments(args);
        fragment.setCancelable(false);
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
        binding = FragmentContactBranchLocationBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeMap();
        final LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                requireContext().getResources().getDisplayMetrics().widthPixels);
        binding.relativeLayoutMap.setLayoutParams(params);
        binding.buttonExternal.setOnClickListener(this);
        binding.buttonInternal.setOnClickListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeMap() {
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        final IMapController mapController = binding.mapView.getController();
        mapController.setZoom(19.0);
        mapController.setCenter(point);
        addPlace();
    }

    private void addPlace() {
        final Marker startMarker = new Marker(binding.mapView);
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.map_pointer));
        binding.mapView.getOverlays().add(startMarker);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        final int id = v.getId();
        if (id == R.id.button_internal) {
            internalRouting();
        } else if (id == R.id.button_external) {
            externalRouting();
        }
    }

    private void externalRouting() {
        try {
            String uriString = String.format(Locale.US, "geo:%f,%f?q=%s&z=19", point.getLatitude(),
                    point.getLongitude(), Uri.encode(String.format(Locale.US, "%f,%f(label)",
                            point.getLatitude(), point.getLongitude())));
            final Uri uri = Uri.parse(uriString);
            final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            warning(requireContext(), R.string.gps_is_not_available).show();
        }
    }

    private void internalRouting() {
        final GpsTracker gpsTracker = new GpsTracker(requireContext());
        if (!gpsTracker.canGetLocation()) {
            gpsTracker.showSettingsAlert();
        } else if (gpsTracker.getLocation() != null) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            ArrayList<GeoPoint> wayPoints = new ArrayList<>();
            wayPoints.add(new GeoPoint(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
            wayPoints.add(point);
            Polyline roadOverlay = RoadManager.buildRoadOverlay(new OSRMRoadManager(requireContext(),
                    Configuration.getInstance().getUserAgentValue()).getRoad(wayPoints));
//            roadOverlay.setWidth(12);
            roadOverlay.setColor(R.color.purple_7001);
            binding.mapView.getOverlays().add(roadOverlay);
            binding.mapView.invalidate();
        } else {
            warning(requireContext(), R.string.make_sure_internet_is_connected).show();
        }
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
}