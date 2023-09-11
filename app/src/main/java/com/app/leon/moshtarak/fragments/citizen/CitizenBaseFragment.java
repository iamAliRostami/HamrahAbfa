package com.app.leon.moshtarak.fragments.citizen;

import static com.leon.toast.RTLToast.warning;

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

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.databinding.FragmentCitizenBaseBinding;
import com.app.leon.moshtarak.helpers.Constants;

import java.util.ArrayList;
import java.util.Arrays;


public class CitizenBaseFragment extends Fragment {
    private final ArrayList<String> titles = new ArrayList<>();
    private FragmentCitizenBaseBinding binding;
    private ICallback callback;

    public CitizenBaseFragment() {
    }

    public static CitizenBaseFragment newInstance() {
        return new CitizenBaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCitizenBaseBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getForbiddenViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        titles.clear();
        if (callback.getForbiddenViewModel().getParentType().equals(Arrays.asList(getResources().getStringArray(R.array.citizen_menu)).get(1))) {
            titles.add("شستشوی معابر");
            titles.add("نشت کولر");
            titles.add("آبیاری فضای سبز");
            titles.add("پر سازی استخر");
            titles.add("شستشوی خودرو");
            titles.add("شستشوی فرش");
        } else {
            titles.add("پمپ مستقیم به شبکه");
            titles.add("سایر");
        }
        binding.buttonNext.setOnClickListener(v -> {
            if (checkInputs())
                callback.displayView(Constants.CITIZEN_ACCOUNT_FRAGMENT);
        });
        binding.editTextType.setOnClickListener(v -> showMenu(binding.editTextType));
        binding.textLayoutType.setEndIconOnClickListener(v -> showMenu(binding.editTextType));
    }

    private boolean checkInputs() {
        if (callback.getForbiddenViewModel().getType() == null ||
                callback.getForbiddenViewModel().getType().isEmpty()) {
            warning(requireContext(), R.string.enter_status).show();
            binding.editTextType.setError(getString(R.string.enter_status));
            binding.editTextType.requestFocus();
            return false;
        } else if (callback.getForbiddenViewModel().getDescription() == null ||
                callback.getForbiddenViewModel().getDescription().trim().isEmpty()) {
            warning(requireContext(), R.string.enter_description).show();
            binding.editTextDescription.setError(getString(R.string.enter_description));
            binding.editTextDescription.requestFocus();
            return false;
        }
        return true;
    }

    private void showMenu(View v) {
        final PopupMenu popup = new PopupMenu(requireActivity(), v, Gravity.TOP);
        for (int i = 0; i < titles.size(); i++)
            popup.getMenu().add(titles.get(i));
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
            binding.editTextType.setText(menuItem.getTitle());
            return true;
        });
        popup.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        void displayView(int position);

        ForbiddenViewModel getForbiddenViewModel();
    }
}