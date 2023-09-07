package com.app.leon.moshtarak.fragments.contact_us;

import static com.leon.toast.RTLToast.warning;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.adapters.recycler_view.FAQAdapter;
import com.app.leon.moshtarak.adapters.recycler_view.RecyclerItemClickListener;
import com.app.leon.moshtarak.databinding.FragmentContactFaqBinding;
import com.app.leon.moshtarak.enums.FragmentTags;
import com.app.leon.moshtarak.fragments.dialog.WaitingFragment;
import com.app.leon.moshtarak.requests.contact_us.GetFAQRequest;
import com.app.leon.moshtarak.utils.ShowFragment;

import java.util.ArrayList;

public class ContactFAQFragment extends Fragment implements TextWatcher {
    private final ArrayList<ContactFAQViewModel> faqs = new ArrayList<>();
    private FragmentContactFaqBinding binding;
    private DialogFragment fragment;
    private FAQAdapter adapter;

    public ContactFAQFragment() {
    }

    public static ContactFAQFragment newInstance() {
        return new ContactFAQFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactFaqBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        requestFAQ();
        binding.editTextSearch.addTextChangedListener(this);
    }

    private void requestFAQ() {
        progressStatus(new GetFAQRequest(requireContext(), new GetFAQRequest.ICallback() {
            @Override
            public void succeed(ArrayList<ContactFAQViewModel> faqs) {
                ContactFAQFragment.this.faqs.addAll(faqs);
                initializeRecyclerView();
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }).request());
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance(this::initializeRecyclerView);
                ShowFragment.showFragmentDialogOnce(requireContext(), FragmentTags.WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void initializeRecyclerView() {
        adapter = new FAQAdapter(requireContext(), faqs);
        binding.recyclerViewQuestion.setAdapter(adapter);
        binding.recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(requireContext()));
        setRecyclerViewListener();
    }

    private void setRecyclerViewListener() {
        final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewQuestion, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.updateSelectedService(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        binding.recyclerViewQuestion.addOnItemTouchListener(listener);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (binding.editTextSearch.getEditableText() == s) {
            if (s.toString().length() == 0) {
                adapter.filterList(faqs);
                return;
            }
            filter(s.toString());
        }
    }

    private void filter(String text) {
        final ArrayList<ContactFAQViewModel> faqTemp = new ArrayList<>();
        for (ContactFAQViewModel faq : faqs) {
            if (faq.q.toLowerCase().contains(text.toLowerCase()))
                faqTemp.add(faq);
        }
        if (faqTemp.isEmpty()) {
            warning(requireContext(), R.string.not_found).show();
        } else {
            adapter.filterList(faqTemp);
        }
    }
}