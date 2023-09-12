package com.app.leon.moshtarak.requests.follow_request;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.follow_request.DetailHistoryItem;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetItemHistoryRequest {
    private final Context context;
    private final ICallback callback;
    private final String id;

    public GetItemHistoryRequest(Context context, ICallback callback, String id) {
        this.context = context;
        this.callback = callback;
        this.id = id;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<DetailHistoryItem> call = iAbfaService.getDetailHistoryItem(id);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new ItemHistorySuccessful(callback),
                new ItemHistoryIncomplete(context, callback), new ItemHistoryFailed(context, callback));
    }

    public interface ICallback {
        void succeed(DetailHistoryItem itemInfo);

        void changeUI(boolean show);
    }
}

class ItemHistorySuccessful implements ICallbackSucceed<DetailHistoryItem> {
    private final GetItemHistoryRequest.ICallback callback;

    public ItemHistorySuccessful(GetItemHistoryRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<DetailHistoryItem> response) {
        if (response.body() != null) {
            callback.succeed(response.body());
        }
        callback.changeUI(false);
    }
}

class ItemHistoryIncomplete implements ICallbackIncomplete<DetailHistoryItem> {
    private final Context context;
    private final GetItemHistoryRequest.ICallback callback;

    public ItemHistoryIncomplete(Context context, GetItemHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<DetailHistoryItem> response) {
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

class ItemHistoryFailed implements ICallbackFailure {
    private final Context context;
    private final GetItemHistoryRequest.ICallback callback;

    public ItemHistoryFailed(Context context, GetItemHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}