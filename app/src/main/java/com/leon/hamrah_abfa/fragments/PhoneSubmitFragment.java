package com.leon.hamrah_abfa.fragments;

import static com.leon.hamrah_abfa.helpers.Constants.VERIFICATION_FRAGMENT;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentPhoneSubmitBinding;

public class PhoneSubmitFragment extends Fragment implements View.OnClickListener {
    private FragmentPhoneSubmitBinding binding;
    private Callback submitActivity;

    public PhoneSubmitFragment() {
    }

    public static PhoneSubmitFragment newInstance() {
        return new PhoneSubmitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhoneSubmitBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.textLayoutMobile.setHelperText(Html.fromHtml("<p>با وارد نمودن شماره همراه، یک <b>کد تایید</b> برای شما ارسال خواهد شد</p>", Html.FROM_HTML_MODE_COMPACT));
//            binding.textViewTip.setText(Html.fromHtml("<p><font color=#666666>I agree سلام</font><font color=#0173B7>  <b><u fontWeight=600 style=\"fontWeight:600\">سلام</u></b></font><font color=#666666> and the <u></font><b><font color=#0173B7>Privacy Policy</font></u></b></font></p>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            binding.textLayoutMobile.setHelperText(Html.fromHtml("<p>با وارد نمودن شماره همراه، یک <b>کد تایید</b> برای شما ارسال خواهد شد</p>"));
        }
        binding.imageViewSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.image_view_submit) {
            submitActivity.displayView(VERIFICATION_FRAGMENT);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) submitActivity = (Callback) context;
    }


    public interface Callback {
        void displayView(int position);
    }
}