package com.leon.hamrah_abfa.fragments.ui.services;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.SERVICE_TYPE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.activities.ServiceActivity;
import com.leon.hamrah_abfa.adapters.CardPagerAdapter;
import com.leon.hamrah_abfa.adapters.ServicesMainAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentServiceBinding;

public class ServiceFragment extends Fragment {
    private FragmentServiceBinding binding;
    private ICallback callback;

    public ServiceFragment() {
    }

    public static ServiceFragment newInstance() {
        return new ServiceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeViewPager();
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        binding.recyclerViewMenu.setAdapter(new ServicesMainAdapter(requireContext(),
                R.array.services_main_menu, R.array.services_main_icons));
        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewMenu.addOnItemTouchListener(new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewMenu, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                itemClick(view, position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }
    private void initializeViewPager() {
        callback.createCardPagerAdapter();
        binding.viewPagerCard.setAdapter(callback.getCardPagerAdapter());
        binding.viewPagerCard.setOffscreenPageLimit(1);
        final CompositePageTransformer cpt = new CompositePageTransformer();
        cpt.addTransformer(new MarginPageTransformer(20));
        cpt.addTransformer((page, position) -> page.setScaleY(0.80f + (1 - Math.abs(position)) * 0.20f));
        binding.viewPagerCard.setPageTransformer(cpt);
    }

    private void itemClick(View view, int position) {
        final Intent intent = new Intent(view.getContext(), ServiceActivity.class);
        intent.putExtra(BILL_ID.getValue(), callback.getCurrentBillId(binding.viewPagerCard.getCurrentItem()));
        intent.putExtra(SERVICE_TYPE.getValue(), position);
        startActivity(intent);
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
        CardPagerAdapter getCardPagerAdapter();

        void createCardPagerAdapter();

        String getCurrentBillId(int position);
    }
}