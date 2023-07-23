package com.leon.hamrah_abfa.utils.background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.leon.toast.RTLToast;

public class MyForegroundService extends Service {
    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                RTLToast.warning(context, "Service is still running").show();
                Log.e("status", "Service is still running");
                handler.postDelayed(runnable, 2000);
            }
        };

        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        Log.e("status", "Service stopped");
        RTLToast.error(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

//    @Override
//    public void onStart(Intent intent, int startid) {
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RTLToast.success(this, "Service started by user.", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        RTLToast.warning(this,"here").show();
//        return super.onStartCommand(intent, flags, startId);
//    }
//    private void showNotification(String channelId, String title, String message) {
//        // Create a pending intent to open the app when the notification is clicked
//        Intent notificationIntent = new Intent(this, MainActivity.class); // Replace YourActivity with the activity you want to open
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.app_name);
//            String description = getString(R.string.app_name);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
//                .setSmallIcon(R.drawable.notification_bill)
//                .setContentTitle("قبض")
//                .setContentText("قبض جدید صادر شد")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("قبض جدید صادر شد..."));
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        notificationManager.notify(1001, builder.build());
//    }
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
}
