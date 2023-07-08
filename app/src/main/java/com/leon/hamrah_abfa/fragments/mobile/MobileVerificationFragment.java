package com.leon.hamrah_abfa.fragments.mobile;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.TOKEN;
import static com.leon.hamrah_abfa.helpers.Constants.SUBMIT_PHONE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentMobileVerificationBinding;
import com.leon.hamrah_abfa.utils.SMSReceiver;
import com.leon.hamrah_abfa.utils.mobile_submit.VerifyReceivedCodeRequest;

public class MobileVerificationFragment extends Fragment implements View.OnClickListener,
        TextWatcher, View.OnKeyListener, SMSReceiver.OTPReceiveListener {
    private SMSReceiver smsReceiver = new SMSReceiver();
    private FragmentMobileVerificationBinding binding;
    private ICallback callback;

    public MobileVerificationFragment() {
    }

    public static MobileVerificationFragment newInstance() {
        return new MobileVerificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMobileVerificationBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        startSMSListener();
        startCounter();
        binding.textViewMobile.setText(getString(R.string.mobile).concat(" ").concat(callback.getViewModel().getMobile()));
        binding.buttonSubmit.setOnClickListener(this);
        binding.imageViewEdit.setOnClickListener(this);
        binding.textViewTryAgain.setOnClickListener(this);
        binding.editText1.addTextChangedListener(this);
        binding.editText2.addTextChangedListener(this);
        binding.editText3.addTextChangedListener(this);
        binding.editText4.addTextChangedListener(this);
        binding.editText1.setOnKeyListener(this);
        binding.editText2.setOnKeyListener(this);
        binding.editText3.setOnKeyListener(this);
        binding.editText4.setOnKeyListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.text_view_try_again) {
            //TODO
            callback.displayView(SUBMIT_PHONE_FRAGMENT);
        } else if (id == R.id.image_view_edit) {
            callback.displayView(SUBMIT_PHONE_FRAGMENT);
        } else if (id == R.id.button_submit) {
            if (checkInputs()) {
                //TODO call new api
                String verificationCode = binding.editText1.getEditableText().toString() +
                        binding.editText2.getEditableText().toString() +
                        binding.editText3.getEditableText().toString() +
                        binding.editText4.getEditableText().toString();
                callback.getViewModel().setSubmitCode(verificationCode);
                request();
            }
        }
    }

    private void request() {
        new VerifyReceivedCodeRequest(getContext(), callback.getViewModel(),
                new VerifyReceivedCodeRequest.ICallback() {
                    @Override
                    public void succeed(String token, String failureMessage, boolean result) {
                        callback.editPreLoginViewModel(token, failureMessage, result);
//                        getInstance().getApplicationComponent().SharedPreferenceModel().putData(MOBILE.getValue(),
//                                callback.getViewModel().getMobile());
                        getInstance().getApplicationComponent().SharedPreferenceModel().putData(TOKEN.getValue(),
                                callback.getViewModel().getToken());
                        requireActivity().finish();
                    }

                    @Override
                    public void changeUI(boolean done) {
                        //TODO
                        if (done) {
                            binding.buttonSubmit.setVisibility(View.VISIBLE);
                            binding.lottieAnimationView.setVisibility(View.GONE);
                            binding.lottieAnimationView.pauseAnimation();
                        } else {
                            binding.buttonSubmit.setVisibility(View.GONE);
                            binding.lottieAnimationView.playAnimation();
                            binding.lottieAnimationView.setVisibility(View.VISIBLE);
                        }
                    }
                }
        ).request();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0)
            if (s == binding.editText1.getEditableText())
                binding.editText2.requestFocus();
            else if (s == binding.editText2.getEditableText())
                binding.editText3.requestFocus();
            else if (s == binding.editText3.getEditableText())
                binding.editText4.requestFocus();
            else if (s == binding.editText4.getEditableText()) {
                final InputMethodManager inputManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                binding.buttonSubmit.requestFocus();
            }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (TextUtils.isEmpty(binding.editText2.getText())) {
                binding.editText1.requestFocus();
            } else if (TextUtils.isEmpty(binding.editText3.getText())) {
                binding.editText2.requestFocus();
            } else if (TextUtils.isEmpty(binding.editText4.getText())) {
                binding.editText3.requestFocus();
            }
        }
        return false;
    }

    private boolean checkInputs() {
        boolean cancel = false;
        if (TextUtils.isEmpty(binding.editText1.getText())) {
            cancel = true;
            binding.editText1.requestFocus();
        } else if (TextUtils.isEmpty(binding.editText2.getText())) {
            cancel = true;
            binding.editText2.requestFocus();
        } else if (TextUtils.isEmpty(binding.editText3.getText())) {
            cancel = true;
            binding.editText3.requestFocus();
        } else if (TextUtils.isEmpty(binding.editText4.getText())) {
            cancel = true;
            binding.editText4.requestFocus();
        }
        return !cancel;
    }

    private void startCounter() {
        callback.getViewModel().setCounterVisibility(View.VISIBLE);
        callback.getViewModel().setTryAgainVisibility(View.GONE);
        callback.getViewModel().setArrowVisibility(View.GONE);
        new CountDownTimer(callback.getViewModel().getRemainedSeconds() * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                binding.textViewCounter.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {

                binding.textViewCounter.setVisibility(View.GONE);
                binding.textViewTryAgain.setVisibility(View.VISIBLE);
                binding.imageViewRightArrow.setVisibility(View.VISIBLE);
            }

        }.start();
    }
    private void startSMSListener() {
        try {
            smsReceiver.setOTPListener(this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            requireActivity().registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(requireActivity());

            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(aVoid -> {
            });

            task.addOnFailureListener(Throwable::printStackTrace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOTPReceived(String otp) {
        otp = otp.substring(otp.lastIndexOf(":") + 1, otp.lastIndexOf("\n"));
        callback.getViewModel().setVerificationCode(otp);
        binding.editText1.setText(otp.split("")[0]);
        binding.editText2.setText(otp.split("")[1]);
        binding.editText3.setText(otp.split("")[2]);
        binding.editText4.setText(otp.split("")[3]);

        if (smsReceiver != null) {
            requireActivity().unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }
    }

    @Override
    public void onOTPTimeOut() {
        callback.displayView(SUBMIT_PHONE_FRAGMENT);
    }

    @Override
    public void onOTPReceivedError(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            requireActivity().unregisterReceiver(smsReceiver);
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

        void editPreLoginViewModel(String token, String failureMessage, boolean result);
    }
}