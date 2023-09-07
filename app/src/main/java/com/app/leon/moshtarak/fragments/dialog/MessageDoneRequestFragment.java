package com.app.leon.moshtarak.fragments.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.databinding.FragmentMessageDoneRequestBinding;
import com.app.leon.moshtarak.enums.BundleEnum;

public class MessageDoneRequestFragment extends DialogFragment implements View.OnClickListener {
    private FragmentMessageDoneRequestBinding binding;
    private String message;
    private String textButton;
    private IClickListener listener;

    public MessageDoneRequestFragment() {
    }

    public MessageDoneRequestFragment(IClickListener listener) {
        this.listener = listener;
    }

    public static MessageDoneRequestFragment newInstance(String trackNumber, String textButton,
                                                         IClickListener listener) {
        Bundle args = new Bundle();
        args.putString(BundleEnum.MESSAGE.getValue(), trackNumber);
        args.putString(BundleEnum.TEXT_BUTTON.getValue(), textButton);
        MessageDoneRequestFragment fragment = new MessageDoneRequestFragment(listener);
        fragment.setArguments(args);
        fragment.setCancelable(false);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(BundleEnum.MESSAGE.getValue());
            textButton = getArguments().getString(BundleEnum.TEXT_BUTTON.getValue());
            getArguments().clear();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessageDoneRequestBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.textViewTin.setText(message);
        binding.buttonReturn.setText(textButton);
        binding.buttonReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_return) {
            listener.yes(this);
        }
    }

    @Override
    public void onResume() {
        if (getDialog() != null) {
            final WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes(params);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        super.onResume();
    }


    public interface IClickListener {
        void yes(DialogFragment dialogFragment);
    }
}