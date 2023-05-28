package com.leon.hamrah_abfa.fragments.contact_us;

import static com.leon.hamrah_abfa.helpers.Constants.POINT;
import static com.leon.toast.RTLToast.warning;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.recycler_view.BranchAdapter;
import com.leon.hamrah_abfa.databinding.FragmentContactBranchBinding;

import java.util.ArrayList;

public class ContactBranchFragment extends Fragment implements TextWatcher {
    private FragmentContactBranchBinding binding;
    private BranchAdapter adapter;
    private final ArrayList<BranchViewModel> branches = new ArrayList<>();

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
        binding.editTextSearch.addTextChangedListener(this);
    }

    private void initializeRecyclerView() {
        branches.add(new BranchViewModel("منطقه یک", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه دو", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه سه", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه چهار", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه پنج", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه شش", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه هفت", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        branches.add(new BranchViewModel("منطقه هشت", "رضا رضایی", "منطقه یک",
                "03133333333", "03133333333", "اصفهان - خیابان اصفهان - کوچه اصفهان - پلاک اصفهان",
                "1234567890", POINT));
        adapter = new BranchAdapter(requireContext(), branches);
        binding.recyclerViewBranch.setAdapter(adapter);
        binding.recyclerViewBranch.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (binding.editTextSearch.getEditableText() == s) {
            if (s.toString().length() == 0) {
                adapter.filterList(branches);
                return;
            }
            filter(s.toString());
        }
    }

    private void filter(String text) {
        final ArrayList<BranchViewModel> branchesTemp = new ArrayList<>();
        for (BranchViewModel branch : branches) {
            if (branch.getName().toLowerCase().contains(text.toLowerCase()))
                branchesTemp.add(branch);
        }
        if (branchesTemp.isEmpty()) {
            warning(requireContext(), R.string.not_found).show();
        } else {
            adapter.filterList(branchesTemp);
        }
    }
}