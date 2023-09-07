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
import com.app.leon.moshtarak.databinding.FragmentInfoYesBinding;
import com.app.leon.moshtarak.enums.BundleEnum;

public class InfoYesFragment extends DialogFragment implements View.OnClickListener {
    private FragmentInfoYesBinding binding;
    private YesNoViewModel viewModel;
    private IClickListener listener;

    public InfoYesFragment() {
    }

    public InfoYesFragment(IClickListener listener) {
        this.listener = listener;
    }

    public static InfoYesFragment newInstance(int drawable, String title, String question,
                                              String yes, String no, IClickListener listener) {
        final Bundle args = new Bundle();
        args.putInt(BundleEnum.DRAWABLE.getValue(), drawable);
        args.putString(BundleEnum.TITLE.getValue(), title);
        args.putString(BundleEnum.QUESTION.getValue(), question);
        args.putString(BundleEnum.YEY.getValue(), yes);
        args.putString(BundleEnum.NO.getValue(), no);
        final InfoYesFragment fragment = new InfoYesFragment(listener);
        fragment.setCancelable(false);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel = new YesNoViewModel(getArguments().getString(BundleEnum.TITLE.getValue()),
                    getArguments().getString(BundleEnum.QUESTION.getValue()), getArguments().getString(BundleEnum.YEY.getValue()),
                    getArguments().getString(BundleEnum.NO.getValue()), getArguments().getInt(BundleEnum.DRAWABLE.getValue()));
            getArguments().clear();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoYesBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonYes.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            final WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes(params);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_yes) {
            listener.yes(this);
        }
    }

    public interface IClickListener {
        void yes(DialogFragment dialogFragment);
    }
}