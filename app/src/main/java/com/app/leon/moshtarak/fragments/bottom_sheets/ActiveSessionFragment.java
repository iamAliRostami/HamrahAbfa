package com.app.leon.moshtarak.fragments.bottom_sheets;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.leon.moshtarak.adapters.recycler_view.ActiveSessionAdapter;
import com.app.leon.moshtarak.base_items.BaseBottomSheetFragment;
import com.app.leon.moshtarak.databinding.FragmentActiveSessionBinding;

public class ActiveSessionFragment extends BaseBottomSheetFragment {
    private FragmentActiveSessionBinding binding;
    private ICallback callback;

    public ActiveSessionFragment() {
    }

    public static ActiveSessionFragment newInstance() {
        return new ActiveSessionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentActiveSessionBinding.inflate(inflater, container, false);
        initializeRecyclerView();
        return binding.getRoot();
    }

    private void initializeRecyclerView() {
        binding.recyclerViewActiveSession.setAdapter(new ActiveSessionAdapter(requireContext(), callback.getMobileHistory().loginHistory));
        binding.recyclerViewActiveSession.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        MobileHistory getMobileHistory();
    }
}