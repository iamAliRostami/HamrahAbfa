package com.app.leon.moshtarak.fragments.counter;

import static com.app.leon.moshtarak.helpers.Constants.COUNTER_VERIFICATION_CODE_FRAGMENT;
import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.databinding.FragmentCounterBaseBinding;
import com.app.leon.moshtarak.helpers.Constants;
import com.app.leon.moshtarak.requests.AskVerificationCodeRequest;

public class CounterBaseFragment extends Fragment implements View.OnClickListener {
    private FragmentCounterBaseBinding binding;
    private ICallback callback;

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
            if (checkInput() && mobileValidation()) {
                requestVerificationCode();
            }
        }
    }

    private boolean mobileValidation() {
        if (callback.getViewModel().getMobile() == null || callback.getViewModel().getMobile().isEmpty()) {
            warning(requireContext(), getString(R.string.enter_mobile)).show();
            binding.editTextMobile.setError(getString(R.string.enter_mobile));
            binding.editTextMobile.requestFocus();
            return false;
        } else if (!Constants.MOBILE_REGEX.matcher(callback.getViewModel().getMobile()).matches()) {
            warning(requireContext(), getString(R.string.mobile_error)).show();
            binding.editTextMobile.setError(getString(R.string.mobile_error));
            binding.editTextMobile.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkInput() {
        if (callback.getViewModel().getCounterClaim() == null ||
                callback.getViewModel().getCounterClaim().isEmpty()) {
            warning(requireContext(), getString(R.string.enter_counter_number)).show();
            binding.editTextCounterNumber.setError(getString(R.string.enter_counter_number));
            binding.editTextCounterNumber.requestFocus();
            return false;
        } else if (!(Integer.parseInt(callback.getViewModel().getCounterClaim()) > 0)) {
            warning(requireContext(), getString(R.string.enter_more_than_zero)).show();
            binding.editTextCounterNumber.setError(getString(R.string.enter_more_than_zero));
            binding.editTextCounterNumber.requestFocus();
            return false;
        }
        return true;
    }

    private void requestVerificationCode() {
        progressStatus(new AskVerificationCodeRequest(getContext(), callback.getViewModel(),
                new AskVerificationCodeRequest.ICallback() {
                    @Override
                    public void succeed(String id, long remainedSeconds) {
                        callback.editViewModel(id, remainedSeconds);
                        callback.displayView(COUNTER_VERIFICATION_CODE_FRAGMENT);
                    }

                    @Override
                    public void changeUI(boolean visible) {
                        progressStatus(visible);
                    }
                }).request());
    }

    private void progressStatus(boolean visible) {
        if (visible) {
            binding.buttonSubmit.setVisibility(View.GONE);
            binding.lottieAnimationView.playAnimation();
            binding.lottieAnimationView.setVisibility(View.VISIBLE);
        } else {
            binding.buttonSubmit.setVisibility(View.VISIBLE);
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.lottieAnimationView.pauseAnimation();
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        CounterViewModel getViewModel();

        void displayView(int position);

        void editViewModel(String id, long remainedSeconds);
    }
}