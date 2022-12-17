package com.leon.hamrah_abfa.fragments;

import static com.leon.hamrah_abfa.enums.BundleEnum.BACKGROUND_COLOR;
import static com.leon.hamrah_abfa.enums.BundleEnum.POSITION;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentViewPagerBinding;

public class ViewPagerFragment extends Fragment {
    private FragmentViewPagerBinding binding;
    private int position, bgColor;

    public ViewPagerFragment() {
    }

    public static ViewPagerFragment newInstance(int position, int bgColor) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION.getValue(), position);
        args.putInt(BACKGROUND_COLOR.getValue(), bgColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION.getValue());
            bgColor = getArguments().getInt(BACKGROUND_COLOR.getValue());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imageViewLogo.setImageResource(R.mipmap.ic_launcher_round);
        binding.relativeLayoutContainer.setBackgroundColor(bgColor);
        binding.textViewTitle.setText("Welcome, this is Page: " + position + 1);
        binding.textViewContent.setText("This is Page: " + position + 1);

    }
}