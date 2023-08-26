package com.leon.hamrah_abfa.requests.notification;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.notifications.Notifications;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SetNotificationSeenRequest {
    private final Context context;
    private final ICallback callback;
    private final String id;

    public SetNotificationSeenRequest(Context context, ICallback callback, String id) {
        this.context = context;
        this.callback = callback;
        this.id = id;
    }

    public void request() {
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<Notifications> call = iAbfaService.setNotificationSeen(id);
        callback.succeed(new Notifications());
//         callHttpAsync(context, call, new SeenNotificationSuccessful(callback),
//                new SeenNotificationIncomplete(context), new SeenNotificationFailed(context));
    }

    public interface ICallback {
        void succeed(Notifications notifications);
    }
}

class SeenNotificationSuccessful implements ICallbackSucceed<Notifications> {
    private final SetNotificationSeenRequest.ICallback callback;

    public SeenNotificationSuccessful(SetNotificationSeenRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<Notifications> response) {
        if (response.body() != null) {
            callback.succeed(response.body());
        }
    }
}

class SeenNotificationIncomplete implements ICallbackIncomplete<Notifications> {
    private final Context context;

    public SeenNotificationIncomplete(Context context) {
        this.context = context;
    }

    @Override
    public void executeDismissed(Response<Notifications> response) {
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            //TODO
            warning(context, "dismissed").show();
        }
    }
}

class SeenNotificationFailed implements ICallbackFailure {
    private final Context context;

    public SeenNotificationFailed(Context context) {
        this.context = context;
    }

    @Override
    public void executeFailed(Throwable t) {
        showFailedMessage(t, context);
    }
}