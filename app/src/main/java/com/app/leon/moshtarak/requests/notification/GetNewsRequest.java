package com.app.leon.moshtarak.requests.notification;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.notifications.News;
import com.app.leon.moshtarak.fragments.notifications.Notifications;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetNewsRequest {
    private final Context context;
    private final ICallback callback;

    public GetNewsRequest(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<News> call = iAbfaService.getNews();
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new NewsSuccessful(callback),
                new NewsIncomplete(context, callback), new NewsFailed(context, callback));
    }

    public interface ICallback {
        void succeed(News news);

        void changeUI(boolean show);
    }
}

class NewsSuccessful implements ICallbackSucceed<News> {
    private final GetNewsRequest.ICallback callback;

    public NewsSuccessful(GetNewsRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<News> response) {
//        callback.changeUI(false);
        if (response.body() != null) {
            callback.succeed(response.body());
        }
    }
}

class NewsIncomplete implements ICallbackIncomplete<News> {
    private final Context context;
    private final GetNewsRequest.ICallback callback;

    public NewsIncomplete(Context context, GetNewsRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<News> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else if (error.status() == 400) {
            warning(context,error.message()).show();
        } else if (error.status() == 500) {
            warning(context, R.string.server_error).show();
        }
    }
}

class NewsFailed implements ICallbackFailure {
    private final Context context;
    private final GetNewsRequest.ICallback callback;

    public NewsFailed(Context context, GetNewsRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}