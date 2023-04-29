package com.leon.hamrah_abfa.fragments.bottom_sheets;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.THEME;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_DARK;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_DEFAULT;
import static com.leon.hamrah_abfa.helpers.Constants.THEME_LIGHT;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentThemeBinding;

public class ThemeFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private FragmentThemeBinding binding;

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
        Log.e("here 1", String.valueOf(getResources().getConfiguration().uiMode));
        Log.e("here 2", String.valueOf(Configuration.UI_MODE_NIGHT_MASK));
        binding.buttonSubmit.setOnClickListener(this);
        binding.linearLayoutDark.setOnClickListener(this);
        binding.linearLayoutLight.setOnClickListener(this);
        binding.linearLayoutBasedOnDevice.setOnClickListener(this);
        initializeViews();
    }

    private void initializeViews() {
        if (getApplicationComponent().SharedPreferenceModel().getIntNullData(THEME.getValue()) == THEME_DEFAULT) {
            binding.linearLayoutBasedOnDevice.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_white_blue));
            binding.textViewThemeBasedOnDevice.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_7001));

//            TypedValue typedValue = new TypedValue();
//            Resources.Theme theme = requireContext().getTheme();
//            theme.resolveAttribute(android.R.attr.textColor, typedValue, true);
//            int color = typedValue.data;
//            binding.textViewThemeLight.setTextColor(color);
//            binding.textViewThemeDark.setTextColor(color);

//            binding.linearLayoutLight.setBackground(null);
//            binding.linearLayoutDark.setBackground(null);
//            binding.textViewThemeLight.setTextColor(ContextCompat.getColor(requireContext(), android.R.attr.textColor));
//            binding.textViewThemeDark.setTextColor(ContextCompat.getColor(requireContext(), android.R.attr.textColor));
//            binding.textViewThemeLight.setTextColor(android.R.attr.textColor);
//            binding.textViewThemeDark.setTextColor(android.R.attr.textColor);
        } else if (getApplicationComponent().SharedPreferenceModel().getIntNullData(THEME.getValue()) == THEME_LIGHT) {
            binding.linearLayoutLight.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_white_blue));
            binding.textViewThemeLight.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_7001));
        } else if (getApplicationComponent().SharedPreferenceModel().getIntNullData(THEME.getValue()) == THEME_DARK) {
            binding.linearLayoutDark.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_white_blue));
            binding.textViewThemeDark.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_7001));
        }
    }


    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
        } else if (id == R.id.linear_layout_dark) {
            setTheme(THEME_DARK);
        } else if (id == R.id.linear_layout_light) {
            setTheme(THEME_LIGHT);
        } else if (id == R.id.linear_layout_based_on_device) {
            setTheme(THEME_DEFAULT);
        }
    }

    private void setTheme(int theme) {
        if (theme == THEME_DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (theme == THEME_LIGHT) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        getApplicationComponent().SharedPreferenceModel().putData(THEME.getValue(), theme);
        final FragmentTransaction transactionCurrent = requireActivity().getSupportFragmentManager().beginTransaction();
        transactionCurrent.detach(ThemeFragment.this).commit();
        final FragmentTransaction transactionNext = requireActivity().getSupportFragmentManager().beginTransaction();
        transactionNext.attach(ThemeFragment.this).commit();
    }
}