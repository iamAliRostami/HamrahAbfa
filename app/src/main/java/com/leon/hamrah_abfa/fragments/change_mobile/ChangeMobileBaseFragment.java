package com.leon.hamrah_abfa.fragments.change_mobile;

import static com.leon.hamrah_abfa.helpers.Constants.CHANGE_MOBILE_VERIFICATION_CODE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.MOBILE_REGEX;
import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentChangeMobileBaseBinding;

public class ChangeMobileBaseFragment extends Fragment implements View.OnClickListener {
    private FragmentChangeMobileBaseBinding binding;
    private ICallback callback;

    public ChangeMobileBaseFragment() {
    }

    public static ChangeMobileBaseFragment newInstance() {
        return new ChangeMobileBaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeMobileBaseBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            if (mobileValidation()) {
                callback.displayView(CHANGE_MOBILE_VERIFICATION_CODE_FRAGMENT);
            }
        }
    }

    private boolean mobileValidation() {
        boolean cancel = false;
        if (callback.getViewModel().getNewMobile() == null || callback.getViewModel().getNewMobile().isEmpty()) {
            warning(requireContext(), getString(R.string.enter_mobile)).show();
            binding.editTextNewMobile.setError(getString(R.string.enter_mobile));
            binding.editTextNewMobile.requestFocus();
            cancel = true;
        } else if (!MOBILE_REGEX.matcher(callback.getViewModel().getNewMobile()).matches()) {
            warning(requireContext(), getString(R.string.mobile_error)).show();
            binding.editTextNewMobile.setError(getString(R.string.mobile_error));
            binding.editTextNewMobile.requestFocus();
            cancel = true;
        }
        return !cancel;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        void displayView(int position);

        ChangeMobileViewModel getViewModel();
    }
}