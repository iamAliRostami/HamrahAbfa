package com.app.leon.moshtarak.fragments.notifications;

import static com.app.leon.moshtarak.enums.BundleEnum.POSITION;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.app.leon.moshtarak.base_items.BaseBottomSheetFragment;
import com.app.leon.moshtarak.databinding.FragmentNewsDetailBinding;

import org.jetbrains.annotations.NotNull;

public class NewsDetailFragment extends BaseBottomSheetFragment implements View.OnClickListener {
    private FragmentNewsDetailBinding binding;
    private ICallback callback;
    private int position;

    public NewsDetailFragment() {
    }

    public static NewsDetailFragment newInstance(int position) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION.getValue(), position);
        fragment.setArguments(args);
        fragment.setCancelable(false);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION.getValue());
            getArguments().clear();
        }
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getNews(position));
        initialize();
        return binding.getRoot();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initialize() {
        WebSettings webSettings = binding.webViewNews.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.webViewNews.setWebViewClient(new WebViewClient());
        binding.webViewNews.loadUrl(callback.getNews(position).getLink());
//        binding.webViewNews.loadUrl("https://varzesh3.com");
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface ICallback {
        NewsViewModel getNews(int position);
    }
}