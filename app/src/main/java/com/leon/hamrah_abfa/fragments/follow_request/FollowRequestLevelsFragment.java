package com.leon.hamrah_abfa.fragments.follow_request;

import static com.leon.hamrah_abfa.enums.BundleEnum.TRACK_NUMBER;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

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

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.recycler_view.RequestLevelAdapter;
import com.leon.hamrah_abfa.databinding.FragmentFollowRequestLevelsBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.requests.follow_request.GetDetailHistoryRequest;

import java.util.ArrayList;

public class FollowRequestLevelsFragment extends BottomSheetDialogFragment {
    private FragmentFollowRequestLevelsBinding binding;
    private DialogFragment fragment;
    private int trackNumber;

    public FollowRequestLevelsFragment() {
    }

    public static FollowRequestLevelsFragment newInstance(int trackNumber) {
        FollowRequestLevelsFragment fragment = new FollowRequestLevelsFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putInt(TRACK_NUMBER.getValue(), trackNumber);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trackNumber = getArguments().getInt(TRACK_NUMBER.getValue());
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
        boolean isOnline = new GetDetailHistoryRequest(requireContext(), new GetDetailHistoryRequest.ICallback() {
            @Override
            public void succeed(DetailHistory detailHistory) {
                initializeRecyclerView(detailHistory.trackingOrderedInfos);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, trackNumber).request();
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

    private void initializeRecyclerView(ArrayList<RequestLevel> detailHistory) {
//        final ArrayList<RequestLevel> requestLevels = new ArrayList<>();
//        requestLevels.add(new RequestLevel(1, "ثبت درخواست", "12/12/12"));
//        requestLevels.add(new RequestLevel(2, "ثبت ارزیاب", "12/12/12"));
//        requestLevels.add(new RequestLevel(3, "بررسی مدارک", "12/12/12"));
//        requestLevels.add(new RequestLevel(4, "مرحله 1", "12/12/12"));
//        requestLevels.add(new RequestLevel(5, "مرحله 2", "12/12/12"));
//        requestLevels.add(new RequestLevel(6, "مرحله 3", "12/12/12"));
//        requestLevels.add(new RequestLevel(7, "مرحله 4", "12/12/12"));
//        requestLevels.add(new RequestLevel(8, "مرحله 5", "12/12/12"));
//        requestLevels.add(new RequestLevel(9, "مرحله 6", "12/12/12"));
//        requestLevels.add(new RequestLevel(10, "مرحله 7", "12/12/12"));

        final RequestLevelAdapter adapter = new RequestLevelAdapter(requireContext(), detailHistory);
        binding.recyclerViewLevel.setAdapter(adapter);
        binding.recyclerViewLevel.setLayoutManager(new LinearLayoutManager(requireContext()));
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