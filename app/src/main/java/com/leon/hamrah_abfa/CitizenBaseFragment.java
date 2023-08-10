package com.leon.hamrah_abfa;

import static com.leon.hamrah_abfa.helpers.Constants.CITIZEN_ACCOUNT_FRAGMENT;
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

import com.leon.hamrah_abfa.databinding.FragmentCitizenBaseBinding;
import com.leon.hamrah_abfa.fragments.citizen.ForbiddenViewModel;


public class CitizenBaseFragment extends Fragment implements View.OnClickListener {
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
        binding.buttonNext.setOnClickListener(this);
        binding.editTextType.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.edit_text_type) {
            showMenu(binding.editTextType);
        } else if (id == R.id.button_next) {
            if (checkInput())
                callback.displayView(CITIZEN_ACCOUNT_FRAGMENT);
        }
    }

    private boolean checkInput() {
        if (callback.getForbiddenViewModel().getDescription() == null ||
                callback.getForbiddenViewModel().getDescription().isEmpty()) {
            binding.editTextDescription.setError(getString(R.string.enter_description));
            binding.editTextDescription.requestFocus();
            warning(requireContext(), R.string.enter_description).show();
            return false;
        } else if (callback.getForbiddenViewModel().getType() == null ||
                callback.getForbiddenViewModel().getType().isEmpty()) {
            binding.editTextType.setError(getString(R.string.enter_status));
            binding.editTextType.requestFocus();
            warning(requireContext(), R.string.enter_status).show();
        }

        return true;
    }

    private void showMenu(View v) {
        final PopupMenu popup = new PopupMenu(requireActivity(), v, Gravity.TOP);
//        for (int i = 0; i < feedbackType.size(); i++)
        popup.getMenu().add("title1");
        popup.getMenu().add("title2");
        popup.getMenu().add("title3");
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
            callback.getForbiddenViewModel().setType(String.valueOf(menuItem.getTitle()));
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