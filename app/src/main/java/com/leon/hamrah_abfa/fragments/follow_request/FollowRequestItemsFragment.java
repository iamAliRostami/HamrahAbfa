package com.leon.hamrah_abfa.fragments.follow_request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.base_items.BaseBottomSheetFragment;
import com.leon.hamrah_abfa.databinding.FragmentFollowRequestItemsBinding;
import com.leon.hamrah_abfa.databinding.FragmentLastBillItemsBinding;

public class FollowRequestItemsFragment extends BaseBottomSheetFragment {
    private FragmentFollowRequestItemsBinding binding;

    public FollowRequestItemsFragment() {
    }

    public static FollowRequestItemsFragment newInstance() {
        return new FollowRequestItemsFragment();
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentFollowRequestItemsBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initialize() {

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}