package com.app.leon.moshtarak.fragments.ui.help;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.databinding.FragmentHelpViewPagerBinding;
import com.app.leon.moshtarak.enums.BundleEnum;

public class HelpViewPagerFragment extends Fragment {
    private final HelpViewModel viewModel = new HelpViewModel();
    private FragmentHelpViewPagerBinding binding;

    public HelpViewPagerFragment() {
    }

    public static HelpViewPagerFragment newInstance(int position, String title, String content,
                                                    int anim) {
        final HelpViewPagerFragment fragment = new HelpViewPagerFragment();
        final Bundle args = new Bundle();
        args.putInt(BundleEnum.POSITION.getValue(), position);
        args.putInt(BundleEnum.ANIM.getValue(), anim);
        args.putString(BundleEnum.TITLE.getValue(), title);
        args.putString(BundleEnum.CONTENT.getValue(), content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel.setPosition(getArguments().getInt(BundleEnum.POSITION.getValue()));
            viewModel.setAnimSrc(getArguments().getInt(BundleEnum.ANIM.getValue()));
            viewModel.setTitle(getArguments().getString(BundleEnum.TITLE.getValue()));
            viewModel.setContent(getArguments().getString(BundleEnum.CONTENT.getValue()));
            getArguments().clear();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHelpViewPagerBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.textViewContent.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        binding.lottieLogo.setAnimation(viewModel.getAnimSrc());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}