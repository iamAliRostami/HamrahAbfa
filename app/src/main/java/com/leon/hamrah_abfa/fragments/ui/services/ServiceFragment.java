package com.leon.hamrah_abfa.fragments.ui.services;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.SERVICE_TYPE;
import static com.leon.hamrah_abfa.enums.BundleEnum.UUID;

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
import androidx.viewpager2.widget.ViewPager2;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.activities.ServiceAfterActivity;
import com.leon.hamrah_abfa.activities.ServiceBuyActivity;
import com.leon.hamrah_abfa.adapters.fragment_state_adapter.CardPagerAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.adapters.recycler_view.ServicesMainAdapter;
import com.leon.hamrah_abfa.databinding.FragmentServiceBinding;

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
        intent.putExtra(UUID.getValue(), callback.getCurrentId(binding.viewPagerCard.getCurrentItem()));
        intent.putExtra(BILL_ID.getValue(), callback.getCardPagerAdapter().getCurrentBillId(binding.viewPagerCard.getCurrentItem()));
        intent.putExtra(SERVICE_TYPE.getValue(), position);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    private void initializeViewPager() {
        binding.viewPagerCard.setAdapter(callback.getCardPagerAdapter());
        binding.viewPagerCard.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                callback.setPosition(position);
            }
        });
        binding.viewPagerCard.setOffscreenPageLimit(1);
        final CompositePageTransformer cpt = new CompositePageTransformer();
        cpt.addTransformer(new MarginPageTransformer(20));
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

        void setPosition(int position);

        boolean isEmpty();
    }
}