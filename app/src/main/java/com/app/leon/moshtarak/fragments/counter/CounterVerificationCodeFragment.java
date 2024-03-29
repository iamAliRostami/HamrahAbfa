package com.app.leon.moshtarak.fragments.counter;

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

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.databinding.FragmentCounterVerificationCodeBinding;
import com.app.leon.moshtarak.enums.FragmentTags;
import com.app.leon.moshtarak.fragments.dialog.MessageDoneRequestFragment;
import com.app.leon.moshtarak.helpers.Constants;
import com.app.leon.moshtarak.requests.counter.GenerateBillRequest;
import com.app.leon.moshtarak.utils.SMSReceiver;
import com.app.leon.moshtarak.utils.ShowFragment;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;

public class CounterVerificationCodeFragment extends Fragment implements TextWatcher,
        View.OnKeyListener, View.OnClickListener, SMSReceiver.OTPReceiveListener {
    private SMSReceiver smsReceiver = new SMSReceiver();
    private FragmentCounterVerificationCodeBinding binding;
    private ICallback callback;

    public CounterVerificationCodeFragment() {
    }

    public static CounterVerificationCodeFragment newInstance() {
        return new CounterVerificationCodeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCounterVerificationCodeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        startCounter();
        startSMSListener();
        binding.textViewMobile.setText(callback.getViewModel().getMobile());
        binding.textViewMobile.setSelected(true);
        binding.textViewTryAgain.setOnClickListener(this);
        binding.buttonSubmit.setOnClickListener(this);
        binding.imageViewEdit.setOnClickListener(this);
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
        if (id == R.id.button_submit) {
            if (checkInputs()) {
                String submitCode = binding.editText1.getEditableText().toString() +
                        binding.editText2.getEditableText().toString() +
                        binding.editText3.getEditableText().toString() +
                        binding.editText4.getEditableText().toString();
                callback.getViewModel().setSubmitCode(submitCode);
                generateBillRequest();
            }
        } else if (id == R.id.text_view_try_again) {
            callback.displayView(Constants.COUNTER_BASE_FRAGMENT);
        } else if (id == R.id.image_view_edit) {
            callback.displayView(Constants.COUNTER_BASE_FRAGMENT);
        }
    }

    private void generateBillRequest() {
        progressStatus(!new GenerateBillRequest(getContext(), callback.getViewModel(),
                new GenerateBillRequest.ICallback() {
                    @Override
                    public void succeed(String message) {
                        responseMessage(message);
                        callback.getViewModel().setMessage(message);
                        callback.getViewModel().setCounterClaim("");
                    }

                    @Override
                    public void changeUI(boolean done) {
                        progressStatus(done);
                    }
                }).request());
    }

    private void progressStatus(boolean hide) {
        if (hide) {
            binding.buttonSubmit.setVisibility(View.VISIBLE);
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.lottieAnimationView.pauseAnimation();
        } else {
            binding.buttonSubmit.setVisibility(View.GONE);
            binding.lottieAnimationView.playAnimation();
            binding.lottieAnimationView.setVisibility(View.VISIBLE);
        }
    }

    private void responseMessage(String message) {
        ShowFragment.showFragmentDialogOnce(requireContext(), FragmentTags.REQUEST_DONE.getValue(),
                MessageDoneRequestFragment.newInstance(message, getString(R.string.main_page),
                        dialogFragment -> {
                            dialogFragment.dismiss();
                            callback.displayView(Constants.COUNTER_BASE_FRAGMENT);
                        }
                ));
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
                try {
                    InputMethodManager inputManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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

    private void startCounter() {
        binding.textViewCounter.setVisibility(View.VISIBLE);
        binding.textViewTryAgain.setVisibility(View.GONE);
        binding.imageViewRight.setVisibility(View.GONE);
        new CountDownTimer(callback.getViewModel().getRemainedSeconds() * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                binding.textViewCounter.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                binding.textViewCounter.setVisibility(View.GONE);
                binding.textViewTryAgain.setVisibility(View.VISIBLE);
                binding.imageViewRight.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
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
        String numberOnly = otp.replaceAll("[^0-9]", "");
        callback.getViewModel().setVerificationCode(numberOnly.substring(0, 4));
        binding.editText1.setText(callback.getViewModel().getVerificationCode().substring(0, 1));
        binding.editText2.setText(callback.getViewModel().getVerificationCode().substring(1, 2));
        binding.editText3.setText(callback.getViewModel().getVerificationCode().substring(2, 3));
        binding.editText4.setText(callback.getViewModel().getVerificationCode().substring(3, 4));
        if (smsReceiver != null) {
            requireActivity().unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }
    }

    @Override
    public void onOTPTimeOut() {
        callback.displayView(Constants.SUBMIT_PHONE_FRAGMENT);
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

        CounterViewModel getViewModel();
    }
}