package com.app.leon.moshtarak.requests.my_account;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.bottom_sheets.MobileHistory;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetMobileHistoryRequest {
    private final Context context;
    private final ICallback callback;

    public GetMobileHistoryRequest(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<MobileHistory> call = iAbfaService.mobileHistory();
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new MobileHistorySuccessful(callback),
                new MobileHistoryIncomplete(context, callback), new MobileHistoryFailed(context, callback));
    }

    public interface ICallback {
        void succeed(MobileHistory session);

        void changeUI(boolean show);
    }
}

class MobileHistorySuccessful implements ICallbackSucceed<MobileHistory> {
    private final GetMobileHistoryRequest.ICallback callback;

    public MobileHistorySuccessful(GetMobileHistoryRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<MobileHistory> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.succeed(response.body());
        }
    }
}

class MobileHistoryIncomplete implements ICallbackIncomplete<MobileHistory> {
    private final Context context;
    private final GetMobileHistoryRequest.ICallback callback;

    public MobileHistoryIncomplete(Context context, GetMobileHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<MobileHistory> response) {
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

class MobileHistoryFailed implements ICallbackFailure {
    private final Context context;
    private final GetMobileHistoryRequest.ICallback callback;

    public MobileHistoryFailed(Context context, GetMobileHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}