package com.leon.hamrah_abfa.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.hamrah_abfa.databinding.ActivityServiceBinding;

public class ServiceActivity extends AppCompatActivity {
    private ActivityServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceBinding.inflate(getLayoutInflater());
        initialize();
        setContentView(binding.getRoot());
    }

    private void initialize() {

    }
}