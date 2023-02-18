package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.helpers.Constants.SUBMIT_PHONE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.VERIFICATION_FRAGMENT;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.leon.hamrah_abfa.databinding.ActivitySubmitBinding;
import com.leon.hamrah_abfa.fragments.PhoneSubmitFragment;
import com.leon.hamrah_abfa.fragments.VerificationCodeFragment;

public class SubmitActivity extends AppCompatActivity implements PhoneSubmitFragment.Callback,
        VerificationCodeFragment.Callback {
    private String mobile;
    private ActivitySubmitBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySubmitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize() {
        displayView(SUBMIT_PHONE_FRAGMENT);
    }

    @Override
    public void displayView(int position) {
        final String tag = Integer.toString(position);
//        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
//        if (isLogin.length == 0 && fragment != null && fragment.isVisible()) return;
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.animator.enter, R.animator.exit,
//                R.animator.pop_enter, R.animator.pop_exit);
        fragmentTransaction.replace(binding.containerBody.getId(), getFragment(position), tag);
        // Home fragment is not added to the stack
        if (position != SUBMIT_PHONE_FRAGMENT) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commitAllowingStateLoss();
        runOnUiThread(() -> getFragmentManager().executePendingTransactions());
//        getFragmentManager().executePendingTransactions();
    }

    @Override
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String getMobile() {
        return mobile;
    }

    private Fragment getFragment(int position) {
        switch (position) {
            case VERIFICATION_FRAGMENT:
                return VerificationCodeFragment.newInstance();
            case SUBMIT_PHONE_FRAGMENT:
            default:
                return PhoneSubmitFragment.newInstance();
        }
    }
}