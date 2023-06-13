package com.leon.hamrah_abfa.fragments.checkout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.leon.hamrah_abfa.adapters.recycler_view.CheckoutBillAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentCheckoutBillBinding;

import java.util.ArrayList;
import java.util.List;

public class CheckoutBillFragment extends Fragment implements View.OnClickListener, ChipGroup.OnCheckedStateChangeListener {
    private FragmentCheckoutBillBinding binding;
    private CheckoutBillAdapter adapter;
    private ICallback callback;

    public CheckoutBillFragment() {
    }

    public static CheckoutBillFragment newInstance() {
        return new CheckoutBillFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutBillBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.chipGroup.setOnCheckedStateChangeListener(this);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        final ArrayList<CheckoutBillViewModel> bills = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            bills.add(new CheckoutBillViewModel("1402/12/12", "12/12/12", "1234567890", 125, 38, i % 3));
        adapter = new CheckoutBillAdapter(requireContext(), bills);
        binding.recyclerViewCheckoutBill.setAdapter(adapter);
        binding.recyclerViewCheckoutBill.setLayoutManager(new LinearLayoutManager(requireContext()));
        setRecyclerViewListener();
    }

    private void setRecyclerViewListener() {
        final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewCheckoutBill, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        binding.recyclerViewCheckoutBill.addOnItemTouchListener(listener);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
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
        String getBillId();
    }
}