package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.FragmentTags.REQUEST_DONE;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_BASE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_BRANCH_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_COMPLAINT_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_FAQ_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_FORBIDDEN_COMPLETE_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_FORBIDDEN_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_PHONEBOOK_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_SUGGESTION_FRAGMENT;
import static com.leon.hamrah_abfa.utils.ShowFragment.addFragment;
import static com.leon.hamrah_abfa.utils.ShowFragment.replaceFragment;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.ImageViewAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityContactUsBinding;
import com.leon.hamrah_abfa.fragments.contact_us.ContactBaseFragment;
import com.leon.hamrah_abfa.fragments.contact_us.ContactBranchFragment;
import com.leon.hamrah_abfa.fragments.contact_us.ContactComplaintFragment;
import com.leon.hamrah_abfa.fragments.contact_us.ContactFAQFragment;
import com.leon.hamrah_abfa.fragments.contact_us.ContactForbiddenBaseFragment;
import com.leon.hamrah_abfa.fragments.contact_us.ContactForbiddenCompleteFragment;
import com.leon.hamrah_abfa.fragments.contact_us.ContactPhonebookFragment;
import com.leon.hamrah_abfa.fragments.contact_us.ContactSuggestionFragment;
import com.leon.hamrah_abfa.fragments.contact_us.ForbiddenViewModel;
import com.leon.hamrah_abfa.fragments.dialog.RequestDoneFragment;

public class ContactUsActivity extends BaseActivity implements ContactBaseFragment.ICallback,
        ContactForbiddenBaseFragment.ICallback, ContactForbiddenCompleteFragment.ICallback {
    private ActivityContactUsBinding binding;
    private ImageViewAdapter adapter;
    private final ForbiddenViewModel viewModel = new ForbiddenViewModel();

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
            replaceFragment(this, binding.fragmentContact.getId(), ContactComplaintFragment.newInstance());
        } else if (position == CONTACT_FAQ_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactFAQFragment.newInstance());
        } else if (position == CONTACT_BRANCH_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactBranchFragment.newInstance());
        } else if (position == CONTACT_FORBIDDEN_FRAGMENT) {
            viewModel.resetViewModel();
            replaceFragment(this, binding.fragmentContact.getId(), ContactForbiddenBaseFragment.newInstance());
        } else if (position == CONTACT_PHONEBOOK_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactPhonebookFragment.newInstance());
        } else if (position == CONTACT_FORBIDDEN_COMPLETE_FRAGMENT) {
            replaceFragment(this, binding.fragmentContact.getId(), ContactForbiddenCompleteFragment.newInstance());
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
        return adapter;
    }

    @Override
    public void setImageViewAdapter(ImageViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void confirm() {
        viewModel.resetViewModel();
        getSupportFragmentManager().popBackStack();
        showFragmentDialogOnce(this, REQUEST_DONE.getValue(), RequestDoneFragment.newInstance("123456",
                getString(R.string.main_page), new RequestDoneFragment.IClickListener() {
                    @Override
                    public void yes(DialogFragment dialogFragment) {
                        dialogFragment.dismiss();
                    }

                    @Override
                    public void no(DialogFragment dialogFragment) {

                    }
                }));
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
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