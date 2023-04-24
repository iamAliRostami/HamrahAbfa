package com.leon.hamrah_abfa.fragments.ui.incident;

import static com.leon.hamrah_abfa.utils.PermissionManager.checkRecorderPermission;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;

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
import com.leon.hamrah_abfa.databinding.FragmentIncidentBinding;
import com.leon.toast.RTLToast;

import java.io.IOException;
import java.util.Map;

public class IncidentFragment extends Fragment implements View.OnClickListener {
    private final IncidentViewModel viewModel = new IncidentViewModel();
    private FragmentIncidentBinding binding;
    private boolean recording, playing, ready = true;
    private long lastClickTime = 0;

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
        binding.setViewModel(viewModel);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.textViewIncidentType.setOnClickListener(this);
        binding.lottieAnimationView.setOnClickListener(this);
        binding.imageViewMicPlayPause.setOnClickListener(this);
        binding.imageViewDelete.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.text_view_incident_type) {
            showMenu(binding.textViewIncidentType, R.menu.incident_menu);
        } else if (id == R.id.image_view_mic_play_pause) {
            if (checkRecorderPermission(requireContext())) {
                binding.imageViewMicPlayPause.setVisibility(View.GONE);
                binding.imageViewDelete.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.pause));
                binding.lottieAnimationView.setVisibility(View.VISIBLE);
                if (ready) {
                    setupMediaRecorder();
                    binding.imageViewDelete.setVisibility(View.VISIBLE);
                    binding.lottieAnimationView.setAnimation(R.raw.recorder_animation);
                } else {
                    setupMediaPlayer();
                    binding.lottieAnimationView.setAnimation(R.raw.player_animation_1);
                }
                binding.lottieAnimationView.playAnimation();
            } else {
                requestRecordPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
            }

        }
        else if (id == R.id.lottie_animation_view) {
            handler.removeCallbacks(runnable);
            binding.lottieAnimationView.pauseAnimation();
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.imageViewMicPlayPause.setVisibility(View.VISIBLE);
            binding.imageViewMicPlayPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.play));
            viewModel.setPosition(0);
            binding.imageViewDelete.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.delete));
            if (recording) {
                recording = false;
                viewModel.stopRecorder();
            } else if (playing) {
                //TODO
                viewModel.setStartTime(SystemClock.uptimeMillis());
                viewModel.setLength();
                playing = false;
                viewModel.stopPlaying();
            }
        } else if (id == R.id.image_view_delete) {

            viewModel.setPosition(0);
            binding.lottieAnimationView.pauseAnimation();
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.imageViewMicPlayPause.setVisibility(View.VISIBLE);
            binding.imageViewMicPlayPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.play));
            binding.imageViewDelete.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.delete));

            if (recording) {
                recording = false;
                viewModel.stopRecorder();
            } else if (playing) {
                playing = false;
                viewModel.setStartTime(SystemClock.uptimeMillis());
                viewModel.setLength();
                viewModel.stopPlaying();
            } else {
                //TODO delete current file;
                if (SystemClock.elapsedRealtime() - lastClickTime < 2000) {
                    //viewModel.setR
                    viewModel.setStartTime(SystemClock.uptimeMillis());
                    viewModel.setLength();
                    binding.audioRecordView.recreate();
                    binding.imageViewDelete.setVisibility(View.GONE);
                    binding.imageViewMicPlayPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.mic_blue));
                    ready = true;
                }
                RTLToast.info(requireContext(), getString(R.string.delete_by_press_again)).show();
                lastClickTime = SystemClock.elapsedRealtime();
            }
        }

    }

    private void setupMediaPlayer() {
        binding.audioRecordView.recreate();
        handler.removeCallbacks(runnable);
        try {
            playing = true;
            viewModel.preparePlayer(requireContext());
            viewModel.setStartTime(SystemClock.uptimeMillis());
            handler.postDelayed(runnable, 200);
        } catch (IOException e) {
            playing = false;
            error(requireContext(), e.toString()).show();
            binding.lottieAnimationView.pauseAnimation();
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.imageViewDelete.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.delete));
            binding.imageViewMicPlayPause.setVisibility(View.VISIBLE);
        }
    }

    private void setupMediaRecorder() {
        binding.audioRecordView.recreate();
        handler.removeCallbacks(runnable);
        ready = false;
        recording = true;
        try {
            viewModel.setPosition(0);
            viewModel.prepareRecorder(requireContext());
            viewModel.setStartTime(SystemClock.uptimeMillis());
            handler.postDelayed(runnable, 200);
        } catch (IOException e) {
            recording = false;
            ready = true;
            error(requireContext(), e.toString()).show();
            viewModel.stopRecorder();

            binding.lottieAnimationView.pauseAnimation();
            binding.lottieAnimationView.setVisibility(View.GONE);

            binding.imageViewDelete.setVisibility(View.GONE);

            binding.imageViewMicPlayPause.setVisibility(View.VISIBLE);
        }
    }

    private final Handler handler = new Handler();
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
                    playing = false;
                    handler.removeCallbacks(runnable);
                    binding.lottieAnimationView.setVisibility(View.GONE);
                    binding.imageViewMicPlayPause.setVisibility(View.VISIBLE);
                    binding.imageViewMicPlayPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.play));
                    binding.imageViewDelete.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.delete));
                    //TODO
                    binding.audioRecordView.recreate();
                    viewModel.setPosition(0);
                    viewModel.stopPlaying();
                    viewModel.setStartTime(SystemClock.uptimeMillis());
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
}