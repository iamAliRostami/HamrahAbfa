package com.leon.hamrah_abfa.fragments;

import static com.leon.hamrah_abfa.enums.BundleEnum.BACKGROUND_COLOR;
import static com.leon.hamrah_abfa.enums.BundleEnum.CONTENT;
import static com.leon.hamrah_abfa.enums.BundleEnum.LOGO;
import static com.leon.hamrah_abfa.enums.BundleEnum.POSITION;
import static com.leon.hamrah_abfa.enums.BundleEnum.TITLE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentViewPagerBinding;
import com.leon.hamrah_abfa.di.view_model.WelcomeViewModel;

public class ViewPagerFragment extends Fragment {
    private final WelcomeViewModel viewModel = new WelcomeViewModel();

    public ViewPagerFragment() {
    }

    public static ViewPagerFragment newInstance(int position, int bgColor, int logo, String title,
                                                String content) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(LOGO.getValue(), logo);
        args.putString(TITLE.getValue(), title);
        args.putInt(POSITION.getValue(), position);
        args.putString(CONTENT.getValue(), content);
        args.putInt(BACKGROUND_COLOR.getValue(), bgColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel.setLogo(getArguments().getInt(LOGO.getValue()));
            viewModel.setLogoDrawable(ContextCompat.getDrawable(requireContext(), getArguments().getInt(LOGO.getValue())));
            viewModel.setTitle(getArguments().getString(TITLE.getValue()));
            viewModel.setPosition(getArguments().getInt(POSITION.getValue()));
            viewModel.setContent(getArguments().getString(CONTENT.getValue()));
            viewModel.setBgColor(getArguments().getInt(BACKGROUND_COLOR.getValue()));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentViewPagerBinding binding = FragmentViewPagerBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}