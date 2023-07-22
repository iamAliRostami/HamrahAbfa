package com.leon.hamrah_abfa.helpers;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.activities.MainActivity;

public class BackgroundService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Make the network request to the server here
        // For example, you can use Retrofit or Volley to handle network requests

        // Process the server response
        // ...

        // Create and show the notification with the result
        String channelId = getString(R.string.app_name); // Replace with your channel ID
        String title = "Your Title"; // Replace with the notification title
        String message = "Your message goes here"; // Replace with the notification message

        Log.e(title, message);
        showNotification(channelId, title, message);

        return START_NOT_STICKY;
    }

    // Helper method to show a notification
    private void showNotification(String channelId, String title, String message) {
        // Create a pending intent to open the app when the notification is clicked
        Intent notificationIntent = new Intent(this, MainActivity.class); // Replace YourActivity with the activity you want to open


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notification_bill)
                .setContentTitle("قبض")
                .setContentText("قبض جدید صادر شد")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("قبض جدید صادر شد..."));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
