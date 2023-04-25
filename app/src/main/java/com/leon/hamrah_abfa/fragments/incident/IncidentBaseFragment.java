package com.leon.hamrah_abfa.fragments.incident;

import static com.leon.hamrah_abfa.utils.PermissionManager.checkRecorderPermission;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;
import static com.leon.toast.RTLToast.warning;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentIncidentBaseBinding;

import java.io.IOException;
import java.util.Map;

public class IncidentBaseFragment extends Fragment implements View.OnClickListener {
    private final IncidentViewModel viewModel = new IncidentViewModel();
    private final Handler handler = new Handler();
    private boolean recording, playing, ready = true;
    private FragmentIncidentBaseBinding binding;
    private long lastClickTime = 0;

    public IncidentBaseFragment() {
    }

    public static IncidentBaseFragment newInstance() {
        return new IncidentBaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIncidentBaseBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.editTextIncidentType.setOnClickListener(this);
        binding.lottieAnimationView.setOnClickListener(this);
        binding.imageViewMicPlayPause.setOnClickListener(this);
        binding.imageViewDelete.setOnClickListener(this);
        binding.buttonNext.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.edit_text_incident_type) {
            showMenu(binding.editTextIncidentType, R.menu.incident_menu);
        } else if (id == R.id.image_view_mic_play_pause) {
            if (checkRecorderPermission(requireContext())) {
                if (ready) {
                    startRecording();
                } else {
                    startPlaying();
                }
                binding.textViewTimer.setVisibility(View.VISIBLE);
                binding.imageViewDelete.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_pause));
                binding.imageViewMicPlayPause.setVisibility(View.GONE);
                binding.lottieAnimationView.setVisibility(View.VISIBLE);
                binding.lottieAnimationView.playAnimation();
            } else {
                requestRecordPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
            }

        } else if (id == R.id.lottie_animation_view) {
            handler.removeCallbacks(runnable);
            viewModel.setPosition(0);
            binding.lottieAnimationView.pauseAnimation();
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.imageViewMicPlayPause.setVisibility(View.VISIBLE);
            binding.imageViewMicPlayPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_play));
            binding.imageViewDelete.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_1));
            if (recording) {
                stopRecording();
            } else if (playing) {
                stopPlaying();
            }
        } else if (id == R.id.image_view_delete) {
            viewModel.setPosition(0);
            binding.lottieAnimationView.pauseAnimation();
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.imageViewMicPlayPause.setVisibility(View.VISIBLE);
            binding.imageViewMicPlayPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_play));
            binding.imageViewDelete.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_1));
            if (recording) {
                stopRecording();
            } else if (playing) {
                stopPlaying();
            } else {
                resetRecorder();
            }
        } else if (id == R.id.button_next) {
            if (viewModel.getIncidentType() == null || viewModel.getIncidentType().isEmpty()) {
                binding.editTextIncidentType.setError(getString(R.string.choose_incident_type));
                binding.editTextIncidentType.requestFocus();
                warning(requireContext(), R.string.choose_incident_type).show();
            } else {
                if ((viewModel.getDescription() == null || viewModel.getDescription().isEmpty()) &&
                        viewModel.getMediaRecorder() == null) {
                    binding.editTextDescription.setError(getString(R.string.choose_incident_type));
                    binding.editTextDescription.requestFocus();
                    warning(requireContext(), R.string.field_incident_description).show();
                } else {
                    nextPage();
                }
            }
        }
    }

    private void nextPage() {
//TODO
    }

    private void resetRecorder() {
        //TODO delete current file;
        if (SystemClock.elapsedRealtime() - lastClickTime < 2000) {
            viewModel.resetTime();
            viewModel.resetRecorder();
            binding.audioRecordView.recreate();
            binding.imageViewDelete.setVisibility(View.GONE);
            binding.textViewTimer.setVisibility(View.GONE);
            binding.imageViewMicPlayPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_recorder));
            ready = true;
        }
        warning(requireContext(), getString(R.string.delete_by_press_again)).show();
        lastClickTime = SystemClock.elapsedRealtime();
    }

    private void stopRecording() {
        recording = false;
        viewModel.stopRecorder();
    }

    private void stopPlaying() {
        viewModel.stopPlaying();
        viewModel.resetTime();
        playing = false;
    }

    private void startPlaying() {
        setupMediaPlayer();
        binding.lottieAnimationView.setAnimation(R.raw.player_animation_1);
    }

    private void startRecording() {
        setupMediaRecorder();
        binding.imageViewDelete.setVisibility(View.VISIBLE);
        binding.lottieAnimationView.setAnimation(R.raw.recorder_animation);
    }

    private void setupMediaPlayer() {
        binding.audioRecordView.recreate();
        handler.removeCallbacks(runnable);
        playing = true;
        try {
            viewModel.preparePlayer(requireContext());
            viewModel.setStartTime(SystemClock.uptimeMillis());
            handler.postDelayed(runnable, 200);
        } catch (IOException e) {
            playing = false;
            error(requireContext(), e.toString()).show();
            binding.lottieAnimationView.pauseAnimation();
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.imageViewDelete.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_1));
            binding.imageViewMicPlayPause.setVisibility(View.VISIBLE);
        }
    }

    private void setupMediaRecorder() {
        binding.audioRecordView.recreate();
        handler.removeCallbacks(runnable);
        ready = false;
        recording = true;
        try {
            viewModel.prepareRecorder(requireContext());
            viewModel.setStartTime(SystemClock.uptimeMillis());
            handler.postDelayed(runnable, 200);
        } catch (IOException e) {
            error(requireContext(), e.toString()).show();
            recording = false;
            ready = true;
            viewModel.stopRecorder();
            binding.lottieAnimationView.pauseAnimation();
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.imageViewDelete.setVisibility(View.GONE);
            binding.imageViewMicPlayPause.setVisibility(View.VISIBLE);
        }
    }

    private final Runnable runnable = new Runnable() {
        @SuppressLint("DefaultLocale")
        public void run() {
            if (recording) {
                viewModel.setAmplitude(viewModel.getMaxAmplitudeRecorder());
                binding.audioRecordView.update(viewModel.getAmplitudes(viewModel.getAmplitudes().size() - 1));
            } else if (playing) {
                if (viewModel.getPosition() < viewModel.getAmplitudes().size()) {
                    binding.audioRecordView.update(viewModel.getAmplitudes(viewModel.getPosition()));
                    viewModel.setPosition(viewModel.getPosition() + 1);
                }
                if (viewModel.getCurrentPosition() == viewModel.getDuration()) {
                    handler.removeCallbacks(runnable);
                    binding.lottieAnimationView.setVisibility(View.GONE);
                    binding.imageViewMicPlayPause.setVisibility(View.VISIBLE);
                    binding.imageViewMicPlayPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_play));
                    binding.imageViewDelete.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_1));
                    binding.audioRecordView.recreate();
                    viewModel.setPosition(0);
                    viewModel.stopPlaying();
                    viewModel.setStartTime(SystemClock.uptimeMillis());
                    playing = false;
                }
            }
            viewModel.setLength();
            if (recording || playing)
                handler.postDelayed(this, 200);
        }
    };
    private final ActivityResultLauncher<String> requestRecordPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    success(requireContext(), getString(R.string.voice_recorder_permission_granted)).show();
                } else {
                    error(requireContext(), getString(R.string.voice_recorder_permission_unavailable)).show();
                }
            });

    private final ActivityResultLauncher<String[]> requestStoragePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                    new ActivityResultCallback<Map<String, Boolean>>() {
                        @Override
                        public void onActivityResult(Map<String, Boolean> result) {
                            Log.e("here", "onActivityResult");
                            Log.e("here", result.toString());
                        }
                    });

    private void showMenu(View v, @MenuRes int menuRes) {
        final PopupMenu popup = new PopupMenu(requireActivity(), v, Gravity.TOP);
        // Inflating the Popup using xml file
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        // There is no public API to make icons show on menus.
        // IF you need the icons to show this works however it's discouraged to rely on library only
        // APIs since they might disappear in future versions.
        if (popup.getMenu() instanceof MenuBuilder) {
            final MenuBuilder menuBuilder = (MenuBuilder) popup.getMenu();
            //noinspection RestrictedApi
            menuBuilder.setOptionalIconsVisible(true);
            //noinspection RestrictedApi
            for (MenuItem item : menuBuilder.getVisibleItems()) {
               final int iconMarginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        R.dimen.low_dp, getResources().getDisplayMetrics());
                if (item.getIcon() != null) {
                    item.setIcon(new InsetDrawable(item.getIcon(), iconMarginPx, 0, iconMarginPx, 0));
                }
            }
        }
        popup.setOnMenuItemClickListener(menuItem -> {
            binding.editTextIncidentType.setText(menuItem.getTitle());
            return true;
        });
        popup.show();
    }
}