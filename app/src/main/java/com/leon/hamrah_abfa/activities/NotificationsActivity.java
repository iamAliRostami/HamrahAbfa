package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.fragment_state_adapter.ViewPagerAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityNotificationsBinding;
import com.leon.hamrah_abfa.fragments.notifications.NewsFragment;
import com.leon.hamrah_abfa.fragments.notifications.NotificationFragment;
import com.leon.hamrah_abfa.tables.News;
import com.leon.hamrah_abfa.tables.Notification;

import java.util.ArrayList;
import java.util.Arrays;

public class NotificationsActivity extends BaseActivity implements TabLayout.OnTabSelectedListener,
        NotificationFragment.ICallback, NewsFragment.ICallback {
    private ActivityNotificationsBinding binding;
    private String billId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initialize() {
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            billId = getIntent().getExtras().getString(BILL_ID.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        insertData();
        initializeViewPager();
        initializeTabLayout();
    }

    private void insertData() {
//        int id, String summary, String title, String text, String date, int category
        final ArrayList<News> news = new ArrayList<>(Arrays.asList(
                new News(12, billId, "خلاصه 1", "خبر 1", "این متن خبر یک است.", "12/12/12", 1),
                new News(13, billId, "خلاصه 2", "خبر 11", "این متن خبر دو است.", "12/12/12", 2),
                new News(14, billId, "خلاصه 3", "خبر 12", "این متن خبر سه است.", "12/12/12", 3),
                new News(15, billId, "خلاصه 4", "خبر 13", "این متن خبر چهار است.", "12/12/12", 4),
                new News(22, billId, "خلاصه 5", "خبر 14", "این متن خبر پنج است.", "12/12/12", 5),
                new News(32, billId, "خلاصه 6", "خبر 15", "این متن خبر شش است.", "12/12/12", 4),
                new News(42, billId, "خلاصه 7", "خبر 16", "این متن خبر هفت است.", "12/12/12", 5),
                new News(52, billId, "خلاصه 8", "خبر 17", "این متن خبر هشت است.", "12/12/12", 3),
                new News(17, billId, "خلاصه 9", "خبر 18", "این متن خبر نه است.", "12/12/12", 2),
                new News(86, billId, "خلاصه 9", "خبر 18", "این متن خبر نه است.", "12/12/12", 1),
                new News(86, billId, "خلاصه 9", "خبر 18", "این متن خبر نه است.", "12/12/12", 1),
                new News(21, billId, "خلاصه 10", "خبر 19", "این متن خبر ده است.", "12/12/12", 1)));
        getApplicationComponent().MyDatabase().newsDao().insertNews(news);
        final ArrayList<Notification> notifications = new ArrayList<>(Arrays.asList(
                new Notification(12, billId, "خلاصه 1", "نوتیف 1", "این متن نوتیف یک است.", "12/12/12", 1),
                new Notification(13, billId, "خلاصه 2", "نوتیف 11", "این متن نوتیف دو است.", "12/12/12", 3),
                new Notification(14, billId, "خلاصه 3", "نوتیف 12", "این متن نوتیف سه است.", "12/12/12", 2),
                new Notification(15, billId, "خلاصه 4", "نوتیف 13", "این متن نوتیف چهار است.", "12/12/12", 4),
                new Notification(22, billId, "خلاصه 5", "نوتیف 14", "این متن نوتیف پنج است.", "12/12/12", 5),
                new Notification(32, billId, "خلاصه 6", "نوتیف 15", "این متن نوتیف شش است.", "12/12/12", 5),
                new Notification(42, billId, "خلاصه 7", "نوتیف 16", "این متن نوتیف هفت است.", "12/12/12", 4),
                new Notification(52, billId, "خلاصه 8", "نوتیف 17", "این متن نوتیف هشت است.", "12/12/12", 3),
                new Notification(17, billId, "خلاصه 9", "نوتیف 18", "این متن نوتیف نه است.", "12/12/12", 3),
                new Notification(86, billId, "خلاصه 9", "نوتیف 18", "این متن نوتیف نه است.", "12/12/12", 2),
                new Notification(86, billId, "خلاصه 10", "نوتیف 18", "این متن نوتیف نه است.", "12/12/12", 2),
                new Notification(21, billId, "خلاصه 11", "نوتیف 19", "این متن نوتیف ده است.", "12/12/12", 1)));
        getApplicationComponent().MyDatabase().notificationDao().insertNotifications(notifications);
    }

    private void initializeViewPager() {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        adapter.addFragment(NotificationFragment.newInstance());
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

    @Override
    public void setUnseenNotificationNumber() {
        final BadgeDrawable badgeDrawable = binding.tabLayout.getTabAt(0).getOrCreateBadge();
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(getApplicationComponent().MyDatabase().notificationDao().getUnseenNotificationNumber(billId, false));
    }

    @Override
    public void setUnseenNewsNumber() {
        final BadgeDrawable badgeDrawable = binding.tabLayout.getTabAt(1).getOrCreateBadge();
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(getApplicationComponent().MyDatabase().newsDao().getUnseenNewsNumber(billId, false));
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        binding.viewPager.setCurrentItem(tab.getPosition());
        tab.removeBadge();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public String getBillId() {
        return billId;
    }
}