package com.app.leon.moshtarak.utils;

import static com.app.leon.moshtarak.enums.FragmentTags.ASK_YES_NO;
import static com.app.leon.moshtarak.enums.SharedReferenceKeys.ALIAS;
import static com.app.leon.moshtarak.enums.SharedReferenceKeys.BILL_ID;
import static com.app.leon.moshtarak.enums.SharedReferenceKeys.DEBT;
import static com.app.leon.moshtarak.enums.SharedReferenceKeys.ID;
import static com.app.leon.moshtarak.enums.SharedReferenceKeys.MOBILE;
import static com.app.leon.moshtarak.enums.SharedReferenceKeys.OLD_MOBILE;
import static com.leon.toast.RTLToast.error;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.app.leon.moshtarak.fragments.dialog.InfoYesFragment;
import com.app.leon.moshtarak.helpers.MyApplication;
import com.app.leon.moshtarak.R;

import java.lang.annotation.Annotation;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter = MyApplication.getInstance().getApplicationComponent().Retrofit()
                .responseBodyConverter(APIError.class, new Annotation[0]);
//        try (ResponseBody errorBody = response.errorBody()) {
        try {
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                return converter.convert(errorBody);
            }
        } catch (Exception e) {
            return new APIError();
        }
        return new APIError();
    }

    public static String getFailedMessage(Throwable throwable, Context context) {
        String errorMessage;
        if (throwable instanceof SocketTimeoutException) {
            errorMessage = context.getString(R.string.error_connection);
        } else if (throwable instanceof SocketException) {
            errorMessage = context.getString(R.string.connection_lost);
        } else {
            errorMessage = context.getString(R.string.error_other);
        }
        return errorMessage;
    }


    public static void expiredToken(Context context) {
        error(context, R.string.expired_access).show();

        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(OLD_MOBILE.getValue(),
                MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(MOBILE.getValue()));
        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(MOBILE.getValue(), "");
        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(ID.getValue(), "");
        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), "");
        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(ALIAS.getValue(), "");
        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), "");

        ShowFragment.showFragmentDialogOnce(context, ASK_YES_NO.getValue(),
                InfoYesFragment.newInstance(R.drawable.setting_logout, context.getString(R.string.security_error),
                        context.getString(R.string.expired_access), context.getString(R.string.login_again), context.getString(R.string.cancel),
                        fragment -> {
//                            Intent startActivity = new Intent(context, MainActivity.class);
//                            int pendingIntentId = 987654;
//                            PendingIntent pendingIntent = PendingIntent.getActivity(context, pendingIntentId, startActivity,
//                                    PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//                            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
//                            System.exit(0);

                            PackageManager packageManager = context.getPackageManager();
                            Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
                            ComponentName componentName = intent.getComponent();
                            Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                            context.startActivity(mainIntent);
                            Runtime.getRuntime().exit(0);
                        }));
    }

    public static void showFailedMessage(Throwable throwable, Context context) {
        error(context, getFailedMessage(throwable, context), Toast.LENGTH_LONG).show();
    }
}
