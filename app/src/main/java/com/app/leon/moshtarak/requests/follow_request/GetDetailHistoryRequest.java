package com.app.leon.moshtarak.requests.follow_request;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.follow_request.DetailHistory;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetDetailHistoryRequest {
    private final Context context;
    private final ICallback callback;
    private final int trackNumber;

    public GetDetailHistoryRequest(Context context, ICallback callback, int trackNumber) {
        this.context = context;
        this.callback = callback;
        this.trackNumber = trackNumber;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<DetailHistory> call = iAbfaService.getDetailHistory(trackNumber);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new DetailHistorySuccessful(callback),
                new DetailHistoryIncomplete(context, callback), new DetailHistoryFailed(context, callback));
    }

    public interface ICallback {
        void succeed(DetailHistory requestInfo);

        void changeUI(boolean show);
    }
}

class DetailHistorySuccessful implements ICallbackSucceed<DetailHistory> {
    private final GetDetailHistoryRequest.ICallback callback;

    public DetailHistorySuccessful(GetDetailHistoryRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<DetailHistory> response) {
        if (response.body() != null) {
            callback.succeed(response.body());
        }
        callback.changeUI(false);
    }
}

class DetailHistoryIncomplete implements ICallbackIncomplete<DetailHistory> {
    private final Context context;
    private final GetDetailHistoryRequest.ICallback callback;

    public DetailHistoryIncomplete(Context context, GetDetailHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<DetailHistory> response) {
        //TODO
        callback.changeUI(false);
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

class DetailHistoryFailed implements ICallbackFailure {
    private final Context context;
    private final GetDetailHistoryRequest.ICallback callback;

    public DetailHistoryFailed(Context context, GetDetailHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}