package com.app.leon.moshtarak.activities;

import static com.app.leon.moshtarak.enums.FragmentTags.REQUEST_DONE;
import static com.app.leon.moshtarak.helpers.Constants.CONTACT_BASE_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CONTACT_BRANCH_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CONTACT_COMPLAINT_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CONTACT_FAQ_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CONTACT_FORBIDDEN_COMPLETE_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CONTACT_FORBIDDEN_DESCRIPTION_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CONTACT_FORBIDDEN_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CONTACT_PHONEBOOK_FRAGMENT;
import static com.app.leon.moshtarak.helpers.Constants.CONTACT_SUGGESTION_FRAGMENT;
import static com.app.leon.moshtarak.utils.ShowFragment.addFragment;
import static com.app.leon.moshtarak.utils.ShowFragment.replaceFragment;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.app.leon.moshtarak.adapters.base_adapter.ImageViewAdapter;
import com.app.leon.moshtarak.base_items.BaseActivity;
import com.app.leon.moshtarak.fragments.citizen.ContactForbiddenBaseFragment;
import com.app.leon.moshtarak.fragments.citizen.ContactForbiddenCompleteFragment;
import com.app.leon.moshtarak.fragments.citizen.ContactForbiddenInfoFragment;
import com.app.leon.moshtarak.fragments.citizen.ForbiddenViewModel;
import com.app.leon.moshtarak.fragments.contact_us.ContactBaseFragment;
import com.app.leon.moshtarak.fragments.contact_us.ContactBranchFragment;
import com.app.leon.moshtarak.fragments.contact_us.ContactComplaintFragment;
import com.app.leon.moshtarak.fragments.contact_us.ContactFAQFragment;
import com.app.leon.moshtarak.fragments.contact_us.ContactPhonebookFragment;
import com.app.leon.moshtarak.fragments.contact_us.ContactSuggestionFragment;
import com.app.leon.moshtarak.fragments.dialog.MessageDoneRequestFragment;
import com.app.leon.moshtarak.R;

import com.app.leon.moshtarak.databinding.ActivityContactUsBinding;
public class ContactUsActivity extends BaseActivity implements ContactBaseFragment.ICallback,
        ContactForbiddenBaseFragment.ICallback, ContactForbiddenCompleteFragment.ICallback,
        ContactForbiddenInfoFragment.ICallback {
    private final ForbiddenViewModel viewModel = new ForbiddenViewModel();
    private ActivityContactUsBinding binding;
    private ImageViewAdapter imageAdapter;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        displayView(CONTACT_BASE_FRAGMENT);
    }

    @Override
    public void displayView(int position) {
        if (position == CONTACT_SUGGESTION_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactSuggestionFragment.newInstance());
        } else if (position == CONTACT_COMPLAINT_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactComplaintFragment.newInstance());
        } else if (position == CONTACT_FAQ_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactFAQFragment.newInstance());
        } else if (position == CONTACT_BRANCH_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactBranchFragment.newInstance());
        } else if (position == CONTACT_FORBIDDEN_FRAGMENT) {
            imageAdapter = new ImageViewAdapter(this);
            replaceFragment(this, binding.fragmentContact.getId(), ContactForbiddenBaseFragment.newInstance());
        } else if (position == CONTACT_PHONEBOOK_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactPhonebookFragment.newInstance());
        } else if (position == CONTACT_FORBIDDEN_COMPLETE_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactForbiddenCompleteFragment.newInstance());
        } else if (position == CONTACT_FORBIDDEN_DESCRIPTION_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactForbiddenInfoFragment.newInstance());
        } else {
            addFragment(this, binding.fragmentContact.getId(), ContactBaseFragment.newInstance());
        }
    }

    @Override
    public ForbiddenViewModel getForbiddenViewModel() {
        return viewModel;
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
    public void onClick(View v) {
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

}