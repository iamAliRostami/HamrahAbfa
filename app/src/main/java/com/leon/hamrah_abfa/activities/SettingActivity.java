package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.SHOW_PRE_FRAGMENT;
import static com.leon.hamrah_abfa.enums.FragmentTags.ACTIVE_SESSION;
import static com.leon.hamrah_abfa.enums.FragmentTags.CHANGE_THEME;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.THEME;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.MenuAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivitySettingBinding;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ActiveSessionFragment;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ThemeFragment;

public class SettingActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        ThemeFragment.ICallback {
    private ActivitySettingBinding binding;
    private boolean isChange = false;

    @Override
    protected void initialize() {
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean(SHOW_PRE_FRAGMENT.getValue())) {
                ShowFragmentDialogOnce(this, CHANGE_THEME.getValue(), ThemeFragment.newInstance());
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
        getApplicationComponent().SharedPreferenceModel().putData(THEME.getValue(), value);
        final Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
        intent.putExtra(SHOW_PRE_FRAGMENT.getValue(), true);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            ShowFragmentDialogOnce(this, ACTIVE_SESSION.getValue(), ActiveSessionFragment.newInstance());
        } else if (position == 1) {
            ShowFragmentDialogOnce(this, CHANGE_THEME.getValue(), ThemeFragment.newInstance());
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
    public void onBackPressed() {
        super.onBackPressed();
        setResult();
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

}