package com.leon.hamrah_abfa.fragments.notifications;

import static com.leon.hamrah_abfa.enums.BundleEnum.POSITION;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.leon.hamrah_abfa.base_items.BaseBottomSheetFragment;
import com.leon.hamrah_abfa.databinding.FragmentNotificationMessageDetailBinding;

import org.jetbrains.annotations.NotNull;

public class NotificationMessageDetailFragment extends BaseBottomSheetFragment implements View.OnClickListener {
    private FragmentNotificationMessageDetailBinding binding;
    private ICallback callback;
    private int position;

    public NotificationMessageDetailFragment() {
    }

    public static NotificationMessageDetailFragment newInstance(int position) {
        NotificationMessageDetailFragment fragment = new NotificationMessageDetailFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION.getValue(), position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION.getValue());
            getArguments().clear();
        }
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentNotificationMessageDetailBinding.inflate(inflater, container, false);
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
        if (id == v.getId()) {
            callback.setNotificationSeen(position);
            dismiss();
        }
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