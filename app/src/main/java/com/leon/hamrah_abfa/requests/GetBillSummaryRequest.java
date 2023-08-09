package com.leon.hamrah_abfa.requests;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.ui.dashboard.BillSummary;
import com.leon.hamrah_abfa.fragments.ui.dashboard.DashboardViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetBillSummaryRequest {

    private final Context context;
    private final ICallback callback;

    public GetBillSummaryRequest(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(true);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        //TODO
        Call<BillSummary> call = iAbfaService.getBillSummary("d6685f04-c2ab-4ac4-87d6-0a8769786ac5");
        return callHttpAsync(context, call, new BillSummarySuccessful(callback),
                new BillSummaryIncomplete(context, callback), new BillSummaryFailed(context, callback));
    }

    public interface ICallback {
        void succeed(ArrayList<DashboardViewModel> billSummary);

        void changeUI(boolean show);
    }
}

class BillSummarySuccessful implements ICallbackSucceed<BillSummary> {
    private final GetBillSummaryRequest.ICallback callback;

    public BillSummarySuccessful(GetBillSummaryRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<BillSummary> response) {
        if (response.body() != null) {
            callback.succeed(response.body().billSummaryWrapper);
        }
        callback.changeUI(false);
    }
}

class BillSummaryIncomplete implements ICallbackIncomplete<BillSummary> {

    private final Context context;
    private final GetBillSummaryRequest.ICallback callback;

    public BillSummaryIncomplete(Context context, GetBillSummaryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<BillSummary> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
        }
    }
}

class BillSummaryFailed implements ICallbackFailure {
    private final Context context;
    private final GetBillSummaryRequest.ICallback callback;

    public BillSummaryFailed(Context context, GetBillSummaryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {

        callback.changeUI(false);
        //TODO
        error(context, "failed").show();
    }
}