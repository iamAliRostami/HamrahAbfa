package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.FragmentTags.SUBMIT_INFO;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ALIAS;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEBT;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.IS_FIRST;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.MOBILE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.warning;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.fragment_state_adapter.CardPagerAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityMainBinding;
import com.leon.hamrah_abfa.fragments.cards.BillsSummary;
import com.leon.hamrah_abfa.enums.BundleEnum;
import com.leon.hamrah_abfa.fragments.bottom_sheets.SubmitInfoFragment;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.fragments.cards.BillCardViewModel;
import com.leon.hamrah_abfa.fragments.ui.home.HomeFragment;
import com.leon.hamrah_abfa.fragments.ui.services.ServiceFragment;
import com.leon.hamrah_abfa.requests.bill.GetBillsRequest;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BaseActivity implements HomeFragment.ICallback,
        SubmitInfoFragment.ICallback, ServiceFragment.ICallback {
    private ActivityMainBinding binding;
    private CardPagerAdapter cardPagerAdapter;
    private int position;

    @Override
    protected void initialize() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //TODO
        initializeSplash();
        // TODO
//        createCardPagerAdapter();
        initializeBottomSheet();
        binding.floatButtonAdd.setOnClickListener(this);
        final ImageView imageViewSetting = findViewById(R.id.image_view_setting);
        imageViewSetting.setOnClickListener(this);
        final ImageView imageViewNotification = findViewById(R.id.image_view_notification);
        imageViewNotification.setOnClickListener(this);
    }

    private void initializeBottomSheet() {
        final AppBarConfiguration appBar = new AppBarConfiguration.Builder(R.id.navigation_home,
                R.id.navigation_incident, R.id.navigation_services, R.id.navigation_dashboard).build();
        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupActionBarWithNavController(this, navController, appBar);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }
        final MaterialShapeDrawable bottomBarBackground = (MaterialShapeDrawable) binding.bottomAppBar.getBackground();
        bottomBarBackground.setShapeAppearanceModel(bottomBarBackground.getShapeAppearanceModel()
                .toBuilder()
                .setTopLeftCorner(new RoundedCornerTreatment()).setTopLeftCornerSize(new RelativeCornerSize(0.5f))
                .setTopRightCorner(new RoundedCornerTreatment()).setTopRightCornerSize(new RelativeCornerSize(0.5f))
                .build());
    }

    private void initializeSplash() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        binding.imageViewSplash.startAnimation(animation);
        binding.lottieAnimationView.playAnimation();
        binding.lottieAnimationView.addAnimatorListener(animatorLottieListener);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.float_button_add) {
            showFragmentDialogOnce(this, SUBMIT_INFO.getValue(), SubmitInfoFragment.newInstance());
        } else if (id == R.id.image_view_setting) {
            final Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            settingActivityResultLauncher.launch(intent);
        } else if (id == R.id.image_view_notification) {
            final Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
            //TODO
            intent.putExtra(BundleEnum.ID.getValue(), getCurrentId(position));
            startActivity(intent);
        }
    }

    private final Animation.AnimationListener animationTypoListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            binding.imageViewLogo.setVisibility(View.GONE);
            binding.container.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (/*  TODO true ||*/getInstance().getApplicationComponent().SharedPreferenceModel().getBoolData(IS_FIRST.getValue(), true)) {
                final Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
            } else if (!getInstance().getApplicationComponent().SharedPreferenceModel().checkIsNotEmpty(MOBILE.getValue())) {
                final Intent intent = new Intent(getApplicationContext(), MobileSubmitActivity.class);
                //TODO
                submitMobileActivityResultLauncher.launch(intent);
            } else {
                requestBills();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private final Animator.AnimatorListener animatorLottieListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(@NonNull Animator animation) {

        }

        @Override
        public void onAnimationEnd(@NonNull Animator animation) {
            binding.relativeLayoutSplash.setVisibility(View.GONE);
            binding.imageViewLogo.setVisibility(View.VISIBLE);
            final Animation animationFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
            animationFadeOut.setAnimationListener(animationTypoListener);
            binding.imageViewLogo.startAnimation(animationFadeOut);
        }

        @Override
        public void onAnimationCancel(@NonNull Animator animation) {

        }

        @Override
        public void onAnimationRepeat(@NonNull Animator animation) {

        }
    };
    final ActivityResultLauncher<Intent> submitMobileActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    requestBills();
                }
            });
    final ActivityResultLauncher<Intent> settingActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    final Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });

    @Override
    protected String getExitMessage() {
        return getString(R.string.exit_by_press_again);
    }

    @Override
    public CardPagerAdapter getCardPagerAdapter() {
        return cardPagerAdapter;
    }

    @Override
    public void updateCard() {
        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        if (navHostFragment != null) {
            final NavController navController = navHostFragment.getNavController();
            if (navController.getCurrentDestination() != null) {
                final int i = navController.getCurrentDestination().getId();
                navController.popBackStack(i, true);
                navController.navigate(i);
            }
        }
    }

    private void requestBills() {
        boolean isOnline = new GetBillsRequest(this, new GetBillsRequest.ICallback() {
            @Override
            public void succeed(BillsSummary billsInfo) {
                String billsId = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue());
                ArrayList<String> billIds = new ArrayList<>(Arrays.asList(billsId.split(",")));
                for (int i = 0; i < billsInfo.billDtos.size(); i++) {
                    if (billsId.length() == 0)
                        insertData(billsInfo.billDtos.get(i));
                    else {
                        for (int j = 0; j < billIds.size(); j++) {
                            if (billIds.get(j).equals(billsInfo.billDtos.get(i).getBillId())) {
                                editData(billsInfo);
                            } else {
                                insertData(billsInfo.billDtos.get(i));
                            }
                        }
                    }
                }
                updateCard();
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }).request();
        progressStatus(isOnline);
    }

    private void insertData(BillCardViewModel bill) {
        //TODO
        bill.setDebtString(String.valueOf(bill.getDebt()));
        String id = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ID.getValue()).concat(bill.getId()).concat(",");
        String billId = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue()).concat(bill.getBillId()).concat(",");
        String alias = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ALIAS.getValue()).concat(bill.getAlias()).concat(",");
        String debt = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(DEBT.getValue()).concat(bill.getDebtString()).concat(",");


        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ID.getValue(), id);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), billId);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ALIAS.getValue(), alias);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), debt);
    }

    private void editData(BillsSummary billInfo) {
        String debt = "";
        String alias = "";
        String id = "";
        for (int i = 0; i < billInfo.billDtos.size(); i++) {
            billInfo.billDtos.get(i).setDebtString(String.valueOf(billInfo.billDtos.get(i).getDebt()));
            debt = debt.concat(billInfo.billDtos.get(i).getDebtString()).concat(",");
            alias = alias.concat(billInfo.billDtos.get(i).getAlias()).concat(",");
            id = id.concat(billInfo.billDtos.get(i).getId()).concat(",");
        }

        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ID.getValue(), id);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ALIAS.getValue(), alias);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), debt);
    }

    private DialogFragment fragment;

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                showFragmentDialogOnce(this, WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    @Override
    public void createCardPagerAdapter() {
        cardPagerAdapter = new CardPagerAdapter(this);
    }

    @Override
    public String getCurrentId(int position) {
        return cardPagerAdapter.getCurrentId(position);
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    //TODO
    @Override
    public boolean isEmpty() {
        if (cardPagerAdapter.isEmpty()) {
            warning(this, R.string.there_is_no_bill).show();
            showFragmentDialogOnce(this, SUBMIT_INFO.getValue(), SubmitInfoFragment.newInstance());
            return true;
        }
        return false;
    }
}