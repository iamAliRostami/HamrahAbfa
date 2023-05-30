package com.leon.hamrah_abfa.fragments.contact_us;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.SystemClock;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;
import com.leon.hamrah_abfa.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ForbiddenViewModel extends BaseObservable {
    private String description;
    private String postalCode;
    private String preBillId;
    private String nextBillId;
    private String length;
    private long startTime;
    private long currentTime;
    private int position;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private final ArrayList<Integer> amplitudes = new ArrayList<>();

    public ForbiddenViewModel() {
        setStartTime(SystemClock.uptimeMillis());
        setLength();
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        notifyPropertyChanged(BR.postalCode);
    }

    @Bindable
    public String getPreBillId() {
        return preBillId;
    }

    public void setPreBillId(String preBillId) {
        this.preBillId = preBillId;
        notifyPropertyChanged(BR.preBillId);
    }

    @Bindable
    public String getNextBillId() {
        return nextBillId;
    }

    public void setNextBillId(String nextBillId) {
        this.nextBillId = nextBillId;
        notifyPropertyChanged(BR.nextBillId);
    }

    @Bindable
    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
        notifyPropertyChanged(BR.startTime);
    }

    public void resetTime() {
        setStartTime(SystemClock.uptimeMillis());
        setLength();
    }

    public void resetRecorder() {
        mediaRecorder = null;
    }

    @Bindable
    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
        notifyPropertyChanged(BR.currentTime);
    }

    @Bindable
    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
        notifyPropertyChanged(BR.length);
    }

    @SuppressLint("DefaultLocale")
    public void setLength() {
        setCurrentTime(SystemClock.uptimeMillis());
        final long length = getCurrentTime() - getStartTime();
        setLength(String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(length), TimeUnit.MILLISECONDS.toSeconds(length) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(length))));
    }

    public MediaRecorder getMediaRecorder() {
        return mediaRecorder;
    }

    public void prepareRecorder(Context context) throws IOException {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(context.getExternalFilesDir(null).getAbsolutePath() +
                context.getString(R.string.voice_file_name));
        mediaRecorder.prepare();
        mediaRecorder.start();
    }

    public void stopRecorder() {
        mediaRecorder.stop();
    }

    public int getMaxAmplitudeRecorder() {
        return mediaRecorder.getMaxAmplitude();
    }

    public void preparePlayer(Context context) throws IOException {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(context.getExternalFilesDir(null).getAbsolutePath() +
                context.getString(R.string.voice_file_name));
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    public void stopPlaying() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public ArrayList<Integer> getAmplitudes() {
        return amplitudes;
    }

    public int getAmplitudes(int position) {
        return amplitudes.get(position);
    }

    public void setAmplitude(int amplitude) {
        amplitudes.add(amplitude);
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}