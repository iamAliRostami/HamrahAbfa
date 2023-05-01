package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.FragmentTags.ACTIVE_SESSION;
import static com.leon.hamrah_abfa.enums.FragmentTags.CHANGE_THEME;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.MenuAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivitySettingBinding;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ActiveSessionFragment;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ThemeFragment;

public class SettingActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ActivitySettingBinding binding;
    @Override
    protected void initialize() {
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final MenuAdapter adapter = new MenuAdapter(this, R.array.setting_menu, R.array.setting_icons);
        binding.gridViewMenu.setAdapter(adapter);
        binding.gridViewMenu.setOnItemClickListener(this);
        binding.imageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.image_view_back) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            ShowFragmentDialogOnce(this, ACTIVE_SESSION.getValue(), ActiveSessionFragment.newInstance());
        } else if (position == 1) {
            ShowFragmentDialogOnce(this, CHANGE_THEME.getValue(), ThemeFragment.newInstance());
        }
    }

    @Override
    protected String getExitMessage() {
        return null;
    }
}