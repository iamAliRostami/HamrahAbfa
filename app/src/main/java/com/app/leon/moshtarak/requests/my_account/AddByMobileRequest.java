package com.app.leon.moshtarak.requests.my_account;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.success;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.cards.BillsSummary;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddByMobileRequest {
    private final Context context;
    private final ICallback callback;
    private final String mobile;

    public AddByMobileRequest(Context context, ICallback callback, String mobile) {
        this.context = context;
        this.callback = callback;
        this.mobile = mobile;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<BillsSummary> call = iAbfaService.addByMobile(mobile);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new AddByMobileSuccessful(context, callback),
                new AddByMobileIncomplete(context, callback), new AddByMobileFailed(context, callback));
    }

    public interface ICallback {
        void succeed();

        void changeUI(boolean show);
    }
}

class AddByMobileSuccessful implements ICallbackSucceed<BillsSummary> {
    private final AddByMobileRequest.ICallback callback;
    private final Context context;

    public AddByMobileSuccessful(Context context, AddByMobileRequest.ICallback callback) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    public void executeCompleted(Response<BillsSummary> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            success(context, response.body().message).show();
            callback.changeUI(true);
            //TODO restart application
            callback.succeed();
        }
    }
}

class AddByMobileIncomplete implements ICallbackIncomplete<BillsSummary> {
    private final Context context;
    private final AddByMobileRequest.ICallback callback;

    public AddByMobileIncomplete(Context context, AddByMobileRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<BillsSummary> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 400) {
            warning(context, error.message()).show();
        } else if (error.status() == 401) {
            expiredToken(context);
        } else if (error.status() == 500) {
            warning(context, R.string.server_error).show();
        }
    }
}

class AddByMobileFailed implements ICallbackFailure {
    private final AddByMobileRequest.ICallback callback;
    private final Context context;

    public AddByMobileFailed(Context context, AddByMobileRequest.ICallback callback) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}