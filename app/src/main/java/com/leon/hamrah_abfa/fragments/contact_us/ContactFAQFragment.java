package com.leon.hamrah_abfa.fragments.contact_us;

import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
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

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.recycler_view.FAQAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentContactFaqBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.requests.contact_us.GetFAQRequest;

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
        boolean isOnline = new GetFAQRequest(requireContext(), new GetFAQRequest.ICallback() {
            @Override
            public void succeed(ArrayList<ContactFAQViewModel> faqs) {
                initializeRecyclerView(faqs);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }).request();
        progressStatus(isOnline);
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                showFragmentDialogOnce(requireContext(), WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void initializeRecyclerView(ArrayList<ContactFAQViewModel> faqsTemp) {
        faqs.addAll(faqsTemp);
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