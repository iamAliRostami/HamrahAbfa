package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.UUID;
import static com.leon.hamrah_abfa.enums.FragmentTags.ASK_YES_NO;
import static com.leon.hamrah_abfa.enums.FragmentTags.SUBMIT_INFO;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ALIAS;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEADLINE;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEBT;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.IS_FIRST;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.IS_PAYED;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.MOBILE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.hamrah_abfa.utils.background.Scheduler.backgroundTaskInTime;
import static com.leon.hamrah_abfa.utils.background.Scheduler.scheduleBackgroundTask;
import static com.leon.toast.RTLToast.warning;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
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
import com.leon.hamrah_abfa.fragments.bottom_sheets.EditInfoFragment;
import com.leon.hamrah_abfa.fragments.bottom_sheets.SubmitInfoFragment;
import com.leon.hamrah_abfa.fragments.cards.BillCardViewModel;
import com.leon.hamrah_abfa.fragments.cards.BillsSummary;
import com.leon.hamrah_abfa.fragments.cards.CardFragment;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.fragments.dialog.YesNoFragment;
import com.leon.hamrah_abfa.fragments.ui.dashboard.DashboardBaseFragment;
import com.leon.hamrah_abfa.fragments.ui.home.HomeFragment;
import com.leon.hamrah_abfa.fragments.ui.services.ServiceFragment;
import com.leon.hamrah_abfa.requests.bill.GetBillsRequest;
import com.leon.hamrah_abfa.requests.my_account.AddByMobileRequest;

public class MainActivity extends BaseActivity implements HomeFragment.ICallback,
        SubmitInfoFragment.ICallback, ServiceFragment.ICallback, DashboardBaseFragment.ICallback,
        EditInfoFragment.ICallback, CardFragment.ICallback {
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    backgroundTaskInTime(this);
                    scheduleBackgroundTask(this);
                } else {
                    warning(this, R.string.notification_won_t_send).show();
                }
            });
    private final ActivityResultLauncher<Intent> submitMobileActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            showSyncDialog();
                            startBackgroundTask();
//                            //TODO show
//                            if (getInstance().getApplicationComponent().SharedPreferenceModel().getBoolData(SYNCED.getValue(), true)) {
//
//                            } else {
//                                requestBills();
//                            }
                        }
                    });

    private void showSyncDialog() {
        showFragmentDialogOnce(this, ASK_YES_NO.getValue(),
                YesNoFragment.newInstance(
                        R.drawable.setting_logout, getString(R.string.connected_account), getString(R.string.are_u_sure_sync),
                        getString(R.string.go_continue), getString(R.string.cancel), new YesNoFragment.IClickListener() {
                            @Override
                            public void yes(DialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                progressStatus(new AddByMobileRequest(MainActivity.this,
                                        new AddByMobileRequest.ICallback() {
                                            @Override
                                            public void succeed() {
//                                                getInstance().getApplicationComponent().SharedPreferenceModel().putData(SYNCED.getValue(), false);
                                                requestBills();
                                            }

                                            @Override
                                            public void changeUI(boolean done) {
                                                progressStatus(done);
                                            }
                                        }, getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(MOBILE.getValue()))
                                        .request());
                            }

                            @Override
                            public void no(DialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                requestBills();
                            }
                        })
        );
    }

    private final ActivityResultLauncher<Intent> welcomeActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            getInstance().getApplicationComponent().SharedPreferenceModel().putData(IS_FIRST.getValue(), false);
                        }
                        if (!getInstance().getApplicationComponent().SharedPreferenceModel().checkIsNotEmpty(MOBILE.getValue())) {
                            Intent intent = new Intent(MainActivity.this, SubmitMobileActivity.class);
                            submitMobileActivityResultLauncher.launch(intent);
                        }
                    });
    private final ActivityResultLauncher<Intent> settingActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            final Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
    private CardPagerAdapter cardPagerAdapter;
    private ActivityMainBinding binding;
    private DialogFragment fragment;
    private final Animation.AnimationListener animationTypoListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            if (getInstance().getApplicationComponent().SharedPreferenceModel().checkIsNotEmpty(MOBILE.getValue())) {
                requestBills();
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            binding.imageViewLogo.setVisibility(View.GONE);
            binding.container.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (/*  TODO true ||*/getInstance().getApplicationComponent().SharedPreferenceModel().getBoolData(IS_FIRST.getValue(), true)) {
                final Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                welcomeActivityResultLauncher.launch(intent);
            } else if (!getInstance().getApplicationComponent().SharedPreferenceModel().checkIsNotEmpty(MOBILE.getValue())) {
                //TODO
                Fragment prev = getSupportFragmentManager().findFragmentByTag(ASK_YES_NO.getValue());
                if (prev == null) {
                    Intent intent = new Intent(MainActivity.this, SubmitMobileActivity.class);
                    submitMobileActivityResultLauncher.launch(intent);
                }
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
    private int position;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //TODO
        startScheduledBackgroundTask();
        initializeSplash();
        // TODO
        initializeBottomSheet();
        binding.floatButtonAdd.setOnClickListener(this);
        final ImageView imageViewSetting = findViewById(R.id.image_view_setting);
        imageViewSetting.setOnClickListener(this);
        final ImageView imageViewNotification = findViewById(R.id.image_view_notification);
        imageViewNotification.setOnClickListener(this);
    }

    private void startScheduledBackgroundTask() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                scheduleBackgroundTask(this);
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        } else {
            scheduleBackgroundTask(this);
        }
    }

    private void startBackgroundTask() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                backgroundTaskInTime(this);
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        } else {
            backgroundTaskInTime(this);
        }
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
//            intent.putExtra(UUID.getValue(), getCurrentId(position));
            startActivity(intent);
        }
    }

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
        new GetBillsRequest(this, new GetBillsRequest.ICallback() {
            @Override
            public void succeed(BillsSummary billsInfo) {
                getInstance().getApplicationComponent().SharedPreferenceModel().putData(ID.getValue(), "");
                getInstance().getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), "");
                getInstance().getApplicationComponent().SharedPreferenceModel().putData(ALIAS.getValue(), "");
                getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), "");
                getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), "");
                getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEADLINE.getValue(), "");
                for (int i = 0; i < billsInfo.billDtos.size(); i++) {
                    insertData(billsInfo.billDtos.get(i));
                }
                updateCard();
            }

            @Override
            public void changeUI(boolean done) {
            }
        }).request();
    }

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

    private void insertData(BillCardViewModel bill) {
        //TODO
//        bill.setDebt(String.valueOf(bill.getDebt()));
        String id = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ID.getValue()).concat(bill.getId()).concat(",");
        String billId = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue()).concat(bill.getBillId()).concat(",");
        String alias = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ALIAS.getValue()).concat(bill.getAlias()).concat(",");
        String debt = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(DEBT.getValue()).concat(bill.getDebt()).concat(",");
        //TODO
        String deadline = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(DEADLINE.getValue())
                .concat(bill.getDeadline() != null ? bill.getDeadline() : "-").concat(",");

        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ID.getValue(), id);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), billId);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ALIAS.getValue(), alias);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), debt);
        //TODO
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEADLINE.getValue(), deadline);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(IS_PAYED.getValue()
                .concat(bill.getBillId()), bill.isPayed());
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