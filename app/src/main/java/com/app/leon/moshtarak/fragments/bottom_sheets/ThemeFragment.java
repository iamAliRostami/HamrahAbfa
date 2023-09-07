package com.app.leon.moshtarak.fragments.bottom_sheets;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.base_items.BaseBottomSheetFragment;
import com.app.leon.moshtarak.databinding.FragmentThemeBinding;
import com.app.leon.moshtarak.enums.SharedReferenceKeys;
import com.app.leon.moshtarak.helpers.Constants;
import com.app.leon.moshtarak.helpers.MyApplication;
import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

public class ThemeFragment extends BaseBottomSheetFragment {
    private FragmentThemeBinding binding;
    private ICallback callback;

    public ThemeFragment() {
    }

    public static ThemeFragment newInstance() {
        return new ThemeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentThemeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.linearLayoutDark.setOnClickListener(this);
        binding.linearLayoutLight.setOnClickListener(this);
        binding.linearLayoutBasedOnDevice.setOnClickListener(this);
        initializeViews();
        initializeSlider();
    }

    private void initializeSlider() {
        binding.sliderSize.setValue(MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getIntNullData(SharedReferenceKeys.THEME.getValue()));
//        binding.sliderSize.addOnChangeListener((slider, value, fromUser) -> settingActivity.changeTheme((int) value));
        binding.sliderSize.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                callback.changeTheme((int) slider.getValue());

            }
        });
    }

    private void initializeViews() {
        resetTextViews();
        if (MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getIntNullData(SharedReferenceKeys.THEME_MODE.getValue()) == Constants.THEME_DEFAULT) {
            binding.linearLayoutBasedOnDevice.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_white_blue));
            binding.textViewThemeBasedOnDevice.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_7001));
        } else if (MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getIntNullData(SharedReferenceKeys.THEME_MODE.getValue()) == Constants.THEME_LIGHT) {
            binding.linearLayoutLight.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_white_blue));
            binding.textViewThemeLight.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_7001));
        } else if (MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getIntNullData(SharedReferenceKeys.THEME_MODE.getValue()) == Constants.THEME_DARK) {
            binding.linearLayoutDark.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_white_blue));
            binding.textViewThemeDark.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_7001));
        }
    }

    private void resetTextViews() {
        binding.textViewThemeBasedOnDevice.setSelected(true);
        binding.textViewThemeLight.setSelected(true);
        binding.textViewThemeDark.setSelected(true);

        binding.textViewThemeBasedOnDevice.setTextAppearance(requireContext(), R.style.TextThemeStyle);
        binding.textViewThemeLight.setTextAppearance(requireContext(), R.style.TextThemeStyle);
        binding.textViewThemeDark.setTextAppearance(requireContext(), R.style.TextThemeStyle);
        binding.linearLayoutBasedOnDevice.setBackground(null);
        binding.linearLayoutLight.setBackground(null);
        binding.linearLayoutDark.setBackground(null);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        final int id = v.getId();
        if (id == R.id.linear_layout_dark) {
            setThemeMode(Constants.THEME_DARK);
        } else if (id == R.id.linear_layout_light) {
            setThemeMode(Constants.THEME_LIGHT);
        } else if (id == R.id.linear_layout_based_on_device) {
            setThemeMode(Constants.THEME_DEFAULT);
        }
    }

    private void setThemeMode(int theme) {
        if (theme == Constants.THEME_DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (theme == Constants.THEME_LIGHT) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.THEME_MODE.getValue(), theme);
        initializeViews();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        void changeTheme(int value);
    }
}