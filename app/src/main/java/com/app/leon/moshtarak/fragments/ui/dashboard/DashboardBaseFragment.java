package com.app.leon.moshtarak.fragments.ui.dashboard;

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
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.activities.DashboardActivity;
import com.app.leon.moshtarak.adapters.fragment_state_adapter.CardPagerAdapter;
import com.app.leon.moshtarak.databinding.FragmentDashboardBaseBinding;
import com.app.leon.moshtarak.enums.BundleEnum;

public class DashboardBaseFragment extends Fragment implements View.OnClickListener {
    private FragmentDashboardBaseBinding binding;
    private ICallback callback;

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
                intent.putExtra(BundleEnum.UUID.getValue(), callback.getCurrentId(binding.viewPagerCard.getCurrentItem()));
                intent.putExtra(BundleEnum.BILL_ID.getValue(), callback.getCardPagerAdapter().getCurrentBillId(binding.viewPagerCard.getCurrentItem()));
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

        boolean isEmpty();
    }
}