package com.leon.hamrah_abfa.requests.contact_us;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsyncCached;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.contact_us.ContactPhoneBook;
import com.leon.hamrah_abfa.fragments.contact_us.PhonebookViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetTelRequest {

    private final Context context;
    private final ICallback callback;

    public GetTelRequest(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().NetworkHelperModel().getClientCached(context);
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<ContactPhoneBook> call = iAbfaService.getTel();
        return callHttpAsyncCached(context, call, new TelSuccessful(callback),
                new TelIncomplete(context, callback), new TelFailed(context, callback));
    }

    public interface ICallback {
        void succeed(ArrayList<PhonebookViewModel> phonebook);

        void changeUI(boolean show);
    }
}

class TelSuccessful implements ICallbackSucceed<ContactPhoneBook> {
    private final GetTelRequest.ICallback callback;

    public TelSuccessful(GetTelRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<ContactPhoneBook> response) {
        if (response.body() != null) {
            callback.succeed(response.body().telInfoDtos);
        }
        callback.changeUI(false);
    }
}

class TelIncomplete implements ICallbackIncomplete<ContactPhoneBook> {

    private final Context context;
    private final GetTelRequest.ICallback callback;

    public TelIncomplete(Context context, GetTelRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<ContactPhoneBook> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
        }
    }
}

class TelFailed implements ICallbackFailure {
    private final Context context;
    private final GetTelRequest.ICallback callback;

    public TelFailed(Context context, GetTelRequest.ICallback callback) {
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
