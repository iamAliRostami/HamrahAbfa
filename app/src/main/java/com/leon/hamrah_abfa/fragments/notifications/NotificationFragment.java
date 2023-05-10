package com.leon.hamrah_abfa.fragments.notifications;

import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.adapters.recycler_view.NotificationAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentNotificationBinding;

public class NotificationFragment extends Fragment {
    private ICallback callback;
    private FragmentNotificationBinding binding;
    private NotificationAdapter adapter;

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
        binding.recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void initializeRecyclerView() {
        adapter = new NotificationAdapter(requireContext(), callback.getBillId());
        binding.recyclerViewNotifications.setAdapter(adapter);
        setRecyclerViewListener();
    }

    private void setRecyclerViewListener() {
        final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewNotifications, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getApplicationComponent().MyDatabase().notificationDao().updateOnOffLoadSeen(
                        adapter.getNotification(position).customId, true);
                adapter.updateNotification(position);
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
        String getBillId();
    }
}