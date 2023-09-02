package com.leon.hamrah_abfa.fragments.contact_us;

import static com.leon.hamrah_abfa.enums.FragmentTags.REQUEST_DONE;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.utils.FileCustomizer.createImageFile;
import static com.leon.hamrah_abfa.utils.FileCustomizer.prepareImage;
import static com.leon.hamrah_abfa.utils.PermissionManager.checkCameraPermission;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;
import static com.leon.toast.RTLToast.warning;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.BuildConfig;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.ImageViewAdapter;
import com.leon.hamrah_abfa.databinding.FragmentContactSuggestionBinding;
import com.leon.hamrah_abfa.fragments.dialog.MessageDoneRequestFragment;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.requests.contact_us.GetSuggestionsTypes;
import com.leon.hamrah_abfa.requests.contact_us.RegisterFeedbackRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ContactSuggestionFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private final FeedbackViewModel viewModel = new FeedbackViewModel();
    private final ArrayList<FeedbackType> feedbackType = new ArrayList<>();
    private FragmentContactSuggestionBinding binding;
    private ImageViewAdapter adapter;
    private long lastClickTime = 0;
    private File fileImage = null;

    private DialogFragment fragment;

    public ContactSuggestionFragment() {
    }

    public static ContactSuggestionFragment newInstance() {
        return new ContactSuggestionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactSuggestionBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        requestSuggestionsTypes();
        initializeGridView();
        binding.editTextSuggestionType.setOnClickListener(this);
        binding.buttonSubmit.setOnClickListener(this);
    }

    private void initializeGridView() {
        adapter = new ImageViewAdapter(requireContext());
        binding.gridViewImages.setAdapter(adapter);
        binding.gridViewImages.setOnItemClickListener(this);
    }

    private void requestSuggestionsTypes() {
        progressStatus(new GetSuggestionsTypes(requireContext(), new GetSuggestionsTypes.ICallback() {
            @Override
            public void succeed(ArrayList<FeedbackType> feedbackType) {
                ContactSuggestionFragment.this.feedbackType.addAll(feedbackType);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }

        }).request());
    }


    private void requestRegisterFeedback() {
        progressStatus(new RegisterFeedbackRequest(requireContext(),
                new RegisterFeedbackRequest.ICallback() {
                    @Override
                    public void succeed(FeedbackViewModel viewModel) {
                        showFragmentDialogOnce(requireContext(), REQUEST_DONE.getValue(),
                                MessageDoneRequestFragment.newInstance(viewModel.getMessage(),
                                        getString(R.string.main_page), dialogFragment -> {
                                            dialogFragment.dismiss();
                                            requireActivity().getSupportFragmentManager().popBackStack();
                                        }
                                ));
                    }

                    @Override
                    public void changeUI(boolean done) {
                        progressStatus(done);
                    }
                }, viewModel).request());
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

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.edit_text_suggestion_type) {
            showMenu(binding.editTextSuggestionType);
        } else if (id == R.id.button_submit) {
            if (checkInput()) {
                requestRegisterFeedback();
            }
        }
    }

    private boolean checkInput() {
        if (viewModel.getFeedbackTypeId() == null) {
            binding.editTextSuggestionType.setError(getString(R.string.choose_complaint_type));
            binding.editTextSuggestionType.requestFocus();
            warning(requireContext(), R.string.choose_complaint_type).show();
            return false;
        } else if (viewModel.getDescription() == null || viewModel.getDescription().isEmpty()) {
            binding.editTextDescription.setError(getString(R.string.field_complaint_description));
            binding.editTextDescription.requestFocus();
            warning(requireContext(), R.string.field_complaint_description).show();
            return false;
        }
        if (viewModel.getSolution() == null || viewModel.getSolution().isEmpty()) {
            viewModel.setSolution("");
        }
        return true;
    }

    private void showMenu(View v) {
        final PopupMenu popup = new PopupMenu(requireActivity(), v, Gravity.TOP);
        for (int i = 0; i < feedbackType.size(); i++)
            popup.getMenu().add(feedbackType.get(i).title);
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
            binding.editTextSuggestionType.setText(menuItem.getTitle());
            for (int i = 0; i < feedbackType.size(); i++) {
                if (feedbackType.get(i).title.contentEquals(menuItem.getTitle())) {
                    viewModel.setFeedbackTypeId(feedbackType.get(i).id);
                }
            }
            return true;
        });
        popup.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
        lastClickTime = SystemClock.elapsedRealtime();
        if (position + 1 == adapter.getCount()) {
            if (checkCameraPermission(requireContext()))
                openCameraForResult();
            else {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        }
    }

    private void openCameraForResult() {
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            fileImage = createImageFile(requireContext());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(requireContext(),
                    BuildConfig.APPLICATION_ID.concat(".provider"), fileImage));
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            cameraResultLauncher.launch(cameraIntent);
        } catch (IOException e) {
            error(requireContext(), e.getMessage()).show();
        }
    }

    private final ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    try {
                        adapter.addItem(prepareImage(fileImage));
                    } catch (IOException e) {
                        error(requireContext(), e.getMessage()).show();
                    }
                }
            });
    private final ActivityResultLauncher<String> requestCameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    success(requireContext(), getString(R.string.camera_permission_granted)).show();
                } else {
                    error(requireContext(), getString(R.string.camera_permission_unavailable)).show();
                }
            });
}