package com.app.leon.moshtarak.fragments.notifications;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.leon.moshtarak.adapters.recycler_view.NotificationAdapter;
import com.app.leon.moshtarak.adapters.recycler_view.RecyclerItemClickListener;
import com.app.leon.moshtarak.databinding.FragmentNotificationBinding;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {
    private FragmentNotificationBinding binding;
    private NotificationAdapter adapter;
    private ICallback callback;

    public NotificationFragment() {
    }

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        adapter = new NotificationAdapter(requireContext(), callback.getNotifications());
        binding.recyclerViewNotifications.setAdapter(adapter);
        binding.recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(requireContext()));
        setRecyclerViewListener();
    }

    private void setRecyclerViewListener() {
        final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewNotifications, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                callback.setNotificationSeen(position);
                callback.showMessageDialog(position);
                adapter.updateNotificationSeen(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        binding.recyclerViewNotifications.addOnItemTouchListener(listener);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface ICallback {
        void setNotificationSeen(int position);

        ArrayList<NotificationsViewModel> getNotifications();

        void showMessageDialog(int position);
    }
}