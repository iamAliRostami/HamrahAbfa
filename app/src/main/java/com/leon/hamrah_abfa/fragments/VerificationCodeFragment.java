package com.leon.hamrah_abfa.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentVerificationCodeBinding;

public class VerificationCodeFragment extends Fragment implements View.OnClickListener {
    private FragmentVerificationCodeBinding binding;

    public VerificationCodeFragment() {
    }

    public static VerificationCodeFragment newInstance() {
        return new VerificationCodeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerificationCodeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        startCounter();
        binding.textViewTryAgain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.text_view_try_again) {
            binding.textViewCounter.setVisibility(View.VISIBLE);
            binding.textViewTryAgain.setVisibility(View.GONE);
            startCounter();
        }
    }

    private void startCounter() {
        new CountDownTimer(10000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                binding.textViewCounter.setText(millisUntilFinished / 1000 + " : " + millisUntilFinished / (1000 * 60));
            }

            public void onFinish() {
                binding.textViewCounter.setVisibility(View.GONE);
                binding.textViewTryAgain.setVisibility(View.VISIBLE);
            }

        }.start();
    }
}