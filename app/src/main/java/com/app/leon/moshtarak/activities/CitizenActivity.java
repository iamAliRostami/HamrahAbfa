package com.app.leon.moshtarak.activities;

import static com.app.leon.moshtarak.enums.FragmentTags.REQUEST_DONE;
import static com.app.leon.moshtarak.helpers.Constants.CITIZEN_ACCOUNT_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CITIZEN_BASE_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CITIZEN_COMPLETE_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CITIZEN_LIST_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CONTACT_FORBIDDEN_COMPLETE_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CONTACT_FORBIDDEN_DESCRIPTION_FRAGMENT;
import static com.app.leon.moshtarak.utils.ShowFragment.addFragment;
import static com.app.leon.moshtarak.utils.ShowFragment.replaceFragment;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.app.leon.moshtarak.adapters.base_adapter.ImageViewAdapter;
import com.app.leon.moshtarak.adapters.recycler_view.CitizenAdapter;
import com.app.leon.moshtarak.base_items.BaseActivity;
import com.app.leon.moshtarak.fragments.citizen.CitizenBaseFragment;
import com.app.leon.moshtarak.fragments.citizen.CitizenCompleteFragment;
import com.app.leon.moshtarak.fragments.citizen.CitizenInfoFragment;
import com.app.leon.moshtarak.fragments.citizen.CitizenListFragment;
import com.app.leon.moshtarak.fragments.citizen.ContactForbiddenBaseFragment;
import com.app.leon.moshtarak.fragments.citizen.ContactForbiddenCompleteFragment;
import com.app.leon.moshtarak.fragments.citizen.ContactForbiddenInfoFragment;
import com.app.leon.moshtarak.fragments.citizen.ForbiddenViewModel;
import com.app.leon.moshtarak.fragments.contact_us.ContactBaseFragment;
import com.app.leon.moshtarak.fragments.dialog.MessageDoneRequestFragment;
import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.databinding.ActivityCitizenBinding;

import java.util.Arrays;

public class CitizenActivity extends BaseActivity implements CitizenListFragment.ICallback,
        ContactBaseFragment.ICallback, ContactForbiddenBaseFragment.ICallback,
        ContactForbiddenCompleteFragment.ICallback, ContactForbiddenInfoFragment.ICallback,
        CitizenBaseFragment.ICallback, CitizenInfoFragment.ICallback, CitizenCompleteFragment.ICallback {
    private final ForbiddenViewModel viewModel = new ForbiddenViewModel();
    private ActivityCitizenBinding binding;
    private ImageViewAdapter imageAdapter;
    private CitizenAdapter adapter;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityCitizenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        displayView(CITIZEN_LIST_FRAGMENT);
    }

    @Override
    public void displayView(int position) {
        if (position == CITIZEN_LIST_FRAGMENT) {
            adapter = new CitizenAdapter(this, R.array.citizen_menu, R.array.citizen_introduction,
                    R.array.citizen_icons);
            addFragment(this, binding.fragmentCitizen.getId(), CitizenListFragment.newInstance());
        } else if (position == CITIZEN_BASE_FRAGMENT) {
            imageAdapter = new ImageViewAdapter(this);
            viewModel.setType("");
            if (viewModel.getParentType().equals(Arrays.asList(getResources().getStringArray(R.array.citizen_menu)).get(0))) {
                replaceFragment(this, binding.fragmentCitizen.getId(), ContactForbiddenBaseFragment.newInstance());
            } else {
                replaceFragment(this, binding.fragmentCitizen.getId(), CitizenBaseFragment.newInstance());
            }
        } else if (position == CITIZEN_ACCOUNT_FRAGMENT) {
            replaceFragment(this, binding.fragmentCitizen.getId(), CitizenInfoFragment.newInstance());
        } else if (position == CITIZEN_COMPLETE_FRAGMENT) {
            replaceFragment(this, binding.fragmentCitizen.getId(), CitizenCompleteFragment.newInstance());
        } else if (position == CONTACT_FORBIDDEN_DESCRIPTION_FRAGMENT) {
            replaceFragment(this, binding.fragmentCitizen.getId(), ContactForbiddenInfoFragment.newInstance());
        } else if (position == CONTACT_FORBIDDEN_COMPLETE_FRAGMENT) {
            replaceFragment(this, binding.fragmentCitizen.getId(), ContactForbiddenCompleteFragment.newInstance());
        }
    }

    @Override
    protected String getExitMessage() {
//        final FragmentManager fragmentManager = getSupportFragmentManager();
//        if (fragmentManager.getBackStackEntryCount() == 0)
//            return getString(R.string.return_by_press_again);
//        else return null;
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setServices(String selectedTitle) {
        viewModel.setParentType(selectedTitle);
    }

    @Override
    public ImageViewAdapter getImageViewAdapter() {
        return imageAdapter;
    }

    @Override
    public void setImageViewAdapter(ImageViewAdapter adapter) {
        this.imageAdapter = adapter;
    }

    @Override
    public void confirm(String message) {
        viewModel.resetViewModel();
        imageAdapter = new ImageViewAdapter(this);
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();
        showFragmentDialogOnce(this, REQUEST_DONE.getValue(),
                MessageDoneRequestFragment.newInstance(message, getString(R.string.main_page), DialogFragment::dismiss));
    }

    @Override
    public ForbiddenViewModel getForbiddenViewModel() {
        return viewModel;
    }

    @Override
    public CitizenAdapter getCitizenAdapter() {
        return adapter;
    }
}