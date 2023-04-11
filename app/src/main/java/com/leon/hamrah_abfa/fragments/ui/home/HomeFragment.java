package com.leon.hamrah_abfa.fragments.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.CardPagerAdapter;
import com.leon.hamrah_abfa.adapters.MenuAdapter;
import com.leon.hamrah_abfa.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private FragmentHomeBinding binding;
    private CardPagerAdapter cardPagerAdapter = new CardPagerAdapter(requireActivity());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.viewPagerCard.setAdapter(cardPagerAdapter);

        binding.viewPagerCard.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        final MenuAdapter adapter = new MenuAdapter(requireContext(), R.array.home_menu, R.array.home_icons);
        binding.gridViewMenu.setAdapter(adapter);
        binding.gridViewMenu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private void updateCard(){
        cardPagerAdapter.update();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}