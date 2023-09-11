package com.app.leon.moshtarak.requests.bill;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;

import android.content.Context;

import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.cards.BillsSummary;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetBillsRequest {
    private final Context context;
    private final ICallback callback;

    public GetBillsRequest(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void request() {
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<BillsSummary> call = iAbfaService.getBills();
        HttpClientWrapper.callHttpAsyncCancelable(context, call,
                new GetBillsSuccessful(callback), response -> {
                    APIError error = parseError(response);
                    if (error.status() == 401) {
                        expiredToken(context);
                    }
                }, t -> {
                });
    }

    public interface ICallback {
        void succeed(BillsSummary bills);
    }
}

class GetBillsSuccessful implements ICallbackSucceed<BillsSummary> {
    private final GetBillsRequest.ICallback callback;

    public GetBillsSuccessful(GetBillsRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<BillsSummary> response) {
        if (response.body() != null) {
            if (response.body().billDtos != null)
                callback.succeed(response.body());
        }
    }
}