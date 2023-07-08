package com.leon.hamrah_abfa.utils.bill;

import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.di.view_model.Bills;
import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;

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
        callback.changeUI(false);

        showFragmentDialogOnce(context, WAITING.getValue(), WaitingFragment.newInstance());
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<Bills> call = iAbfaService.getBills();
        HttpClientWrapper.callHttpAsync(context, call, new GetBillsSuccessful(callback),
                new GetBillsIncomplete(context, callback), new GetBillsFailed(context, callback));
    }

    public interface ICallback {
        void succeed();

        void changeUI(boolean done);
    }
}

class GetBillsSuccessful implements ICallbackSucceed<Bills> {
    private final GetBillsRequest.ICallback callback;

    public GetBillsSuccessful(GetBillsRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<Bills> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed();
        }

    }
}

class GetBillsIncomplete implements ICallbackIncomplete<Bills> {
    private final Context context;
    private final GetBillsRequest.ICallback callback;

    public GetBillsIncomplete(Context context, GetBillsRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<Bills> response) {
        callback.changeUI(true);
        //TODO
        warning(context, "dismissed").show();
    }
}

class GetBillsFailed implements ICallbackFailure {
    private final Context context;
    private final GetBillsRequest.ICallback callback;

    public GetBillsFailed(Context context, GetBillsRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(true);
        //TODO
        error(context, "failed").show();
    }
}