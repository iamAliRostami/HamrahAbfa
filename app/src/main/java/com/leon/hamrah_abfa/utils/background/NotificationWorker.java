package com.leon.hamrah_abfa.utils.background;

import static com.leon.hamrah_abfa.enums.DataInputEnum.ATTEMPT_COUNT;
import static com.leon.hamrah_abfa.enums.DataInputEnum.BACKOFF_DELAY;
import static com.leon.hamrah_abfa.enums.DataInputEnum.HAS_RETRY_ONCE;
import static com.leon.hamrah_abfa.helpers.Constants.DELAY;
import static com.leon.hamrah_abfa.utils.PermissionManager.isNetworkAvailable;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.activities.MainActivity;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class NotificationWorker extends Worker {
    private final Context context;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        if (isNetworkAvailable(getApplicationContext())) {
            // TODO: Send the request to the server
            // You can use Retrofit, Volley, or any other library to send the request.
            Log.e("here", "success");
            showNotification("success");
            return Result.success();
        } else {
            // Check if the task has already been retried once
            Data inputData = getInputData();
            boolean hasRetriedOnce = inputData.getBoolean(HAS_RETRY_ONCE.getValue(), false);
            int i = inputData.getInt(ATTEMPT_COUNT.getValue(), 0);
            if (!hasRetriedOnce) {
                Log.e("here", "retry ".concat(String.valueOf(i)));
                showNotification("retry ".concat(String.valueOf(i)));
                rescheduleWithDelay(); // Set the flag to true for the retry
                return Result.retry();
            } else {
                Log.e("here", "failure ".concat(String.valueOf(i)));
                showNotification("failure ".concat(String.valueOf(i)));
                return Result.failure();
            }
        }

    }

    private void rescheduleWithDelay() {
        // Calculate the delay in milliseconds
//        long delayMillis = TimeUnit.HOURS.toMillis(DELAY);
        // Create a new Data object with the retry flag
        Data retryData = new Data.Builder()
                .putBoolean(HAS_RETRY_ONCE.getValue(), true)
                .putLong(BACKOFF_DELAY.getValue(), DELAY)
                .putInt(ATTEMPT_COUNT.getValue(), getRunAttemptCount() + 1)
                .build();
        // Create a OneTimeWorkRequest for the retry task with the specified delay and data
        OneTimeWorkRequest retryWorkRequest =
                new OneTimeWorkRequest.Builder(NotificationWorker.class)
                        .setInputData(retryData)
                        .setInitialDelay(DELAY, TimeUnit.MILLISECONDS)
                        .build();
        // Enqueue the retry work request with WorkManager
        WorkManager.getInstance(getApplicationContext()).enqueue(retryWorkRequest);
    }

    private void showNotification(String message) {
        Calendar calendar = Calendar.getInstance();
        //TODO
        String channelId = getApplicationContext().getString(R.string.app_name).concat(String.valueOf(calendar.getTimeInMillis())); // Replace with your channel ID
        String title = "Your Title"; // Replace with the notification title
        // Create a pending intent to open the app when the notification is clicked
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getApplicationContext().getString(R.string.app_name);
            String description = getApplicationContext().getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.notification_bill)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1001, builder.build());
    }
}
