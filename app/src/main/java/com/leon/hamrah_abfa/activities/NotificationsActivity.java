package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.FragmentTags.MESSAGE_DETAIL;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.fragment_state_adapter.ViewPagerAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityNotificationsBinding;
import com.leon.hamrah_abfa.fragments.citizen.NotFoundFragment;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.fragments.notifications.NewsFragment;
import com.leon.hamrah_abfa.fragments.notifications.NotificationFragment;
import com.leon.hamrah_abfa.fragments.notifications.NotificationMessageDetailFragment;
import com.leon.hamrah_abfa.fragments.notifications.Notifications;
import com.leon.hamrah_abfa.fragments.notifications.NotificationsViewModel;
import com.leon.hamrah_abfa.requests.notification.GetNotificationRequest;
import com.leon.hamrah_abfa.requests.notification.SetNotificationSeenRequest;
import com.leon.hamrah_abfa.utils.ShowFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotificationsActivity extends BaseActivity implements TabLayout.OnTabSelectedListener,
        NotificationFragment.ICallback, NewsFragment.ICallback, NotificationMessageDetailFragment.ICallback {
    private ActivityNotificationsBinding binding;
    private DialogFragment fragment;
    private final ArrayList<NotificationsViewModel> notifications = new ArrayList<>();
    private int unseenNotification;
    private int unseenNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        insertData();
        requestNotifications();
        initializeViewPager();
        initializeTabLayout();
    }

    private void requestNotifications() {
        boolean isOnline = new GetNotificationRequest(this, new GetNotificationRequest.ICallback() {
            @Override
            public void succeed(Notifications notifications) {
                NotificationsActivity.this.notifications.addAll(notifications.customerNotifications);
                setUnseenNotificationNumber();
                initializeViewPager();
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
                showFragmentDialogOnce(this, WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void initializeViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        if (notifications.isEmpty()) {
            adapter.addFragment(NotFoundFragment.newInstance());
        } else {
            adapter.addFragment(NotificationFragment.newInstance());
        }
        adapter.addFragment(NewsFragment.newInstance());
        binding.viewPager.setAdapter(adapter);
    }

    private void initializeTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.services)).setIcon(android.R.drawable.ic_popup_reminder));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.news)).setIcon(android.R.drawable.ic_dialog_email));
        binding.tabLayout.setSelectedTabIndicator(R.drawable.cat_tabs_rounded_line_indicator);

        setUnseenNotificationNumber();
        setUnseenNewsNumber();
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });
        binding.tabLayout.addOnTabSelectedListener(this);
    }

    public void setUnseenNotificationNumber() {
        unseenNotification = 0;
        for (int i = 0; i < notifications.size(); i++) {
            if (notifications.get(i).getSeenDateTime() == null)
                unseenNotification++;
        }
        BadgeDrawable badgeDrawable = binding.tabLayout.getTabAt(0).getOrCreateBadge();
        if (unseenNotification > 0) {
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(unseenNotification);
        }
    }

    @Override
    public void setUnseenNewsNumber() {
        BadgeDrawable badgeDrawable = binding.tabLayout.getTabAt(1).getOrCreateBadge();
        if (unseenNews > 0) {
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(unseenNews);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        binding.viewPager.setCurrentItem(tab.getPosition());
//        tab.removeBadge();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public ArrayList<NotificationsViewModel> getNotifications() {
        return notifications;
    }

    @Override
    public void setNotificationSeen(int position) {
//        showMessageDialog(position);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy MM dd HH:mm:ss:SSS");
        notifications.get(position).setSeenDateTime(dateFormatter.format(new Date(Calendar.getInstance().getTimeInMillis())));
//        setUnseenNotificationNumber();
        requestSeenNotification(notifications.get(position).getId());
    }

    @Override
    public NotificationsViewModel getNotification(int position) {
        return notifications.get(position);
    }

    @Override
    public void showMessageDialog(int position) {
        showFragmentDialogOnce(this, MESSAGE_DETAIL.getValue(),
                NotificationMessageDetailFragment.newInstance(position));
    }

    private void requestSeenNotification(String id) {
        new SetNotificationSeenRequest(this, new SetNotificationSeenRequest.ICallback() {

            @Override
            public void succeed(Notifications notifications) {
                setUnseenNotificationNumber();
            }
        }, id).request();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected String getExitMessage() {
        return null;
    }


}