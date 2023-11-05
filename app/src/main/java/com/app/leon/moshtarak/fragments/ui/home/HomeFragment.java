package com.app.leon.moshtarak.fragments.ui.home;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.activities.ChangeMobileActivity;
import com.app.leon.moshtarak.activities.CheckoutActivity;
import com.app.leon.moshtarak.activities.CitizenActivity;
import com.app.leon.moshtarak.activities.ContactUsActivity;
import com.app.leon.moshtarak.activities.FollowRequestActivity;
import com.app.leon.moshtarak.activities.LastBillActivity;
import com.app.leon.moshtarak.activities.PayBillActivity;
import com.app.leon.moshtarak.activities.SetCounterNumberActivity;
import com.app.leon.moshtarak.activities.UsageHistoryActivity;
import com.app.leon.moshtarak.adapters.fragment_state_adapter.CardPagerAdapter;
import com.app.leon.moshtarak.adapters.recycler_view.RecyclerItemClickListener;
import com.app.leon.moshtarak.adapters.recycler_view.TileAdapter;
import com.app.leon.moshtarak.databinding.FragmentHomeBinding;
import com.app.leon.moshtarak.enums.BundleEnum;

public class HomeFragment extends Fragment {
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewPager();
    }

    private void initialize() {
        callback.createCardPagerAdapter();
        initializeGridView();
    }

    private void initializeGridView() {
        final TileAdapter tileAdapter = new TileAdapter(requireContext(), R.array.home_menu, R.array.home_icons);
        binding.recyclerViewMenu.setLayoutManager(new GridLayoutManager(requireContext(), 3) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.width = getWidth() / getSpanCount();
                lp.height = getHeight() / getSpanCount();
                return true;
            }
        });
        binding.recyclerViewMenu.setHasFixedSize(true);
        binding.recyclerViewMenu.setAdapter(tileAdapter);
        binding.recyclerViewMenu.addOnItemTouchListener(getListener());
    }

    private RecyclerItemClickListener getListener() {
        return new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewMenu, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 8) {
                    startActivity(new Intent(requireContext(), ContactUsActivity.class));
                    return;
                } else if (position == 3) {
                    startActivity(new Intent(requireContext(), CitizenActivity.class));
                    return;
                }
                if (callback.isEmpty())
                    return;
                if (position == 0) {
                    startActivity(createIntent(PayBillActivity.class));
                } else if (position == 1) {
                    startActivity(createIntent(SetCounterNumberActivity.class));
                } else if (position == 2) {
                    startActivity(createIntent(UsageHistoryActivity.class));
                } else if (position == 4) {
                    startActivity(createIntent(CheckoutActivity.class));
                } else if (position == 5) {
                    startActivity(createIntent(FollowRequestActivity.class));
                } else if (position == 6) {
                    startActivity(createIntent(LastBillActivity.class));
                } else if (position == 7) {
                    startActivity(createIntent(ChangeMobileActivity.class));
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initializeViewPager() {
        binding.viewPagerCard.setAdapter(callback.getCardPagerAdapter());
        binding.viewPagerCard.setOffscreenPageLimit(1);
        final CompositePageTransformer cpt = new CompositePageTransformer();
        cpt.addTransformer(new MarginPageTransformer(15));
        cpt.addTransformer((page, position) -> page.setScaleY(0.80f + (1 - Math.abs(position)) * 0.20f));
        binding.viewPagerCard.setPageTransformer(cpt);
    }

    private Intent createIntent(Class<?> cls) {
        final Intent intent = new Intent(requireContext(), cls);
        intent.putExtra(BundleEnum.UUID.getValue(), callback.getCurrentId(binding.viewPagerCard.getCurrentItem()));
        intent.putExtra(BundleEnum.BILL_ID.getValue(), callback.getCardPagerAdapter().getCurrentBillId(binding.viewPagerCard.getCurrentItem()));
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
        void createCardPagerAdapter();

        String getCurrentId(int position);

        CardPagerAdapter getCardPagerAdapter();

        boolean isEmpty();
    }
}