package com.leon.hamrah_abfa.fragments.usage_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.leon.hamrah_abfa.adapters.recycler_view.UsageHistoryAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.UsageHistoryFailedAdapter;
import com.leon.hamrah_abfa.databinding.FragmentUsageHistorySuccessfulBinding;

import java.util.ArrayList;
import java.util.List;

public class UsageHistorySuccessfulFragment extends Fragment implements ChipGroup.OnCheckedStateChangeListener {
    private FragmentUsageHistorySuccessfulBinding binding;
    private UsageHistoryAdapter adapter;

    public UsageHistorySuccessfulFragment() {
    }

    public static UsageHistorySuccessfulFragment newInstance() {
        return new UsageHistorySuccessfulFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsageHistorySuccessfulBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        final ArrayList<UsageHistoryViewModel> usageHistory = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            usageHistory.add(new UsageHistoryViewModel("12/12/12", "145","4523655233"));
        adapter = new UsageHistoryAdapter(requireContext(), usageHistory);
        binding.recyclerViewUsageSuccessful.setAdapter(adapter);
        binding.recyclerViewUsageSuccessful.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
        final Chip chip = group.findViewById(checkedIds.get(0));
        if (chip != null) {
            adapter.setShownNumber(Integer.parseInt(chip.getText().toString()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}