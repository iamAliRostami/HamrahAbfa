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

import com.app.leon.moshtarak.adapters.recycler_view.NewsAdapter;
import com.app.leon.moshtarak.adapters.recycler_view.RecyclerItemClickListener;
import com.app.leon.moshtarak.databinding.FragmentNewsBinding;

import java.util.ArrayList;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private NewsAdapter adapter;
    private ICallback callback;

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        adapter = new NewsAdapter(requireContext(), callback.getNews());
        binding.recyclerViewNews.setAdapter(adapter);
        binding.recyclerViewNews.setLayoutManager(new LinearLayoutManager(requireContext()));
        setRecyclerViewListener();
    }

    private void setRecyclerViewListener() {
        final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewNews, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                MyApplication.getInstance().getApplicationComponent().MyDatabase().newsDao().updateNewsSeen(
//                        adapter.getNews(position).customId, true);
//                callback.setUnseenNewsNumber();
//                adapter.updateNews(position);
                callback.showNewsDialog(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        binding.recyclerViewNews.addOnItemTouchListener(listener);
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

        void showNewsDialog(int position);

        ArrayList<NewsViewModel> getNews();
    }
}