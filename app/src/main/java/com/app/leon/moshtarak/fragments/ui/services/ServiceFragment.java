package com.app.leon.moshtarak.fragments.ui.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.app.leon.moshtarak.activities.ServiceAfterActivity;
import com.app.leon.moshtarak.activities.ServiceBuyActivity;
import com.app.leon.moshtarak.adapters.fragment_state_adapter.CardPagerAdapter;
import com.app.leon.moshtarak.adapters.recycler_view.RecyclerItemClickListener;
import com.app.leon.moshtarak.adapters.recycler_view.ServicesMainAdapter;
import com.app.leon.moshtarak.databinding.FragmentServiceBinding;
import com.app.leon.moshtarak.enums.BundleEnum;
import com.app.leon.moshtarak.R;

public class ServiceFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewPager();
    }

    private void initialize() {
        callback.createCardPagerAdapter();
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        binding.recyclerViewMenu.setAdapter(new ServicesMainAdapter(requireContext(),
                R.array.services_main_menu, R.array.services_main_icons));
        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewMenu.addOnItemTouchListener(new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewMenu, this));
    }

    @Override
    public void onItemClick(View view, int position) {
        if (callback.isEmpty())
            return;
        Intent intent;
        if (position == 0)
            intent = new Intent(view.getContext(), ServiceBuyActivity.class);
        else intent = new Intent(view.getContext(), ServiceAfterActivity.class);
//        intent.putExtra(BILL_ID.getValue(), callback.getCurrentId(binding.viewPagerCard.getCurrentItem()));
        intent.putExtra(BundleEnum.UUID.getValue(), callback.getCurrentId(binding.viewPagerCard.getCurrentItem()));
        intent.putExtra(BundleEnum.BILL_ID.getValue(), callback.getCardPagerAdapter().getCurrentBillId(binding.viewPagerCard.getCurrentItem()));
        intent.putExtra(BundleEnum.SERVICE_TYPE.getValue(), position);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    private void initializeViewPager() {
        binding.viewPagerCard.setAdapter(callback.getCardPagerAdapter());
        binding.viewPagerCard.setOffscreenPageLimit(1);
        final CompositePageTransformer cpt = new CompositePageTransformer();
        cpt.addTransformer(new MarginPageTransformer(15));
        cpt.addTransformer((page, position) -> page.setScaleY(0.80f + (1 - Math.abs(position)) * 0.20f));
        binding.viewPagerCard.setPageTransformer(cpt);
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
        void createCardPagerAdapter();

        CardPagerAdapter getCardPagerAdapter();

        String getCurrentId(int position);

        boolean isEmpty();
    }
}