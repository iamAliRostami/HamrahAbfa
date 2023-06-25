package com.leon.hamrah_abfa.fragments.dialog;

import static com.leon.hamrah_abfa.enums.BundleEnum.TRACK_NUMBER;
import static com.leon.toast.RTLToast.success;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.leon.hamrah_abfa.databinding.FragmentPayBillSucceedPaymentBinding;

public class PayBillSucceedFragment extends DialogFragment implements View.OnClickListener {
    private FragmentPayBillSucceedPaymentBinding binding;
    private String trackNumber;
    private IClickListener listener;

    public PayBillSucceedFragment() {
    }

    public PayBillSucceedFragment(IClickListener listener) {
        this.listener = listener;
    }

    public static PayBillSucceedFragment newInstance(String trackNumber, IClickListener listener) {
        final PayBillSucceedFragment fragment = new PayBillSucceedFragment(listener);
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
        binding = FragmentPayBillSucceedPaymentBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.textViewTrackNumber.setText(trackNumber);
        binding.buttonReturn.setOnClickListener(this);
        binding.linearLayoutCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_return) {
            listener.yes(this);
        } else if (id == R.id.linear_layout_copy) {
            final ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            final ClipData clip = ClipData.newPlainText(getString(R.string.track_number), trackNumber);
            clipboard.setPrimaryClip(clip);
            success(requireContext(), R.string.track_number_is_copied).show();
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