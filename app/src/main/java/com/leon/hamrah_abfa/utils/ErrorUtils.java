package com.leon.hamrah_abfa.utils;

import static com.leon.hamrah_abfa.enums.FragmentTags.ASK_YES_NO;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ALIAS;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEBT;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ID;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.error;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.activities.MainActivity;
import com.leon.hamrah_abfa.fragments.dialog.YesNoFragment;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter = getInstance().getApplicationComponent().Retrofit()
                .responseBodyConverter(APIError.class, new Annotation[0]);
        try (ResponseBody errorBody = response.errorBody()) {
            if (errorBody != null) {
                return converter.convert(errorBody);
            }
        } catch (IOException e) {
            return new APIError();
        }
        return new APIError();
    }


    public static void expiredToken(Context context) {
        error(context, R.string.expired_access).show();

        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ID.getValue(), "");
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), "");
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ALIAS.getValue(), "");
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), "");

        showFragmentDialogOnce(context, ASK_YES_NO.getValue(),
                YesNoFragment.newInstance(R.drawable.setting_logout, context.getString(R.string.security_error),
                        context.getString(R.string.expired_access), context.getString(R.string.login_again)
                        , context.getString(R.string.cancel), new YesNoFragment.IClickListener() {
                            @Override
                            public void yes(DialogFragment fragment) {
                                Intent startActivity = new Intent(context, MainActivity.class);
                                int pendingIntentId = 123456;
                                PendingIntent pendingIntent = PendingIntent.getActivity(context, pendingIntentId, startActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                                alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
                                System.exit(0);
                            }

                            @Override
                            public void no(DialogFragment fragment) {
                                fragment.dismiss();
                            }
                        })
        );
    }
}
