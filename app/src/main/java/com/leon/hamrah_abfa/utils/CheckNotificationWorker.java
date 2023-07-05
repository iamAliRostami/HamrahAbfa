package com.leon.hamrah_abfa.utils;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.leon.hamrah_abfa.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckNotificationWorker extends Worker {
    private final Context context;

    public CheckNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final CharSequence name = context.getString(R.string.app_name);
            final String description = context.getString(R.string.app_name);
            final int importance = NotificationManager.IMPORTANCE_DEFAULT;
            final NotificationChannel channel = new NotificationChannel(context.getString(R.string.app_name), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:MMM", Locale.getDefault());
        String formattedDate = dateFormat.format(Calendar.getInstance().getTime());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.app_name))
                .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                .setContentTitle("New Location Update")
                .setContentText("You are at ".concat(formattedDate))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("You are at ".concat(formattedDate)));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return Result.retry();
        }
        notificationManager.notify(1001, builder.build());


        return Result.success();
    }
}
