package com.app.leon.moshtarak.fragments.follow_request;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.leon.moshtarak.adapters.recycler_view.RecyclerItemClickListener;
import com.app.leon.moshtarak.adapters.recycler_view.RequestHistoryAdapter;
import com.app.leon.moshtarak.databinding.FragmentFollowRequestListBinding;
import com.app.leon.moshtarak.enums.FragmentTags;
import com.app.leon.moshtarak.utils.ShowFragment;

import java.util.ArrayList;

public class FollowRequestListFinishedFragment extends Fragment implements View.OnClickListener {
    private FragmentFollowRequestListBinding binding;
    private ICallback callback;

    public FollowRequestListFinishedFragment() {
    }

    public static FollowRequestListFinishedFragment newInstance() {
        return new FollowRequestListFinishedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFollowRequestListBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        binding.recyclerViewRequest.setAdapter(callback.getFinishedAdapter());
        binding.recyclerViewRequest.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewRequest.addOnItemTouchListener(new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewRequest, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                ShowFragment.showFragmentDialogOnce(view.getContext(), FragmentTags.FOLLOW_REQUEST_LEVEL.getValue(),
                        FollowRequestLevelsFragment.newInstance(callback.getFinishedTrackNumber(position)));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            callback = (ICallback) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface ICallback {
        ArrayList<RequestInfo> getRequestInfoFinished();

        RequestHistoryAdapter getFinishedAdapter();

        int getFinishedTrackNumber(int position);
    }
}