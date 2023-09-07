package com.app.leon.moshtarak.fragments.usage_history;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.leon.moshtarak.adapters.recycler_view.UsageHistoryFailedAdapter;
import com.app.leon.moshtarak.databinding.FragmentUsageHistoryFailedBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class UsageHistoryFailedFragment extends Fragment implements ChipGroup.OnCheckedStateChangeListener {
    private FragmentUsageHistoryFailedBinding binding;
    private UsageHistoryFailedAdapter adapter;
    private ICallback callback;

    public UsageHistoryFailedFragment() {
    }

    public static UsageHistoryFailedFragment newInstance() {
        return new UsageHistoryFailedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsageHistoryFailedBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        adapter = new UsageHistoryFailedAdapter(requireContext(), callback.getUnsuccessBills());
        binding.recyclerViewUsageFailed.setAdapter(adapter);
        binding.recyclerViewUsageFailed.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
        final Chip chip = group.findViewById(checkedIds.get(0));
        if (chip != null) {
            adapter.setShownNumber(Integer.parseInt(chip.getText().toString()));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface ICallback {
        String getId();

        ArrayList<AttemptViewModel> getUnsuccessBills();
    }
}