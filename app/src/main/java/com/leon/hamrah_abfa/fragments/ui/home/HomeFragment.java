package com.leon.hamrah_abfa.fragments.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.CardPagerAdapter;
import com.leon.hamrah_abfa.adapters.MenuAdapter;
import com.leon.hamrah_abfa.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private FragmentHomeBinding binding;
    private ICallback callback;

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeViewPager();
        final MenuAdapter adapter = new MenuAdapter(requireContext(), R.array.home_menu, R.array.home_icons);
        binding.gridViewMenu.setAdapter(adapter);
        binding.gridViewMenu.setOnItemClickListener(this);
    }

    private void initializeViewPager() {
        callback.createCardPagerAdapter();
        binding.viewPagerCard.setAdapter(callback.getCardPagerAdapter());
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


        float pageMargin = getResources().getDimensionPixelOffset(R.dimen.large_dp);
        float pageOffset = getResources().getDimensionPixelOffset(R.dimen.medium_dp);
        float px1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                getResources().getDimensionPixelOffset(R.dimen.low_dp),
                getResources().getDisplayMetrics());
//        RTLToast.success(requireContext(), (int) px1).show();

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        float px = getResources().getDimensionPixelSize(R.dimen.large_dp) * (metrics.densityDpi / 160f);

//        RTLToast.info(requireContext(), (int) px).show();

//        binding.viewPagerCard.setPageTransformer((page, position) -> {
//            int pageWidth = binding.viewPagerCard.getMeasuredWidth() -
//                    binding.viewPagerCard.getPaddingLeft() - binding.viewPagerCard.getPaddingRight();
//            final int padding = binding.viewPagerCard.getPaddingLeft();
//            float transformPos = (float) (page.getLeft() -
//                    (binding.viewPagerCard.getScrollX() + padding)) / pageWidth;
//            if (transformPos < -1) {
//                page.setScaleY(0.7f);
//            } else if (transformPos <= 1) {
//                page.setScaleY(1f);
//            } else {
//                page.setScaleY(0.7f);
//            }
//        });

        binding.viewPagerCard.setOffscreenPageLimit(1);
        final CompositePageTransformer cpt = new CompositePageTransformer();
        cpt.addTransformer(new MarginPageTransformer(20));
        cpt.addTransformer((page, position) -> page.setScaleY(0.80f + (1 - Math.abs(position)) * 0.20f));
        binding.viewPagerCard.setPageTransformer(cpt);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
    }
}