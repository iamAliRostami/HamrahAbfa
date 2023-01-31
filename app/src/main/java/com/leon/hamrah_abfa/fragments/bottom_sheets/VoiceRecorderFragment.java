package com.leon.hamrah_abfa.fragments.bottom_sheets;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.databinding.FragmentVoiceRecorderBinding;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VoiceRecorderFragment extends BottomSheetDialogFragment {
    private FragmentVoiceRecorderBinding binding;

    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;

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
        binding.audioRecordView.recreate();
        binding.imageViewMic.setOnClickListener(v -> setupMediaRecorder());
    }

    private void setupMediaRecorder() {
//        binding.audioRecordView.recreate();
        mediaRecorder = new MediaRecorder();
//        binding.visualizer.setPlayer(mediaPlayer.getAudioSessionId());
//        binding.audioRecordView.setMinimumHeight(100);
//        binding.audioRecordView.update(8000);
//        binding.audioRecordView.update(3);
//        binding.audioRecordView.update(2);
//        binding.audioRecordView.update(1);
//        binding.audioRecordView.update(1);
//        binding.audioRecordView.update(1);
//        binding.audioRecordView.update(8000);
//        binding.audioRecordView.update(8000);
//        binding.audioRecordView.update(mediaRecorder.getMaxAmplitude());
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(requireContext().getExternalFilesDir(null).getAbsolutePath() +
                "/voice.ogg");
//        Log.e("address",requireContext().getExternalFilesDir(null).getAbsolutePath() +
//                "/voice.ogg");
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
            mediaRecorder.stop();
        }

//        binding.audioRecordView.update(mediaRecorder.getMaxAmplitude());
//        binding.audioRecordView.update(mediaRecorder.getMaxAmplitude());
//        binding.audioRecordView.update(mediaRecorder.getMaxAmplitude());

        final Handler myHandler = new Handler();
        Runnable UpdateSongTime = new Runnable() {
            public void run() {
                binding.audioRecordView.update(mediaRecorder.getMaxAmplitude());
                myHandler.postDelayed(this, 100);
            }
        };
        myHandler.postDelayed(UpdateSongTime, 100);

    }
}