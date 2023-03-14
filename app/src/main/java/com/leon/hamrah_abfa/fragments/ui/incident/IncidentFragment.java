package com.leon.hamrah_abfa.fragments.ui.incident;

import static com.leon.hamrah_abfa.utils.PermissionManager.checkRecorderPermission;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;

import android.Manifest;
import android.graphics.drawable.InsetDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentIncidentBinding;
import com.leon.toast.RTLToast;

import java.io.IOException;

public class IncidentFragment extends Fragment implements View.OnClickListener {
    private FragmentIncidentBinding binding;

    private MediaRecorder mediaRecorder;

    private MediaPlayer mediaPlayer;
    private boolean recording;

    public IncidentFragment() {
    }

    public static IncidentFragment newInstance() {
        return new IncidentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIncidentBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
//        binding.menu.setOnClickListener(this);
//        binding.audioRecordView.recreate();
//        mediaRecorder = new MediaRecorder();
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        mediaRecorder.setOutputFile(requireContext().getExternalFilesDir(null).getAbsolutePath() +
//                "/voice.ogg");
        binding.textViewIncidentType.setOnClickListener(this);
        binding.imageViewMic.setOnClickListener(this);
    }

    private void showMenu(View v, @MenuRes int menuRes) {
        final PopupMenu popup = new PopupMenu(requireActivity(), v, Gravity.TOP);
        // Inflating the Popup using xml file
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        // There is no public API to make icons show on menus.
        // IF you need the icons to show this works however it's discouraged to rely on library only
        // APIs since they might disappear in future versions.
        if (popup.getMenu() instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) popup.getMenu();
            //noinspection RestrictedApi
            menuBuilder.setOptionalIconsVisible(true);
            //noinspection RestrictedApi
            for (MenuItem item : menuBuilder.getVisibleItems()) {
                int iconMarginPx =
                        (int)
                                TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP, R.dimen.low_dp, getResources().getDisplayMetrics());

                if (item.getIcon() != null) {
                    item.setIcon(new InsetDrawable(item.getIcon(), iconMarginPx, 0, iconMarginPx, 0));
                }
            }
        }
        popup.setOnMenuItemClickListener(
                menuItem -> {
                    binding.textViewIncidentType.setText(menuItem.getTitle());
                    return true;
                });
        popup.show();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (/*id == R.id.menu || */id == R.id.text_view_incident_type) {
            showMenu(binding.textViewIncidentType, R.menu.incident_menu);
        } else if (id == R.id.image_view_mic) {
            if (checkRecorderPermission(requireContext())) {
                setupMediaRecorder();
            } else {
                requestPermissionLauncher.launch(
                        Manifest.permission.RECORD_AUDIO);
            }
        }
    }

    private void setupMediaRecorder() {
//TODO
        binding.audioRecordView.recreate();
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(requireContext().getExternalFilesDir(null).getAbsolutePath() +
                "/voice.ogg");

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
            mediaRecorder.stop();
        }
        final Handler myHandler = new Handler();
        final Runnable UpdateSongTime = new Runnable() {
            public void run() {
                binding.audioRecordView.update(mediaRecorder.getMaxAmplitude());
                myHandler.postDelayed(this, 200);
            }
        };
        myHandler.postDelayed(UpdateSongTime, 200);

    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    success(requireContext(), getString(R.string.voice_recorder_permission_granted)).show();
                } else {
                    error(requireContext(), getString(R.string.voice_recorder_permission_unavailable)).show();
                }
            });
}