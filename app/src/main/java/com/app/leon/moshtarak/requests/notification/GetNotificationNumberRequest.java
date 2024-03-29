package com.app.leon.moshtarak.requests.notification;

import static com.app.leon.moshtarak.di.view_model.HttpClientWrapper.callHttpAsyncBackground;
import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;

import android.content.Context;

import com.app.leon.moshtarak.fragments.notifications.Notifications;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetNotificationNumberRequest {
    private final Context context;
    private final ICallback callback;

    public GetNotificationNumberRequest(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<Notifications> call = iAbfaService.getNotifications();
        return callHttpAsyncBackground(context, call, new NotificationNumberSuccessful(callback));
    }

    public interface ICallback {
        void succeed(int messageNumber);
    }
}

class NotificationNumberSuccessful implements ICallbackSucceed<Notifications> {
    private final GetNotificationNumberRequest.ICallback callback;

    public NotificationNumberSuccessful(GetNotificationNumberRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<Notifications> response) {
        if (response.body() != null && !response.body().customerNotifications.isEmpty()) {
            callback.succeed(response.body().customerNotifications.size());
        }
    }
}