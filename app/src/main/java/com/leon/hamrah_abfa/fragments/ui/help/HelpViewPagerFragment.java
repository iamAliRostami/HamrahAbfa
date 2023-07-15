package com.leon.hamrah_abfa.fragments.ui.help;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;
import static com.leon.hamrah_abfa.enums.BundleEnum.ANIM;
import static com.leon.hamrah_abfa.enums.BundleEnum.BACKGROUND_COLOR;
import static com.leon.hamrah_abfa.enums.BundleEnum.CONTENT;
import static com.leon.hamrah_abfa.enums.BundleEnum.LOGO;
import static com.leon.hamrah_abfa.enums.BundleEnum.POSITION;
import static com.leon.hamrah_abfa.enums.BundleEnum.TITLE;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentHelpViewPagerBinding;

public class HelpViewPagerFragment extends Fragment {
    private final HelpViewModel viewModel = new HelpViewModel();
    private FragmentHelpViewPagerBinding binding;

    public HelpViewPagerFragment() {
    }

    public static HelpViewPagerFragment newInstance(int position, String title, String content,/*int logo,*/
                                                    int anim, int bgColor) {
        final HelpViewPagerFragment fragment = new HelpViewPagerFragment();
        final Bundle args = new Bundle();
        args.putInt(POSITION.getValue(), position);
        args.putString(TITLE.getValue(), title);
        args.putString(CONTENT.getValue(), content);
//        args.putInt(LOGO.getValue(), logo);
        args.putInt(ANIM.getValue(), anim);
        args.putInt(BACKGROUND_COLOR.getValue(), bgColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            viewModel.setLogo(getArguments().getInt(LOGO.getValue()));
//            viewModel.setLogoDrawable(ContextCompat.getDrawable(requireContext(), getArguments().getInt(LOGO.getValue())));
            viewModel.setTitle(getArguments().getString(TITLE.getValue()));
            viewModel.setPosition(getArguments().getInt(POSITION.getValue()));
            viewModel.setContent(getArguments().getString(CONTENT.getValue()));
            viewModel.setBgColor(getArguments().getInt(BACKGROUND_COLOR.getValue()));
            viewModel.setAnimSrc(getArguments().getInt(ANIM.getValue()));
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