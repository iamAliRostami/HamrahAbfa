package com.leon.hamrah_abfa.requests;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsyncBackground;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.notifications.Notifications;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;

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
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<Notifications> call = iAbfaService.getNotifications();
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