package com.leon.hamrah_abfa.fragments.dialog;

import static com.leon.hamrah_abfa.enums.BundleEnum.CANCELABLE;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.databinding.FragmentWaitingBinding;
import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;

public class WaitingFragment extends DialogFragment {
    private boolean cancelable = false;
    private ICancelListener cancelListener;

    public WaitingFragment() {
    }

    public WaitingFragment(ICancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public static WaitingFragment newInstance() {
        WaitingFragment waitingFragment = new WaitingFragment();
        waitingFragment.setCancelable(false);
        return waitingFragment;
    }

    public static WaitingFragment newInstance(ICancelListener cancelListener) {
        WaitingFragment fragment = new WaitingFragment(cancelListener);
        Bundle args = new Bundle();
        args.putBoolean(CANCELABLE.getValue(), true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cancelable = getArguments().getBoolean(CANCELABLE.getValue());
            getArguments().clear();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWaitingBinding binding = FragmentWaitingBinding.inflate(inflater, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return binding.getRoot();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (cancelable) {
            HttpClientWrapper.cancel = true;
            if (HttpClientWrapper.call != null) {
                HttpClientWrapper.call.cancel();
                HttpClientWrapper.call = null;
            }
            cancelListener.cancel();
        }
    }

    @Override
    public void onResume() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            final WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes(params);
        }
        super.onResume();
    }

    public interface ICancelListener {
        void cancel();
    }
}