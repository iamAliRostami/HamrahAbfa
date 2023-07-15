package com.leon.hamrah_abfa.requests.follow_request;

import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;
import android.util.Log;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.fragments.follow_request.DetailHistoryItem;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;

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
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<DetailHistoryItem> call = iAbfaService.getDetailHistoryItem(id);
        return HttpClientWrapper.callHttpAsync(context, call, new ItemHistorySuccessful(callback),
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
        warning(context, "dismissed").show();
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
        //TODO
        error(context, "failed").show();
        Log.e("error", t.toString());
    }
}