package com.leon.hamrah_abfa.requests;

import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;
import android.util.Log;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.fragments.contact_us.ContactFAQ;
import com.leon.hamrah_abfa.fragments.contact_us.ContactFAQViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetFAQRequest {

    private final Context context;
    private final ICallback callback;

    public GetFAQRequest(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(true);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<ContactFAQ> call = iAbfaService.getFAQ();
        return HttpClientWrapper.callHttpAsync(context, call, new GetFAQSuccessful(callback),
                new GetFAQIncomplete(context, callback), new GetFAQFailed(context, callback));
    }

    public interface ICallback {
        void succeed(ArrayList<ContactFAQViewModel> faqs);

        void changeUI(boolean show);
    }
}

class GetFAQSuccessful implements ICallbackSucceed<ContactFAQ> {
    private final GetFAQRequest.ICallback callback;

    public GetFAQSuccessful(GetFAQRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<ContactFAQ> response) {
        if (response.body() != null) {
            callback.succeed(response.body().faQs);
        }
        callback.changeUI(false);
    }
}

class GetFAQIncomplete implements ICallbackIncomplete<ContactFAQ> {

    private final Context context;
    private final GetFAQRequest.ICallback callback;

    public GetFAQIncomplete(Context context, GetFAQRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<ContactFAQ> response) {
        callback.changeUI(false);
        warning(context, "dismissed").show();
    }
}

class GetFAQFailed implements ICallbackFailure {
    private final Context context;
    private final GetFAQRequest.ICallback callback;

    public GetFAQFailed(Context context, GetFAQRequest.ICallback callback) {
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
