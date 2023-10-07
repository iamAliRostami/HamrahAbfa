package com.app.leon.moshtarak.fragments.follow_request;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.adapters.recycler_view.RecyclerItemClickListener;
import com.app.leon.moshtarak.adapters.recycler_view.RequestDetailAdapter;
import com.app.leon.moshtarak.databinding.FragmentFollowRequestLevelsBinding;
import com.app.leon.moshtarak.enums.BundleEnum;
import com.app.leon.moshtarak.enums.FragmentTags;
import com.app.leon.moshtarak.fragments.dialog.WaitingFragment;
import com.app.leon.moshtarak.requests.follow_request.GetDetailHistoryRequest;
import com.app.leon.moshtarak.utils.ShowFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class FollowRequestLevelsFragment extends BottomSheetDialogFragment {
    private FragmentFollowRequestLevelsBinding binding;
    private DialogFragment fragment;
    private int trackNumber;
    private RequestDetailAdapter adapter;

    public FollowRequestLevelsFragment() {
    }

    public static FollowRequestLevelsFragment newInstance(int trackNumber) {
        FollowRequestLevelsFragment fragment = new FollowRequestLevelsFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putInt(BundleEnum.TRACK_NUMBER.getValue(), trackNumber);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trackNumber = getArguments().getInt(BundleEnum.TRACK_NUMBER.getValue());
            getArguments().clear();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFollowRequestLevelsBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        requestDetailHistory();
        binding.textViewTrack.setSelected(true);
        binding.textViewTrack.setText(String.valueOf(trackNumber));
        final LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                requireContext().getResources().getDisplayMetrics().heightPixels
                /*requireActivity().getWindowManager().getDefaultDisplay().getWidth() / 2*/);
        binding.relativeLayout.setLayoutParams(params);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View bottomSheet = view.findViewById(R.id.linear_layout_container);
        if (bottomSheet != null) {
            final DisplayMetrics displayMetrics = requireActivity().getResources().getDisplayMetrics();
            BottomSheetBehavior.from(bottomSheet).setPeekHeight(displayMetrics.heightPixels);
        }
    }

    private void requestDetailHistory() {
        progressStatus(new GetDetailHistoryRequest(requireContext(),
                new GetDetailHistoryRequest.ICallback() {
                    @Override
                    public void succeed(DetailHistory detailHistory) {
                        initializeRecyclerView(detailHistory.trackingOrderedInfos);
                    }

                    @Override
                    public void changeUI(boolean done) {
                        progressStatus(done);
                    }
                }, trackNumber).request());
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

    private void initializeRecyclerView(ArrayList<RequestDetail> detailHistory) {
        /*final RequestDetailAdapter */
        adapter = new RequestDetailAdapter(requireContext(), detailHistory);
        binding.recyclerViewLevel.setAdapter(adapter);
        binding.recyclerViewLevel.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewLevel.addOnItemTouchListener(new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewLevel, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (adapter.getId(position) != null)
                    ShowFragment.showFragmentDialogOnce(view.getContext(), FragmentTags.FOLLOW_REQUEST_ITEM.getValue(),
                            FollowRequestItemsFragment.newInstance(adapter.getId(position), adapter.getTitle(position)));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
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
                BottomSheetBehavior.from(bottomSheet).setPeekHeight((int) (displayMetrics.heightPixels * 0.99));
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