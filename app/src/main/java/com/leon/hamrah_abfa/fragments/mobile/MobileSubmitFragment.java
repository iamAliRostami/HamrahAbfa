package com.leon.hamrah_abfa.fragments.mobile;

import static com.leon.hamrah_abfa.helpers.Constants.MOBILE_REGEX;
import static com.leon.hamrah_abfa.helpers.Constants.VERIFICATION_FRAGMENT;
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
import com.leon.hamrah_abfa.databinding.FragmentMobileSubmitBinding;
import com.leon.hamrah_abfa.requests.mobile_account.AskVerificationCodeRequest;

public class MobileSubmitFragment extends Fragment implements View.OnClickListener {
    private FragmentMobileSubmitBinding binding;
    private ICallback callback;

    public MobileSubmitFragment() {
    }

    public static MobileSubmitFragment newInstance() {
        return new MobileSubmitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMobileSubmitBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.imageViewSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.image_view_submit) {
            if (!mobileValidation()) {
                request();
            }
        }
    }

    private boolean mobileValidation() {
        boolean cancel = false;
        String error = null;
        if (callback.getViewModel().getMobile() == null ||
                callback.getViewModel().getMobile().isEmpty()) {
            cancel = true;
            error = getString(R.string.enter_mobile);
        } else if (!MOBILE_REGEX.matcher(callback.getViewModel().getMobile()).matches()) {
            cancel = true;
            error = getString(R.string.mobile_error);
        }
        if (cancel) {
            warning(requireContext(), error).show();
            binding.editTextMobile.setError(error);
            binding.editTextMobile.requestFocus();
        }
        return cancel;
    }

    private void request() {
        boolean isOnline = new AskVerificationCodeRequest(getContext(), callback.getViewModel(),
                new AskVerificationCodeRequest.ICallback() {
                    @Override
                    public void succeed(String id, long remainedSeconds) {
                        callback.editPreLoginViewModel(id, remainedSeconds);
                        callback.displayView(VERIFICATION_FRAGMENT);
                    }

                    @Override
                    public void changeUI(boolean done) {
                        progressStatus(done);
                    }
                }).request();
        progressStatus(!isOnline);
    }

    private void progressStatus(boolean hide) {
        //TODO
        if (hide) {
            binding.imageViewSubmit.setVisibility(View.VISIBLE);
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.lottieAnimationView.pauseAnimation();
        } else {
            binding.imageViewSubmit.setVisibility(View.GONE);
            binding.lottieAnimationView.playAnimation();
            binding.lottieAnimationView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }


    public interface ICallback {
        void displayView(int position);

        PreLoginViewModel getViewModel();

        void editPreLoginViewModel(String id, long remainedSeconds);
    }
}