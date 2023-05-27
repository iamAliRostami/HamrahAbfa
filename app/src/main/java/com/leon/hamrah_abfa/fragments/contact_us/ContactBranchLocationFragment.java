package com.leon.hamrah_abfa.fragments.contact_us;

import static com.leon.hamrah_abfa.enums.BundleEnum.LATITUDE;
import static com.leon.hamrah_abfa.enums.BundleEnum.LONGITUDE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseBottomSheetFragment;
import com.leon.hamrah_abfa.databinding.FragmentContactBranchLocationBinding;
import com.leon.toast.RTLToast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

public class ContactBranchLocationFragment extends BaseBottomSheetFragment implements View.OnClickListener {
    private FragmentContactBranchLocationBinding binding;
    private GeoPoint point;

    public ContactBranchLocationFragment() {
    }

    public static ContactBranchLocationFragment newInstance(GeoPoint point) {
        final ContactBranchLocationFragment fragment = new ContactBranchLocationFragment();
        final Bundle args = new Bundle();
        args.putDouble(LONGITUDE.getValue(), point.getLongitude());
        args.putDouble(LATITUDE.getValue(), point.getLatitude());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            point = new GeoPoint(getArguments().getDouble(LATITUDE.getValue()),
                    getArguments().getDouble(LONGITUDE.getValue()));
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
                requireContext().getResources().getDisplayMetrics().widthPixels
                /*requireActivity().getWindowManager().getDefaultDisplay().getWidth() / 2*/);
        binding.relativeLayoutMap.setLayoutParams(params);
        binding.buttonExternal.setOnClickListener(this);
        binding.buttonInternal.setOnClickListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeMap() {
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        binding.mapView.setOnTouchListener((v, event) -> true);
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




        } else if (id == R.id.button_external) {
            try {
                final String uriString = "geo:" + point.getLatitude() + "," + point.getLongitude() + "?q=" +
                        Uri.encode(point.getLatitude() + "," + point.getLongitude() + "(label)") + "&z=19";
                final Uri uri = Uri.parse(uriString);
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } catch (Exception e) {
                RTLToast.warning(requireContext(), "دستگاه شما مجهز به مسیریاب نیست.").show();
            }
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