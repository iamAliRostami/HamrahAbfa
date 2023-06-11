package com.leon.hamrah_abfa.fragments.bottom_sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.adapters.recycler_view.ActiveSessionAdapter;
import com.leon.hamrah_abfa.base_items.BaseBottomSheetFragment;
import com.leon.hamrah_abfa.databinding.FragmentActiveSessionBinding;
import com.leon.hamrah_abfa.tables.ActiveSession;

import java.util.ArrayList;

public class ActiveSessionFragment extends BaseBottomSheetFragment {
    private FragmentActiveSessionBinding binding;
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
        final ArrayList<ActiveSession> activeSessions = new ArrayList<>();
        activeSessions.add(new ActiveSession("نام دستگاه", "09130000000", "12/12/12",
                "12.12.12.12"));
        activeSessions.add(new ActiveSession("نام دستگاه", "09130000000", "12/12/12",
                "12.12.12.12"));
        activeSessions.add(new ActiveSession("نام دستگاه", "09130000000", "12/12/12",
                "12.12.12.12"));
        activeSessions.add(new ActiveSession("نام دستگاه", "09130000000", "12/12/12",
                "12.12.12.12"));
        binding.recyclerViewActiveSession.setAdapter(new ActiveSessionAdapter(requireContext(), activeSessions));
        binding.recyclerViewActiveSession.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}