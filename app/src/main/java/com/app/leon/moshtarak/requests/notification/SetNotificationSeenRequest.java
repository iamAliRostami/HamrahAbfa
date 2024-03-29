package com.app.leon.moshtarak.requests.notification;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.R;
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
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<Notifications> call = iAbfaService.setNotificationSeen(id);
        HttpClientWrapper.callHttpAsyncCancelable(context, call, new SeenNotificationSuccessful(callback),
                new SeenNotificationIncomplete(context), new SeenNotificationFailed(context));
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
        }else if (error.status() == 400) {
            warning(context,error.message()).show();
        } else if (error.status() == 500) {
            warning(context, R.string.server_error).show();
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