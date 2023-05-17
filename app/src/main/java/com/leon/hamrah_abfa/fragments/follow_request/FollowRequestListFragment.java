package com.leon.hamrah_abfa.fragments.follow_request;

import static com.leon.hamrah_abfa.enums.BundleEnum.FINISHED;
import static com.leon.hamrah_abfa.enums.FragmentTags.FOLLOW_REQUEST_LEVEL;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.adapters.recycler_view.RequestAdapter;
import com.leon.hamrah_abfa.databinding.FragmentFollowRequestListBinding;
import com.leon.hamrah_abfa.tables.Request;

import java.util.ArrayList;
import java.util.Arrays;

public class FollowRequestListFragment extends Fragment implements View.OnClickListener {
    private FragmentFollowRequestListBinding binding;
    private boolean finished;

    public FollowRequestListFragment() {
    }

    public static FollowRequestListFragment newInstance(boolean finished) {
        final FollowRequestListFragment fragment = new FollowRequestListFragment();
        final Bundle args = new Bundle();
        args.putBoolean(FINISHED.getValue(), finished);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            finished = getArguments().getBoolean(FINISHED.getValue());
        }
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
        final ArrayList<Request> requests = new ArrayList<>(Arrays.asList(
                new Request(requireContext(), 0, "121212", "12/12/12/"),
                new Request(requireContext(), 1, "121212", "12/12/12/"),
                new Request(requireContext(), 2, "121212", "12/12/12/"),
                new Request(requireContext(), 1, "121212", "12/12/12/"),
                new Request(requireContext(), 1, "121212", "12/12/12/"),
                new Request(requireContext(), 2, "121212", "12/12/12/")
        ));
        final RequestAdapter adapter = new RequestAdapter(requireContext(), requests);
        binding.recyclerViewRequest.setAdapter(adapter);
        binding.recyclerViewRequest.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewRequest.addOnItemTouchListener(new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewRequest, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ShowFragmentDialogOnce(view.getContext(), FOLLOW_REQUEST_LEVEL.getValue(),
                        FollowRequestLevelsFragment.newInstance());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.float_button_search) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}