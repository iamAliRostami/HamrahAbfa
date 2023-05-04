package com.leon.hamrah_abfa.fragments.counter;

import static com.leon.hamrah_abfa.helpers.Constants.COUNTER_VERIFICATION_CODE_FRAGMENT;
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
import com.leon.hamrah_abfa.databinding.FragmentCounterBaseBinding;

public class CounterBaseFragment extends Fragment implements View.OnClickListener {
    private ICallback callback;
    private FragmentCounterBaseBinding binding;

    public CounterBaseFragment() {
    }

    public static CounterBaseFragment newInstance() {
        return new CounterBaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCounterBaseBinding.inflate(inflater, container, false);
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
                if (callback.getViewModel().getCounterNumber() == null ||
                        callback.getViewModel().getCounterNumber().isEmpty()) {
                    warning(requireContext(), getString(R.string.enter_counter_number)).show();
                    binding.editTextCounterNumber.setError(getString(R.string.enter_counter_number));
                    binding.editTextCounterNumber.requestFocus();
                } else {

                    callback.displayView(COUNTER_VERIFICATION_CODE_FRAGMENT);
                }
            }
        }
    }

    private boolean mobileValidation() {
        boolean cancel = false;
        if (callback.getViewModel().getMobile().isEmpty()) {
            warning(requireContext(), getString(R.string.enter_mobile)).show();
            binding.editTextMobile.setError(getString(R.string.enter_mobile));
            binding.editTextMobile.requestFocus();
            cancel = true;
        } else if (!MOBILE_REGEX.matcher(callback.getViewModel().getMobile()).matches()) {
            warning(requireContext(), getString(R.string.mobile_error)).show();
            binding.editTextMobile.setError(getString(R.string.mobile_error));
            binding.editTextMobile.requestFocus();
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
        CounterViewModel getViewModel();

        void displayView(int position);
    }
}