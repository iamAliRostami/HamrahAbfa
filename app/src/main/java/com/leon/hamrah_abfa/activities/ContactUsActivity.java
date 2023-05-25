package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_BASE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_BRANCH_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_COMPLAINT_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_FAQ_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_FORBIDDEN_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_PHONEBOOK_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_SUGGESTION_FRAGMENT;
import static com.leon.hamrah_abfa.utils.ShowFragment.replaceFragment;
import static com.leon.hamrah_abfa.utils.ShowFragment.addFragment;

import android.util.Log;
import android.view.View;

import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityContactUsBinding;
import com.leon.hamrah_abfa.fragments.contact_us.ContactBaseFragment;
import com.leon.hamrah_abfa.fragments.contact_us.ContactSuggestionFragment;

public class ContactUsActivity extends BaseActivity implements ContactBaseFragment.ICallback {
    private ActivityContactUsBinding binding;

    @Override
    protected void initialize() {
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        displayView(CONTACT_BASE_FRAGMENT);
    }

    @Override
    public void displayView(int position) {
        if (position == CONTACT_SUGGESTION_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactSuggestionFragment.newInstance());
        } else if (position == CONTACT_COMPLAINT_FRAGMENT) {
        } else if (position == CONTACT_FAQ_FRAGMENT) {
        } else if (position == CONTACT_BRANCH_FRAGMENT) {
        } else if (position == CONTACT_FORBIDDEN_FRAGMENT) {
        } else if (position == CONTACT_PHONEBOOK_FRAGMENT) {
        } else {
            addFragment(this, binding.fragmentContact.getId(), ContactBaseFragment.newInstance());
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1 ) {
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