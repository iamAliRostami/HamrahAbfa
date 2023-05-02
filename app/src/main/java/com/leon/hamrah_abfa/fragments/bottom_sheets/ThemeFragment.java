package com.leon.hamrah_abfa.fragments.bottom_sheets;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.THEME;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.THEME_MODE;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_DARK;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_DEFAULT;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_LIGHT;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.Slider;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentThemeBinding;
import com.leon.hamrah_abfa.enums.SharedReferenceKeys;

import org.jetbrains.annotations.NotNull;

public class ThemeFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private FragmentThemeBinding binding;
    private ICallback settingActivity;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        binding.sliderSize.setValue(getApplicationComponent().SharedPreferenceModel().getIntNullData(THEME.getValue()));
//        binding.sliderSize.addOnChangeListener((slider, value, fromUser) -> settingActivity.changeTheme((int) value));
        binding.sliderSize.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                settingActivity.changeTheme((int) slider.getValue());

            }
        });
    }

    private void initializeViews() {
        binding.textViewThemeBasedOnDevice.setTextAppearance(requireContext(), R.style.ThemeTextStyle);
        binding.textViewThemeLight.setTextAppearance(requireContext(), R.style.ThemeTextStyle);
        binding.textViewThemeDark.setTextAppearance(requireContext(), R.style.ThemeTextStyle);
        binding.linearLayoutBasedOnDevice.setBackground(null);
        binding.linearLayoutLight.setBackground(null);
        binding.linearLayoutDark.setBackground(null);

        if (getApplicationComponent().SharedPreferenceModel().getIntNullData(THEME_MODE.getValue()) == THEME_DEFAULT) {
            binding.linearLayoutBasedOnDevice.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_white_blue));
            binding.textViewThemeBasedOnDevice.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_7001));
        } else if (getApplicationComponent().SharedPreferenceModel().getIntNullData(THEME_MODE.getValue()) == THEME_LIGHT) {
            binding.linearLayoutLight.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_white_blue));
            binding.textViewThemeLight.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_7001));
        } else if (getApplicationComponent().SharedPreferenceModel().getIntNullData(THEME_MODE.getValue()) == THEME_DARK) {
            binding.linearLayoutDark.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_white_blue));
            binding.textViewThemeDark.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_7001));
        }
    }


    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
        } else if (id == R.id.linear_layout_dark) {
            setThemeMode(THEME_DARK);
        } else if (id == R.id.linear_layout_light) {
            setThemeMode(THEME_LIGHT);
        } else if (id == R.id.linear_layout_based_on_device) {
            setThemeMode(THEME_DEFAULT);
        }
    }

    private void setThemeMode(int theme) {
        if (theme == THEME_DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (theme == THEME_LIGHT) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        getApplicationComponent().SharedPreferenceModel().putData(THEME_MODE.getValue(), theme);
        initializeViews();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) settingActivity = (ICallback) context;
    }

    public interface ICallback {
        void changeTheme(int value);
    }
}