package com.leon.hamrah_abfa.fragments.follow_request;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.recycler_view.RequestLevelAdapter;
import com.leon.hamrah_abfa.databinding.FragmentFollowRequestLevelsBinding;
import com.leon.hamrah_abfa.tables.RequestLevel;

import java.util.ArrayList;

public class FollowRequestLevelsFragment extends BottomSheetDialogFragment {
    private FragmentFollowRequestLevelsBinding binding;

    public FollowRequestLevelsFragment() {
    }

    public static FollowRequestLevelsFragment newInstance() {
        return new FollowRequestLevelsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFollowRequestLevelsBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeRecyclerView();
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

    private void initializeRecyclerView() {
        final ArrayList<RequestLevel> requestLevels = new ArrayList<>();

        requestLevels.add(new RequestLevel(1, "ثبت درخواست", "12/12/12", true, false));
        requestLevels.add(new RequestLevel(2, "ثبت ارزیاب", "12/12/12", true, false));
        requestLevels.add(new RequestLevel(3, "بررسی مدارک", "12/12/12", true, false));
        requestLevels.add(new RequestLevel(4, "مرحله 1", "12/12/12", false, true));
        requestLevels.add(new RequestLevel(5, "مرحله 2", "12/12/12", false, false));
        requestLevels.add(new RequestLevel(6, "مرحله 3", "12/12/12", false, false));
        requestLevels.add(new RequestLevel(7, "مرحله 4", "12/12/12", false, false));
        requestLevels.add(new RequestLevel(8, "مرحله 5", "12/12/12", false, false));
        requestLevels.add(new RequestLevel(9, "مرحله 6", "12/12/12", false, false));
        requestLevels.add(new RequestLevel(10, "مرحله 7", "12/12/12", false, false));

        final RequestLevelAdapter adapter = new RequestLevelAdapter(requireContext(), requestLevels);
        binding.recyclerViewLevel.setAdapter(adapter);
        binding.recyclerViewLevel.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}