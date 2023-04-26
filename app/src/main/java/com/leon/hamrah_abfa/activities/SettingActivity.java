package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.FragmentTags.ACTIVE_SESSION;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.MenuAdapter;
import com.leon.hamrah_abfa.databinding.ActivitySettingBinding;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ActiveSessionFragment;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize() {
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

        }
    }
}