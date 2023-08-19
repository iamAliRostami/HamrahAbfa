package com.leon.hamrah_abfa.requests.follow_request;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;
import android.util.Log;

import com.leon.hamrah_abfa.fragments.follow_request.MasterHistory;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetMasterHistoryRequest {
    private final Context context;
    private final ICallback callback;
    private final String id;

    public GetMasterHistoryRequest(Context context, ICallback callback, String id) {
        this.context = context;
        this.callback = callback;
        this.id = id;
    }

    public boolean request() {
        callback.changeUI(true);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<MasterHistory> call = iAbfaService.getMasterHistory(id);
        return callHttpAsync(context, call, new MasterHistorySuccessful(callback),
                new MasterHistoryIncomplete(context, callback), new MasterHistoryFailed(context, callback));
    }

    public interface ICallback {
        void succeed(MasterHistory requestInfo);

        void changeUI(boolean show);
    }
}

class MasterHistorySuccessful implements ICallbackSucceed<MasterHistory> {
    private final GetMasterHistoryRequest.ICallback callback;

    public MasterHistorySuccessful(GetMasterHistoryRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<MasterHistory> response) {
        if (response.body() != null) {
            callback.succeed(response.body());
        }
        callback.changeUI(false);
    }
}

class MasterHistoryIncomplete implements ICallbackIncomplete<MasterHistory> {
    private final Context context;
    private final GetMasterHistoryRequest.ICallback callback;

    public MasterHistoryIncomplete(Context context, GetMasterHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<MasterHistory> response) {
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

class MasterHistoryFailed implements ICallbackFailure {
    private final Context context;
    private final GetMasterHistoryRequest.ICallback callback;

    public MasterHistoryFailed(Context context, GetMasterHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}