package com.leon.hamrah_abfa.fragments.dialog;

import static com.leon.hamrah_abfa.enums.BundleEnum.TRACK_NUMBER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentRequestDoneBinding;

public class RequestDoneFragment extends DialogFragment implements View.OnClickListener {
    private FragmentRequestDoneBinding binding;
    private String trackNumber;

    public RequestDoneFragment() {
    }

    public static RequestDoneFragment newInstance(String trackNumber) {
        final RequestDoneFragment fragment = new RequestDoneFragment();
        final Bundle args = new Bundle();
        args.putString(TRACK_NUMBER.getValue(), trackNumber);
        fragment.setArguments(args);
        fragment.setCancelable(false);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trackNumber = getArguments().getString(TRACK_NUMBER.getValue());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRequestDoneBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.textViewTrackNumber.setText(trackNumber);
        binding.buttonReturn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        if (getDialog() != null) {
            final WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes(params);
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_return) {
            requireActivity().finish();
        }
    }
}