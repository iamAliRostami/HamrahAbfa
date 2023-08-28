package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.IS_FIRST;
import static com.leon.hamrah_abfa.enums.BundleEnum.SHOW_PRE_FRAGMENT;
import static com.leon.hamrah_abfa.enums.FragmentTags.ACTIVE_SESSION;
import static com.leon.hamrah_abfa.enums.FragmentTags.CHANGE_THEME;
import static com.leon.hamrah_abfa.enums.FragmentTags.MY_ACCOUNT;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.THEME;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.AdapterView;

import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.MenuAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivitySettingBinding;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ActiveSessionFragment;
import com.leon.hamrah_abfa.fragments.bottom_sheets.MobileHistory;
import com.leon.hamrah_abfa.fragments.bottom_sheets.MyAccountFragment;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ThemeFragment;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.requests.GetMobileHistoryRequest;

public class SettingActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        ThemeFragment.ICallback, ActiveSessionFragment.ICallback {
    private ActivitySettingBinding binding;
    private DialogFragment fragment;
    private MobileHistory mobileHistory;
    private boolean isChange = false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean(SHOW_PRE_FRAGMENT.getValue())) {
                showFragmentDialogOnce(this, CHANGE_THEME.getValue(), ThemeFragment.newInstance());
                isChange = true;
            }
            getIntent().getExtras().clear();
        }
        initializeGridView();
        binding.imageViewBack.setOnClickListener(this);
    }

    private void initializeGridView() {
        final MenuAdapter adapter = new MenuAdapter(this, R.array.setting_menu, R.array.setting_icons);
        binding.gridViewMenu.setAdapter(adapter);
        binding.gridViewMenu.setOnItemClickListener(this);
    }

    @Override
    public void changeTheme(int value) {
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(THEME.getValue(), value);
        final Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
        intent.putExtra(SHOW_PRE_FRAGMENT.getValue(), true);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            requestLoginHistory();
        } else if (position == 1) {
            showFragmentDialogOnce(this, CHANGE_THEME.getValue(), ThemeFragment.newInstance());
        } else if (position == 2) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra(IS_FIRST.getValue(), false);
            startActivity(intent);
        } else if (position == 5) {
            showFragmentDialogOnce(this, MY_ACCOUNT.getValue(), MyAccountFragment.newInstance());
        }
    }

    private void requestLoginHistory() {
        boolean isOnline = new GetMobileHistoryRequest(this, new GetMobileHistoryRequest.ICallback() {

            @Override
            public void succeed(MobileHistory mobileHistory) {
                showMobileHistory(mobileHistory);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }).request();
        progressStatus(isOnline);
    }

    private void showMobileHistory(MobileHistory mobileHistory) {
        this.mobileHistory = mobileHistory;
        showFragmentDialogOnce(this, ACTIVE_SESSION.getValue(), ActiveSessionFragment.newInstance());
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                showFragmentDialogOnce(this, WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void setResult() {
        if (isChange) {
            final Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.image_view_back) {
            setResult();
            finish();
        }
    }

    @Override
    public MobileHistory getMobileHistory() {
        return mobileHistory;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult();
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

}