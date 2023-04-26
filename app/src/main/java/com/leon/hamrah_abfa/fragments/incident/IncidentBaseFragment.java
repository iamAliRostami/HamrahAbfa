package com.leon.hamrah_abfa.fragments.incident;

import static com.leon.hamrah_abfa.utils.PermissionManager.checkRecorderPermission;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;
import static com.leon.toast.RTLToast.warning;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentIncidentBaseBinding;

import java.io.IOException;

public class IncidentBaseFragment extends Fragment implements View.OnClickListener {
    private FragmentIncidentBaseBinding binding;
    private boolean recording, playing, ready = true;
    private final Handler handler = new Handler();
    private ICallback incidentActivity;
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
        binding.setViewModel(incidentActivity.getIncidentViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.editTextIncidentType.setOnClickListener(this);
        binding.lottieAnimationView.setOnClickListener(this);
        binding.imageViewMicPlayPause.setOnClickListener(this);
        binding.imageViewDelete.setOnClickListener(this);
        binding.buttonNext.setOnClickListener(this);
        initializeRecorder();
    }

    private void initializeRecorder() {
        if (incidentActivity.getIncidentViewModel().getMediaRecorder() != null) {
            binding.textViewTimer.setVisibility(View.VISIBLE);
            binding.imageViewDelete.setVisibility(View.VISIBLE);
            binding.imageViewMicPlayPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_play));
            ready = false;
        }
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
            incidentActivity.getIncidentViewModel().setPosition(0);
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
            incidentActivity.getIncidentViewModel().setPosition(0);
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
            if (incidentActivity.getIncidentViewModel().getIncidentType() == null || incidentActivity.getIncidentViewModel().getIncidentType().isEmpty()) {
                binding.editTextIncidentType.setError(getString(R.string.choose_incident_type));
                binding.editTextIncidentType.requestFocus();
                warning(requireContext(), R.string.choose_incident_type).show();
            } else {
                if ((incidentActivity.getIncidentViewModel().getDescription() == null || incidentActivity.getIncidentViewModel().getDescription().isEmpty()) &&
                        incidentActivity.getIncidentViewModel().getMediaRecorder() == null) {
                    binding.editTextDescription.setError(getString(R.string.choose_incident_type));
                    binding.editTextDescription.requestFocus();
                    warning(requireContext(), R.string.field_incident_description).show();
                } else {
                    incidentActivity.nextPage();
                }
            }
        }
    }

    private void resetRecorder() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 2000) {
            incidentActivity.getIncidentViewModel().resetTime();
            incidentActivity.getIncidentViewModel().resetRecorder();
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
        incidentActivity.getIncidentViewModel().stopRecorder();
    }

    private void stopPlaying() {
        incidentActivity.getIncidentViewModel().stopPlaying();
        incidentActivity.getIncidentViewModel().resetTime();
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
            incidentActivity.getIncidentViewModel().preparePlayer(requireContext());
            incidentActivity.getIncidentViewModel().setStartTime(SystemClock.uptimeMillis());
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
            incidentActivity.getIncidentViewModel().prepareRecorder(requireContext());
            incidentActivity.getIncidentViewModel().setStartTime(SystemClock.uptimeMillis());
            handler.postDelayed(runnable, 200);
        } catch (IOException e) {
            error(requireContext(), e.toString()).show();
            recording = false;
            ready = true;
            incidentActivity.getIncidentViewModel().stopRecorder();
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
                incidentActivity.getIncidentViewModel().setAmplitude(incidentActivity.getIncidentViewModel().getMaxAmplitudeRecorder());
                binding.audioRecordView.update(incidentActivity.getIncidentViewModel().getAmplitudes(incidentActivity.getIncidentViewModel().getAmplitudes().size() - 1));
            } else if (playing) {
                if (incidentActivity.getIncidentViewModel().getPosition() < incidentActivity.getIncidentViewModel().getAmplitudes().size()) {
                    binding.audioRecordView.update(incidentActivity.getIncidentViewModel().getAmplitudes(incidentActivity.getIncidentViewModel().getPosition()));
                    incidentActivity.getIncidentViewModel().setPosition(incidentActivity.getIncidentViewModel().getPosition() + 1);
                }
                if (incidentActivity.getIncidentViewModel().getCurrentPosition() == incidentActivity.getIncidentViewModel().getDuration()) {
                    handler.removeCallbacks(runnable);
                    binding.lottieAnimationView.setVisibility(View.GONE);
                    binding.imageViewMicPlayPause.setVisibility(View.VISIBLE);
                    binding.imageViewMicPlayPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_play));
                    binding.imageViewDelete.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_1));
                    binding.audioRecordView.recreate();
                    incidentActivity.getIncidentViewModel().setPosition(0);
                    incidentActivity.getIncidentViewModel().stopPlaying();
                    incidentActivity.getIncidentViewModel().setStartTime(SystemClock.uptimeMillis());
                    playing = false;
                }
            }
            incidentActivity.getIncidentViewModel().setLength();
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

    private void showMenu(View v, @MenuRes int menuRes) {
        final PopupMenu popup = new PopupMenu(requireActivity(), v, Gravity.TOP);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) incidentActivity = (ICallback) context;
    }

    public interface ICallback {
        IncidentViewModel getIncidentViewModel();

        void nextPage();
    }
}