package com.leon.hamrah_abfa.fragments.incident;

import static com.leon.hamrah_abfa.helpers.Constants.POINT;
import static com.leon.hamrah_abfa.utils.FileCustomize.compressBitmap;
import static com.leon.hamrah_abfa.utils.FileCustomize.createImageFile;
import static com.leon.hamrah_abfa.utils.PermissionManager.checkCameraPermission;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;
import static com.leon.toast.RTLToast.warning;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.BuildConfig;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.ImageViewAdapter;
import com.leon.hamrah_abfa.databinding.FragmentIncidentCompleteBinding;
import com.leon.hamrah_abfa.utils.GpsTracker;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;

import java.io.File;
import java.io.IOException;

public class IncidentCompleteFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private FragmentIncidentCompleteBinding binding;
    private ICallback incidentActivity;
    private long lastClickTime = 0;
    private File fileImage = null;

    public IncidentCompleteFragment() {
    }

    public static IncidentCompleteFragment newInstance() {
        return new IncidentCompleteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIncidentCompleteBinding.inflate(inflater, container, false);
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
        if (incidentActivity.getImageViewAdapter() == null)
            incidentActivity.setImageViewAdapter(new ImageViewAdapter(requireContext()));
        binding.gridViewImages.setAdapter(incidentActivity.getImageViewAdapter());
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
            incidentActivity.confirm();
        } else if (id == R.id.button_previous) {
            incidentActivity.returnToBase();
        } else if (id == R.id.image_view_current_location) {
            showCurrentLocation();
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
        if (position + 1 == incidentActivity.getImageViewAdapter().getCount()) {
            if (checkCameraPermission(requireContext()))
                openCameraForResult();
            else {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        }
    }

    private void openCameraForResult() {
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getContext() != null && cameraIntent.resolveActivity(requireContext().getPackageManager()) != null) {
            try {
                fileImage = createImageFile(requireContext());
            } catch (IOException e) {
                error(requireContext(), e.getMessage()).show();
            }
            if (fileImage != null) {
                try {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(),
                            BuildConfig.APPLICATION_ID.concat(".provider"), fileImage));
                    cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    cameraResultLauncher.launch(cameraIntent);
                } catch (Exception e) {
                    error(requireContext(), e.getMessage()).show();
                }
            }
        } else {
            error(requireContext(), "صفحه ی عکس را بسته و مجددا باز کنید.").show();
        }
    }

    private final ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    final Bitmap bitmap = BitmapFactory.decodeFile(fileImage.getAbsolutePath(),
                            new BitmapFactory.Options());
                    incidentActivity.getImageViewAdapter().addItem(compressBitmap(requireContext(), bitmap));
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
        if (context instanceof Activity) incidentActivity = (ICallback) context;
    }

    public interface ICallback {
        IncidentViewModel getIncidentViewModel();

        void returnToBase();

        void confirm();

        ImageViewAdapter getImageViewAdapter();

        void setImageViewAdapter(ImageViewAdapter adapter);
    }
}