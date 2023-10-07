package com.app.leon.moshtarak.fragments.follow_request;

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

import com.app.leon.moshtarak.adapters.recycler_view.RequestDetailInfoAdapter;
import com.app.leon.moshtarak.base_items.BaseBottomSheetFragment;
import com.app.leon.moshtarak.databinding.FragmentFollowRequestItemsBinding;
import com.app.leon.moshtarak.enums.BundleEnum;
import com.app.leon.moshtarak.enums.FragmentTags;
import com.app.leon.moshtarak.fragments.dialog.WaitingFragment;
import com.app.leon.moshtarak.requests.follow_request.GetItemHistoryRequest;
import com.app.leon.moshtarak.utils.ShowFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
        args.putString(BundleEnum.ID.getValue(), id);
        args.putString(BundleEnum.NAME.getValue(), name);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(BundleEnum.ID.getValue());
            name = getArguments().getString(BundleEnum.NAME.getValue());
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
        progressStatus(new GetItemHistoryRequest(requireContext(),
                new GetItemHistoryRequest.ICallback() {
                    @Override
                    public void succeed(DetailHistoryItem itemInfo) {
                        initializeRecyclerView(itemInfo.trackingValueKeys);
                    }

                    @Override
                    public void changeUI(boolean done) {
                        progressStatus(done);
                    }
                }, id).request());
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                ShowFragment.showFragmentDialogOnce(requireContext(), FragmentTags.WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null && fragment.getShowsDialog()) {
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