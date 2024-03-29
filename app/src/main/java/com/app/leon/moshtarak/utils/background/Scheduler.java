package com.app.leon.moshtarak.utils.background;


import static com.app.leon.moshtarak.helpers.Constants.CHECK_INTERVAL;
import static com.app.leon.moshtarak.helpers.Constants.HOUR_OF_DAY;
import static com.app.leon.moshtarak.helpers.Constants.MINUTE;

import android.content.Context;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    public static void scheduleBackgroundTask(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
        calendar.set(Calendar.MINUTE, MINUTE);
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        long delayInMillis = calendar.getTimeInMillis() - System.currentTimeMillis();
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                .Builder(NotificationWorker.class, CHECK_INTERVAL, TimeUnit.MILLISECONDS)
                .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS).build();
        WorkManager.getInstance(context).enqueue(periodicWorkRequest);
    }


    public static void backgroundTaskInTime(Context context) {
        long delayInMillis = 0;
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                .Builder(NotificationWorker.class, CHECK_INTERVAL, TimeUnit.MILLISECONDS)
                .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS).build();
        WorkManager.getInstance(context).enqueue(periodicWorkRequest);
    }
}
