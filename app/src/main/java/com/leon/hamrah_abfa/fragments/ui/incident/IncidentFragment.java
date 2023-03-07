package com.leon.hamrah_abfa.fragments.ui.incident;

import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentIncidentBinding;

public class IncidentFragment extends Fragment implements View.OnClickListener {
    private FragmentIncidentBinding binding;

    public IncidentFragment() {
    }

    public static IncidentFragment newInstance() {
        return new IncidentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIncidentBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
//        binding.menu.setOnClickListener(this);
        binding.textViewIncidentType.setOnClickListener(this);
    }

    private void showMenu(View v, @MenuRes int menuRes) {
        PopupMenu popup = new PopupMenu(requireActivity(), v);
        // Inflating the Popup using xml file
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        // There is no public API to make icons show on menus.
        // IF you need the icons to show this works however it's discouraged to rely on library only
        // APIs since they might disappear in future versions.
//        if (popup.getMenu() instanceof MenuBuilder) {
//            MenuBuilder menuBuilder = (MenuBuilder) popup.getMenu();
//            //noinspection RestrictedApi
//            menuBuilder.setOptionalIconsVisible(true);
//            //noinspection RestrictedApi
//            for (MenuItem item : menuBuilder.getVisibleItems()) {
//                int iconMarginPx =
//                        (int)
//                                TypedValue.applyDimension(
//                                        TypedValue.COMPLEX_UNIT_DIP, R.dimen.low_dp, getResources().getDisplayMetrics());
//
//                if (item.getIcon() != null) {
//                    item.setIcon(new InsetDrawable(item.getIcon(), iconMarginPx, 0, iconMarginPx, 0));
//                }
//            }
//        }
//        popup.setOnMenuItemClickListener(
//                menuItem -> {
//                    Snackbar.make(
//                                    requireActivity().findViewById(android.R.id.content),
//                                    menuItem.getTitle(),
//                                    Snackbar.LENGTH_LONG)
//                            .show();
//                    return true;
//                });
        popup.show();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (/*id == R.id.menu || */id == R.id.text_view_incident_type) {
            Log.e("here", "menu");
            showMenu(binding.getRoot(), R.menu.incident_menu);
        }
    }
}