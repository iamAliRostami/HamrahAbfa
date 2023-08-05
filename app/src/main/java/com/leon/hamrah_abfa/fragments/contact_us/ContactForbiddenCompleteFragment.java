package com.leon.hamrah_abfa.fragments.contact_us;

import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.helpers.Constants.POINT;
import static com.leon.hamrah_abfa.utils.FileCustomizer.bitmapToFile;
import static com.leon.hamrah_abfa.utils.FileCustomizer.createImageFile;
import static com.leon.hamrah_abfa.utils.FileCustomizer.prepareImage;
import static com.leon.hamrah_abfa.utils.PermissionManager.checkCameraPermission;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;
import static com.leon.toast.RTLToast.warning;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.BuildConfig;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.ImageViewAdapter;
import com.leon.hamrah_abfa.databinding.FragmentContactForbiddenCompleteBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.requests.contact_us.ForbiddenRequest;
import com.leon.hamrah_abfa.utils.GpsTracker;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;

import java.io.File;
import java.io.IOException;

public class ContactForbiddenCompleteFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    private FragmentContactForbiddenCompleteBinding binding;
    private long lastClickTime = 0;
    private File fileImage = null;
    private ICallback callback;
    private DialogFragment fragment;

    public ContactForbiddenCompleteFragment() {
    }

    public static ContactForbiddenCompleteFragment newInstance() {
        return new ContactForbiddenCompleteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactForbiddenCompleteBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeMap();
        initializeGridView();
        binding.buttonConfirm.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
        binding.imageViewCurrentLocation.setOnClickListener(this);
    }

    private void initializeGridView() {
        if (callback.getImageViewAdapter() == null)
            callback.setImageViewAdapter(new ImageViewAdapter(requireContext()));
        binding.gridViewImages.setAdapter(callback.getImageViewAdapter());
        binding.gridViewImages.setOnItemClickListener(this);
    }

    private void initializeMap() {
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        binding.mapView.getZoomController().
                setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        binding.mapView.setMultiTouchControls(true);
        final IMapController mapController = binding.mapView.getController();
        mapController.setZoom(19.0);
        mapController.setCenter(POINT);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_confirm) {
            callback.getForbiddenViewModel().setX(String.valueOf(binding.mapView.getMapCenter().getLongitude()));
            callback.getForbiddenViewModel().setY(String.valueOf(binding.mapView.getMapCenter().getLatitude()));
            for (Bitmap bitmap : callback.getImageViewAdapter().getBitmaps()) {
                callback.getForbiddenViewModel().file.add(bitmapToFile(bitmap, requireContext()));
            }
            requestForbidden();
        } else if (id == R.id.button_previous) {
            requireActivity().getSupportFragmentManager().popBackStack();
        } else if (id == R.id.image_view_current_location) {
            showCurrentLocation();
        }
    }

    private void requestForbidden() {
        boolean isOnline = new ForbiddenRequest(requireContext(), new ForbiddenRequest.ICallback() {
            @Override
            public void succeed(ForbiddenViewModel service) {
                //TODO
                callback.confirm(service.getMessage());
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, callback.getForbiddenViewModel()).request();
        progressStatus(isOnline);
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                showFragmentDialogOnce(requireContext(), WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void showCurrentLocation() {
        final GpsTracker gpsTracker = new GpsTracker(requireContext());
        if (!gpsTracker.canGetLocation()) {
            gpsTracker.showSettingsAlert();
        } else if (gpsTracker.getLocation() != null) {
            final IMapController mapController = binding.mapView.getController();
            mapController.setZoom(19.5);
            mapController.setCenter(new GeoPoint(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
        } else {
            warning(requireContext(), R.string.make_sure_internet_is_connected).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
        lastClickTime = SystemClock.elapsedRealtime();
        if (position + 1 == callback.getImageViewAdapter().getCount()) {
            if (checkCameraPermission(requireContext()))
                openCameraForResult();
            else {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        }
    }

    private void openCameraForResult() {
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            fileImage = createImageFile(requireContext());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(requireContext(),
                    BuildConfig.APPLICATION_ID.concat(".provider"), fileImage));
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            cameraResultLauncher.launch(cameraIntent);
        } catch (IOException e) {
            error(requireContext(), e.getMessage()).show();
        }
    }

    private final ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    try {
                        callback.getImageViewAdapter().addItem(prepareImage(fileImage));
                    } catch (IOException e) {
                        error(requireContext(), e.getMessage()).show();
                    }
                }
            });


    private final ActivityResultLauncher<String> requestCameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    success(requireContext(), getString(R.string.camera_permission_granted)).show();
                } else {
                    error(requireContext(), getString(R.string.camera_permission_unavailable)).show();
                }
            });


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        void displayView(int position);

        ForbiddenViewModel getForbiddenViewModel();

        ImageViewAdapter getImageViewAdapter();

        void setImageViewAdapter(ImageViewAdapter adapter);

        void confirm(String trackNumber);
    }
}