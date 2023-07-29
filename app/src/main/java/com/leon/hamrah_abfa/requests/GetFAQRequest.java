package com.leon.hamrah_abfa.requests;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsyncCached;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.contact_us.ContactFAQ;
import com.leon.hamrah_abfa.fragments.contact_us.ContactFAQViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

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
        Retrofit retrofit = getInstance().getApplicationComponent().NetworkHelperModel().getClientCached(context);
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<ContactFAQ> call = iAbfaService.getFAQ();
        return callHttpAsyncCached(context, call, new FAQSuccessful(callback),
                new FAQIncomplete(context, callback), new FAQFailed(context, callback));
    }

    public interface ICallback {
        void succeed(ArrayList<ContactFAQViewModel> faqs);

        void changeUI(boolean show);
    }
}

class FAQSuccessful implements ICallbackSucceed<ContactFAQ> {
    private final GetFAQRequest.ICallback callback;

    public FAQSuccessful(GetFAQRequest.ICallback callback) {
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

class FAQIncomplete implements ICallbackIncomplete<ContactFAQ> {

    private final Context context;
    private final GetFAQRequest.ICallback callback;

    public FAQIncomplete(Context context, GetFAQRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<ContactFAQ> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
        }
    }
}

class FAQFailed implements ICallbackFailure {
    private final Context context;
    private final GetFAQRequest.ICallback callback;

    public FAQFailed(Context context, GetFAQRequest.ICallback callback) {
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
