package com.app.leon.moshtarak.requests.follow_request;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.follow_request.MasterHistory;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

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
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<MasterHistory> call = iAbfaService.getMasterHistory(id);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new MasterHistorySuccessful(callback),
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
        }else if (error.status() == 400) {
            warning(context,error.message()).show();
        } else if (error.status() == 500) {
            warning(context, R.string.server_error).show();
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