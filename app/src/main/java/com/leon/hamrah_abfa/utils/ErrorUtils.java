package com.leon.hamrah_abfa.utils;

import static com.leon.hamrah_abfa.enums.FragmentTags.ASK_YES_NO;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ALIAS;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEBT;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.MOBILE;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.OLD_MOBILE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.error;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.fragments.dialog.InfoYesFragment;

import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter = getInstance().getApplicationComponent().Retrofit()
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

    public static void expiredToken(Context context) {
        error(context, R.string.expired_access).show();

        getInstance().getApplicationComponent().SharedPreferenceModel().putData(OLD_MOBILE.getValue(),
                getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(MOBILE.getValue()));
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(MOBILE.getValue(), "");
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ID.getValue(), "");
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), "");
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ALIAS.getValue(), "");
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), "");

        showFragmentDialogOnce(context, ASK_YES_NO.getValue(),
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

    public static String getFailedMessage(Throwable throwable, Context context) {
        String errorMessage;
        if (throwable instanceof SocketTimeoutException) {
            errorMessage = context.getString(R.string.error_connection);
        } else {
            errorMessage = context.getString(R.string.error_other);
        }
        return errorMessage;
    }

    public static void showFailedMessage(Throwable throwable, Context context) {
        error(context, getFailedMessage(throwable, context), Toast.LENGTH_LONG).show();
    }
}
