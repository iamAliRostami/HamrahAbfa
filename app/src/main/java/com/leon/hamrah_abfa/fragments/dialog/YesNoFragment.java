package com.leon.hamrah_abfa.fragments.dialog;

import static com.leon.hamrah_abfa.enums.BundleEnum.DRAWABLE;
import static com.leon.hamrah_abfa.enums.BundleEnum.NO;
import static com.leon.hamrah_abfa.enums.BundleEnum.QUESTION;
import static com.leon.hamrah_abfa.enums.BundleEnum.TITLE;
import static com.leon.hamrah_abfa.enums.BundleEnum.YEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentYesNoBinding;

public class YesNoFragment extends DialogFragment implements View.OnClickListener {
    private FragmentYesNoBinding binding;
    private YesNoViewModel viewModel;
    private IClickListener listener;

    public YesNoFragment() {
    }

    public YesNoFragment(IClickListener listener) {
        this.listener = listener;
    }

    public static YesNoFragment newInstance(int drawable, String title, String question,
                                            String yes, String no, IClickListener listener) {
        final YesNoFragment fragment = new YesNoFragment(listener);
        fragment.setCancelable(false);
        Bundle args = new Bundle();
        args.putInt(DRAWABLE.getValue(), drawable);
        args.putString(TITLE.getValue(), title);
        args.putString(QUESTION.getValue(), question);
        args.putString(YEY.getValue(), yes);
        args.putString(NO.getValue(), no);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel = new YesNoViewModel(getArguments().getString(TITLE.getValue()),
                    getArguments().getString(QUESTION.getValue()), getArguments().getString(YEY.getValue()),
                    getArguments().getString(NO.getValue()), getArguments().getInt(DRAWABLE.getValue()));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentYesNoBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonNo.setOnClickListener(this);
        binding.buttonYes.setOnClickListener(this);
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
        if (id == R.id.button_no) {
            listener.no(this);
        } else if (id == R.id.button_yes) {
            listener.yes(this);
        }
    }

    public interface IClickListener {
        void yes(DialogFragment dialogFragment);

        void no(DialogFragment dialogFragment);
    }
}