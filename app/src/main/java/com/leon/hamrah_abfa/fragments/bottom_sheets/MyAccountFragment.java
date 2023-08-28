package com.leon.hamrah_abfa.fragments.bottom_sheets;

import static com.leon.hamrah_abfa.enums.FragmentTags.ASK_YES_NO;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ALIAS;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEADLINE;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEBT;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.MOBILE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseBottomSheetFragment;
import com.leon.hamrah_abfa.databinding.FragmentMyAccountBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.fragments.dialog.YesNoFragment;
import com.leon.hamrah_abfa.requests.my_account.AddByMobileRequest;
import com.leon.hamrah_abfa.requests.my_account.RemoveAccountRequest;

public class MyAccountFragment extends BaseBottomSheetFragment implements View.OnClickListener {

    private DialogFragment fragment;
    private String mobile;

    public MyAccountFragment() {
    }

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        FragmentMyAccountBinding binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        mobile = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(MOBILE.getValue());
        binding.textViewTitle.setText(mobile);
        binding.buttonExit.setOnClickListener(this);
        binding.buttonSync.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.button_sync) {
            showSyncDialog();
        } else if (i == R.id.button_exit) {
            showExitDialog();
        }
    }

    private void showSyncDialog() {
        showFragmentDialogOnce(requireContext(), ASK_YES_NO.getValue(),
                YesNoFragment.newInstance(
                        R.drawable.setting_logout, getString(R.string.connected_account), getString(R.string.are_u_sure_sync),
                        getString(R.string.go_continue), getString(R.string.cancel), new YesNoFragment.IClickListener() {
                            @Override
                            public void yes(DialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                progressStatus(new AddByMobileRequest(requireContext(),
                                        new AddByMobileRequest.ICallback() {
                                            @Override
                                            public void succeed() {
                                                restartApplication();
                                            }

                                            @Override
                                            public void changeUI(boolean done) {
                                                progressStatus(done);
                                            }
                                        }, mobile).request());
                            }

                            @Override
                            public void no(DialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                            }
                        })
        );
    }

    private void showExitDialog() {
        showFragmentDialogOnce(requireContext(), ASK_YES_NO.getValue(),
                YesNoFragment.newInstance(R.drawable.setting_logout, getString(R.string.exit_account), getString(R.string.are_u_sure_remove),
                        getString(R.string.go_continue), getString(R.string.cancel), new YesNoFragment.IClickListener() {
                            @Override
                            public void yes(DialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                progressStatus(new RemoveAccountRequest(requireContext(),
                                        new RemoveAccountRequest.ICallback() {
                                            @Override
                                            public void succeed() {
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(ID.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(ALIAS.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEADLINE.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(MOBILE.getValue(), "");

                                                try {
                                                    Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                    throw new RuntimeException(e);
                                                }
                                                restartApplication();
                                            }

                                            @Override
                                            public void changeUI(boolean done) {
                                                progressStatus(done);
                                            }
                                        }).request());
                            }

                            @Override
                            public void no(DialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                            }
                        })
        );
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                showFragmentDialogOnce(requireContext(), WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void restartApplication() {
        PackageManager packageManager = requireContext().getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(requireContext().getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }
}