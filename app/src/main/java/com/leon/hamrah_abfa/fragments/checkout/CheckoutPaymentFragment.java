package com.leon.hamrah_abfa.fragments.checkout;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.recycler_view.CheckoutPaymentAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentCheckoutPaymentBinding;

public class CheckoutPaymentFragment extends Fragment implements View.OnClickListener {
    private FragmentCheckoutPaymentBinding binding;
    private CheckoutPaymentAdapter adapter;
    private ICallback callback;

    public CheckoutPaymentFragment() {
    }

    public static CheckoutPaymentFragment newInstance() {
        return new CheckoutPaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutPaymentBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.editTextNumberType.setOnClickListener(this);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        adapter = new CheckoutPaymentAdapter(requireContext());
        binding.recyclerViewCheckoutPayment.setAdapter(adapter);
        binding.recyclerViewCheckoutPayment.setLayoutManager(new LinearLayoutManager(requireContext()));
        setRecyclerViewListener();
    }

    private void setRecyclerViewListener() {
        final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewCheckoutPayment, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        binding.recyclerViewCheckoutPayment.addOnItemTouchListener(listener);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.edit_text_number_type) {
            showMenu(binding.editTextNumberType, R.menu.number_type_menu);
        }
    }

    private void showMenu(View v, @MenuRes int menuRes) {
        final PopupMenu popup = new PopupMenu(requireActivity(), v, Gravity.TOP);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        if (popup.getMenu() instanceof MenuBuilder) {
            final MenuBuilder menuBuilder = (MenuBuilder) popup.getMenu();
            //noinspection RestrictedApi
            menuBuilder.setOptionalIconsVisible(true);
            //noinspection RestrictedApi
            for (MenuItem item : menuBuilder.getVisibleItems()) {
                final int iconMarginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        R.dimen.small_dp, getResources().getDisplayMetrics());
                if (item.getIcon() != null) {
                    item.setIcon(new InsetDrawable(item.getIcon(), iconMarginPx, 0, iconMarginPx, 0));
                }
            }
        }
        popup.setOnMenuItemClickListener(menuItem -> {
            ((TextView) v).setText(menuItem.getTitle());
            return true;
        });
        popup.show();
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
        String getBillId();
    }
}