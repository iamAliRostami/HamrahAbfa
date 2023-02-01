package com.leon.hamrah_abfa.fragments.bottom_sheets;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.databinding.FragmentVoiceRecorderBinding;

import java.io.IOException;

public class VoiceRecorderFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private FragmentVoiceRecorderBinding binding;
    private MediaRecorder mediaRecorder;

    private MediaPlayer mediaPlayer;
    private boolean recording;

    public VoiceRecorderFragment() {
    }

    public static VoiceRecorderFragment newInstance() {
        return new VoiceRecorderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVoiceRecorderBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
//        binding.audioRecordView.recreate();
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(requireContext().getExternalFilesDir(null).getAbsolutePath() +
                "/voice.ogg");
        binding.imageViewMic.setOnClickListener(v -> setupMediaRecorder());
    }

    private void setupMediaRecorder() {
        binding.audioRecordView.recreate();
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
            mediaRecorder.stop();
        }
        final Handler myHandler = new Handler();
        Runnable UpdateSongTime = new Runnable() {
            public void run() {
                binding.audioRecordView.update(mediaRecorder.getMaxAmplitude());
                myHandler.postDelayed(this, 200);
            }
        };
        myHandler.postDelayed(UpdateSongTime, 200);

    }

    @Override
    public void onClick(View v) {

    }
}