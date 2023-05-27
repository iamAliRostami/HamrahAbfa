package com.leon.hamrah_abfa.fragments.contact_us;

import static com.leon.hamrah_abfa.helpers.Constants.POINT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.adapters.recycler_view.BranchAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentContactBranchBinding;

import java.util.ArrayList;

public class ContactBranchFragment extends Fragment {
    private FragmentContactBranchBinding binding;
    private BranchAdapter adapter;

    public ContactBranchFragment() {
    }

    public static ContactBranchFragment newInstance() {
        return new ContactBranchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactBranchBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        final ArrayList<BranchViewModel> branches = new ArrayList<>();
        branches.add(new BranchViewModel("منطقه یک", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه یک", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه یک", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه یک", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه یک", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه یک", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه یک", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه یک", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        adapter = new BranchAdapter(requireContext(), branches);
        binding.recyclerViewBranch.setAdapter(adapter);
        binding.recyclerViewBranch.setLayoutManager(new LinearLayoutManager(requireContext()));
//        setRecyclerViewListener();
    }

    private void setRecyclerViewListener() {
        final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewBranch, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.updateSelectedService(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        binding.recyclerViewBranch.addOnItemTouchListener(listener);
    }
}