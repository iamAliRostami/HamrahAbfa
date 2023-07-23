package com.leon.hamrah_abfa.utils.background;

import static com.leon.hamrah_abfa.utils.PermissionManager.isNetworkAvailable;
import static com.leon.hamrah_abfa.utils.background.Scheduler.scheduleAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.leon.toast.RTLToast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check for internet connectivity
        if (isNetworkAvailable(context)) {
            // TODO: Send the request to the server
            // You can use Retrofit, Volley, or any other library to send the request.
            Log.e("here","is on");
            RTLToast.success(context,"is on").show();
        } else {
            // Reschedule the alarm with a one-hour delay
            Log.e("here","is off");
            RTLToast.warning(context,"is off").show();
            scheduleAlarm(context, System.currentTimeMillis() + (1 * 60 * /*60  **/ 1000));
        }
    }


}
