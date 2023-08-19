package com.leon.hamrah_abfa.fragments.contact_us;

import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.warning;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.recycler_view.BranchAdapter;
import com.leon.hamrah_abfa.databinding.FragmentContactBranchBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.requests.contact_us.GetZoneRequest;

import java.util.ArrayList;

public class ContactBranchFragment extends Fragment implements TextWatcher {
    private final ContactBranch branch = new ContactBranch();
    private FragmentContactBranchBinding binding;
    private DialogFragment fragment;
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
        requestZone();
        binding.editTextSearch.addTextChangedListener(this);
    }

    private void requestZone() {
        boolean isOnline = new GetZoneRequest(requireContext(), new GetZoneRequest.ICallback() {

            @Override
            public void succeed(ArrayList<BranchViewModel> branches) {
                branch.zoneInfoDtos.addAll(branches);
                initializeRecyclerView();
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }).request();
        progressStatus(isOnline);
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                showFragmentDialogOnce(requireContext(), WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void initializeRecyclerView() {

        adapter = new BranchAdapter(requireContext(), branch.zoneInfoDtos);
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
                adapter.filterList(branch.zoneInfoDtos);
                return;
            }
            filter(s.toString());
        }
    }

    private void filter(String text) {
        final ArrayList<BranchViewModel> branchesTemp = new ArrayList<>();
        for (BranchViewModel branch : branch.zoneInfoDtos) {
            if (branch.getZoneTitle().toLowerCase().contains(text.toLowerCase()))
                branchesTemp.add(branch);
        }
        if (branchesTemp.isEmpty()) {
            warning(requireContext(), R.string.not_found).show();
        } else {
            adapter.filterList(branchesTemp);
        }
    }
}