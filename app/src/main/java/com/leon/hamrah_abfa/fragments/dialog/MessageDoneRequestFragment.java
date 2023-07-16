package com.leon.hamrah_abfa.fragments.dialog;

import static com.leon.hamrah_abfa.enums.BundleEnum.TEXT_BUTTON;
import static com.leon.hamrah_abfa.enums.BundleEnum.TRACK_NUMBER;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentMessageDoneRequestBinding;

public class MessageDoneRequestFragment extends DialogFragment implements View.OnClickListener {
    private FragmentMessageDoneRequestBinding binding;
    private String trackNumber;
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
        args.putString(TRACK_NUMBER.getValue(), trackNumber);
        args.putString(TEXT_BUTTON.getValue(), textButton);
        MessageDoneRequestFragment fragment = new MessageDoneRequestFragment(listener);
        fragment.setArguments(args);
        fragment.setCancelable(false);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trackNumber = getArguments().getString(TRACK_NUMBER.getValue());
            textButton = getArguments().getString(TEXT_BUTTON.getValue());
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
        binding.textViewTin.setText(trackNumber);
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

        void no(DialogFragment dialogFragment);
    }
}