package com.leon.hamrah_abfa.activities;

import android.view.View;
import android.widget.AdapterView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.MenuAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityContactUsBinding;
import com.leon.hamrah_abfa.databinding.ActivitySettingBinding;

public class ContactUsActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ActivityContactUsBinding binding;

    @Override
    protected void initialize() {
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeGridView();
        binding.imageViewBack.setOnClickListener(this);
    }

    private void initializeGridView() {
        final MenuAdapter adapter = new MenuAdapter(this, R.array.contact_abfa_menu, R.array.contact_abfa_icons);
        binding.gridViewMenu.setAdapter(adapter);
        binding.gridViewMenu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.image_view_back) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

}