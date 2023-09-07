package com.app.leon.moshtarak.requests.notification;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.notifications.Notifications;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetNotificationRequest {
    private final Context context;
    private final ICallback callback;

    public GetNotificationRequest(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<Notifications> call = iAbfaService.getNotifications();
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new NotificationSuccessful(callback),
                new NotificationIncomplete(context, callback), new NotificationFailed(context, callback));
    }

    public interface ICallback {
        void succeed(Notifications notifications);

        void changeUI(boolean show);
    }
}

class NotificationSuccessful implements ICallbackSucceed<Notifications> {
    private final GetNotificationRequest.ICallback callback;

    public NotificationSuccessful(GetNotificationRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<Notifications> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.succeed(response.body());
        }
    }
}

class NotificationIncomplete implements ICallbackIncomplete<Notifications> {
    private final Context context;
    private final GetNotificationRequest.ICallback callback;

    public NotificationIncomplete(Context context, GetNotificationRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<Notifications> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            //TODO
            warning(context, "dismissed").show();
        }
    }
}

class NotificationFailed implements ICallbackFailure {
    private final Context context;
    private final GetNotificationRequest.ICallback callback;

    public NotificationFailed(Context context, GetNotificationRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}