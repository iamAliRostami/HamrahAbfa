package com.app.leon.moshtarak.requests.contact_us;

import static com.app.leon.moshtarak.di.view_model.HttpClientWrapper.callHttpAsyncCached;
import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.fragments.contact_us.ContactFAQ;
import com.app.leon.moshtarak.fragments.contact_us.ContactFAQViewModel;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

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
        showFailedMessage(t, context);
    }
}
