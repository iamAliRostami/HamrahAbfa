package com.leon.hamrah_abfa.fragments.ui.dashboard;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.UUID;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.activities.DashboardActivity;
import com.leon.hamrah_abfa.adapters.fragment_state_adapter.CardPagerAdapter;
import com.leon.hamrah_abfa.databinding.FragmentDashboardBaseBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.requests.GetBillSummaryRequest;

import java.util.ArrayList;

public class DashboardBaseFragment extends Fragment implements View.OnClickListener {
    private FragmentDashboardBaseBinding binding;
    private ICallback callback;
    private String uuid;
    private int id;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBaseBinding.inflate(inflater, container, false);
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
        binding.buttonStart.setOnClickListener(this);
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
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_start) {
            if (!callback.isEmpty()) {
                Intent intent = new Intent(requireContext(), DashboardActivity.class);
                intent.putExtra(UUID.getValue(), callback.getCurrentId(binding.viewPagerCard.getCurrentItem()));
                intent.putExtra(BILL_ID.getValue(), callback.getCardPagerAdapter().getCurrentBillId(binding.viewPagerCard.getCurrentItem()));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        void createCardPagerAdapter();

        String getCurrentId(int position);

        CardPagerAdapter getCardPagerAdapter();

        void setPosition(int position);

        boolean isEmpty();
    }
}