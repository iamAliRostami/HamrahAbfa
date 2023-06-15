package com.leon.hamrah_abfa.fragments.dialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.airbnb.lottie.LottieDrawable;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentWaitingBinding;

public class WaitingFragment extends DialogFragment {
    private FragmentWaitingBinding binding;
    public WaitingFragment() {
    }

    public static WaitingFragment newInstance() {
        return new WaitingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  FragmentWaitingBinding.inflate(inflater, container, false);
//        binding.lottieWaiting.setRepeatCount(LottieDrawable.INFINITE);
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        if (getDialog() != null) {
            final WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes(params);
        }
        super.onResume();
    }
}