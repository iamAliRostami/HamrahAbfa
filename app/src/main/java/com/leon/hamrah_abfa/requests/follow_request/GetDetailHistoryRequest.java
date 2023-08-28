package com.leon.hamrah_abfa.requests.follow_request;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.fragments.follow_request.DetailHistory;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

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
        } else {
            warning(context, "dismissed").show();
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