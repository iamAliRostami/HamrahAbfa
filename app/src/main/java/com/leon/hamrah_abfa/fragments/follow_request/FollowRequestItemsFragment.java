package com.leon.hamrah_abfa.fragments.follow_request;

import static com.leon.hamrah_abfa.enums.BundleEnum.ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.NAME;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.leon.hamrah_abfa.adapters.recycler_view.RequestDetailInfoAdapter;
import com.leon.hamrah_abfa.base_items.BaseBottomSheetFragment;
import com.leon.hamrah_abfa.databinding.FragmentFollowRequestItemsBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.requests.follow_request.GetItemHistoryRequest;

import java.util.ArrayList;

public class FollowRequestItemsFragment extends BaseBottomSheetFragment {
    private FragmentFollowRequestItemsBinding binding;
    private String id;
    private String name;
    private DialogFragment fragment;

    public FollowRequestItemsFragment() {
    }

    public static FollowRequestItemsFragment newInstance(String id, String name) {
        FollowRequestItemsFragment fragment = new FollowRequestItemsFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putString(ID.getValue(), id);
        args.putString(NAME.getValue(), name);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ID.getValue());
            name = getArguments().getString(NAME.getValue());
            getArguments().clear();
        }
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentFollowRequestItemsBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.textViewTitle.setText(name);
        requestDetailItemHistory();
    }

    private void requestDetailItemHistory() {
        boolean isOnline = new GetItemHistoryRequest(requireContext(), new GetItemHistoryRequest.ICallback() {

            @Override
            public void succeed(DetailHistoryItem itemInfo) {
                initializeRecyclerView(itemInfo.trackingValueKeys);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, id).request();
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

    private void initializeRecyclerView(ArrayList<TrackItemInfo> trackItems) {
        RequestDetailInfoAdapter adapter = new RequestDetailInfoAdapter(requireContext(), trackItems);
        binding.recyclerViewInfo.setAdapter(adapter);
        binding.recyclerViewInfo.setLayoutManager(new LinearLayoutManager(requireContext()));

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog FollowRequestLevelsDialog = super.onCreateDialog(savedInstanceState);
        FollowRequestLevelsDialog.setOnShowListener(dialog -> {
            final BottomSheetDialog bottomDialog = (BottomSheetDialog) dialog;
            final FrameLayout bottomSheet = bottomDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                final DisplayMetrics displayMetrics = requireActivity().getResources().getDisplayMetrics();
                BottomSheetBehavior.from(bottomSheet).setPeekHeight((int) (displayMetrics.heightPixels * 0.8));
            }
        });
        return FollowRequestLevelsDialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}