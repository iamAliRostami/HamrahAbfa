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
import com.leon.hamrah_abfa.adapters.recycler_view.CheckoutPaymentAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentCheckoutPaymentBinding;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPaymentFragment extends Fragment implements View.OnClickListener, ChipGroup.OnCheckedStateChangeListener {
    private FragmentCheckoutPaymentBinding binding;
    private CheckoutPaymentAdapter adapter;
    private ICallback callback;

    public CheckoutPaymentFragment() {
    }

    public static CheckoutPaymentFragment newInstance() {
        return new CheckoutPaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutPaymentBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.chipGroup.setOnCheckedStateChangeListener(this);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        final ArrayList<CheckoutPaymentViewModel> payments = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            payments.add(new CheckoutPaymentViewModel("12/12/12", "122222", "اینترنتی", "مهر اقتصاد", i % 2 == 0));

        adapter = new CheckoutPaymentAdapter(requireContext(), payments);
        binding.recyclerViewCheckoutPayment.setAdapter(adapter);
        binding.recyclerViewCheckoutPayment.setLayoutManager(new LinearLayoutManager(requireContext()));
        setRecyclerViewListener();
    }

    private void setRecyclerViewListener() {
        final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewCheckoutPayment, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        binding.recyclerViewCheckoutPayment.addOnItemTouchListener(listener);
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