package com.app.leon.moshtarak.fragments.bottom_sheets;

import static com.app.leon.moshtarak.enums.FragmentTags.WAITING;
import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.base_items.BaseBottomSheetFragment;
import com.app.leon.moshtarak.databinding.FragmentMyAccountBinding;
import com.app.leon.moshtarak.enums.FragmentTags;
import com.app.leon.moshtarak.enums.SharedReferenceKeys;
import com.app.leon.moshtarak.fragments.dialog.WaitingFragment;
import com.app.leon.moshtarak.fragments.dialog.YesNoFragment;
import com.app.leon.moshtarak.helpers.MyApplication;
import com.app.leon.moshtarak.requests.my_account.AddByMobileRequest;
import com.app.leon.moshtarak.requests.my_account.RemoveAccountRequest;
import com.app.leon.moshtarak.utils.ShowFragment;

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
        mobile = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.MOBILE.getValue());
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
        showFragmentDialogOnce(requireContext(), FragmentTags.ASK_YES_NO.getValue(),
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
        showFragmentDialogOnce(requireContext(), FragmentTags.ASK_YES_NO.getValue(),
                YesNoFragment.newInstance(R.drawable.setting_logout, getString(R.string.exit_account), getString(R.string.are_u_sure_remove),
                        getString(R.string.go_continue), getString(R.string.cancel), new YesNoFragment.IClickListener() {
                            @Override
                            public void yes(DialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                progressStatus(new RemoveAccountRequest(requireContext(),
                                        new RemoveAccountRequest.ICallback() {
                                            @Override
                                            public void succeed() {
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.ID.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.BILL_ID.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.ALIAS.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.DEBT.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.DEBT.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.DEADLINE.getValue(), "");
                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.MOBILE.getValue(), "");

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