package com.app.leon.moshtarak.fragments.notifications;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.app.leon.moshtarak.base_items.BaseBottomSheetFragment;
import com.app.leon.moshtarak.databinding.FragmentNotificationDetailBinding;
import com.app.leon.moshtarak.enums.BundleEnum;

import org.jetbrains.annotations.NotNull;

public class NotificationDetailFragment extends BaseBottomSheetFragment implements View.OnClickListener {
    private FragmentNotificationDetailBinding binding;
    private ICallback callback;
    private int position;

    public NotificationDetailFragment() {
    }

    public static NotificationDetailFragment newInstance(int position) {
        NotificationDetailFragment fragment = new NotificationDetailFragment();
        Bundle args = new Bundle();
        args.putInt(BundleEnum.POSITION.getValue(), position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(BundleEnum.POSITION.getValue());
            getArguments().clear();
        }
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentNotificationDetailBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getNotification(position));
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonSeen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        callback.setNotificationSeen(position);
        dismiss();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public interface ICallback {
        NotificationsViewModel getNotification(int position);

        void setNotificationSeen(int position);
    }
}