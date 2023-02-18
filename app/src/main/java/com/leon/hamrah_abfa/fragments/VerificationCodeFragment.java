package com.leon.hamrah_abfa.fragments;

import static com.leon.hamrah_abfa.helpers.Constants.SUBMIT_PHONE_FRAGMENT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.activities.MainActivity;
import com.leon.hamrah_abfa.databinding.FragmentVerificationCodeBinding;

public class VerificationCodeFragment extends Fragment implements View.OnClickListener,
        TextWatcher, View.OnKeyListener {
    private FragmentVerificationCodeBinding binding;
    private Callback submitActivity;

    public VerificationCodeFragment() {
    }

    public static VerificationCodeFragment newInstance() {
        return new VerificationCodeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerificationCodeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        startCounter();
        binding.textViewMobile.setText(getString(R.string.mobile).concat(" ").concat(submitActivity.getMobile()));
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
                inputManager.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                binding.buttonSubmit.requestFocus();
            }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (binding.editText2.getText().length() < 1) {
                binding.editText1.requestFocus();
            } else if (binding.editText3.getText().length() < 1) {
                binding.editText2.requestFocus();
            } else if (binding.editText4.getText().length() < 1) {
                binding.editText3.requestFocus();
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.text_view_try_again) {
            binding.textViewCounter.setVisibility(View.VISIBLE);
            binding.textViewTryAgain.setVisibility(View.GONE);
            binding.imageViewRight.setVisibility(View.GONE);
            startCounter();
        } else if (id == R.id.button_submit) {
            if (checkInputs()) {
                final Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.image_view_edit) {
            submitActivity.displayView(SUBMIT_PHONE_FRAGMENT);
        }
    }

    private boolean checkInputs() {
        boolean cancel = false;
        if (binding.editText1.getText().toString().isEmpty()) {
            cancel = true;
            binding.editText1.requestFocus();
        } else if (binding.editText2.getText().toString().isEmpty()) {
            cancel = true;
            binding.editText2.requestFocus();
        } else if (binding.editText3.getText().toString().isEmpty()) {
            cancel = true;
            binding.editText3.requestFocus();
        } else if (binding.editText4.getText().toString().isEmpty()) {
            cancel = true;
            binding.editText4.requestFocus();
        }
        return !cancel;
    }

    private void startCounter() {
        new CountDownTimer(10000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                binding.textViewCounter.setText(millisUntilFinished / 1000 + " : " + millisUntilFinished / (1000 * 60));
            }

            public void onFinish() {
                binding.textViewCounter.setVisibility(View.GONE);
                binding.textViewTryAgain.setVisibility(View.VISIBLE);
                binding.imageViewRight.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) submitActivity = (Callback) context;
    }


    public interface Callback {
        void displayView(int position);

        void setMobile(String mobile);

        String getMobile();
    }
}