package com.leon.hamrah_abfa.fragments.ui.home;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.LAST_PAGE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.leon.hamrah_abfa.activities.ChangeMobileActivity;
import com.leon.hamrah_abfa.activities.ContactUsActivity;
import com.leon.hamrah_abfa.activities.FollowRequestActivity;
import com.leon.hamrah_abfa.activities.PayBillActivity;
import com.leon.hamrah_abfa.activities.SetCounterNumberActivity;
import com.leon.hamrah_abfa.adapters.base_adapter.MenuAdapter;
import com.leon.hamrah_abfa.adapters.fragment_state_adapter.CardPagerAdapter;
import com.leon.hamrah_abfa.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private FragmentHomeBinding binding;
    private ICallback callback;

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeViewPager();
        initializeGridView();
    }

    private void initializeGridView() {
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            startActivity(createIntent(PayBillActivity.class));
        } else if (position == 1) {
            startActivity(createIntent(SetCounterNumberActivity.class));
        } else if (position == 2) {
        } else if (position == 5) {
            final Intent intent = createIntent(FollowRequestActivity.class);
            intent.putExtra(LAST_PAGE.getValue(), binding.viewPagerCard.getCurrentItem() ==
                    (callback.getCardPagerAdapter().getItemCount() - 1));
            startActivity(intent);
        } else if (position == 7) {
            startActivity(createIntent(ChangeMobileActivity.class));
        } else if (position == 8) {
            startActivity(createIntent(ContactUsActivity.class));
        }
    }

    private Intent createIntent(Class<?> cls) {
        final Intent intent = new Intent(requireContext(), cls);
        intent.putExtra(BILL_ID.getValue(), callback.getCurrentBillId(binding.viewPagerCard.getCurrentItem()));
        return intent;
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

        void setPosition(int position);
    }
}