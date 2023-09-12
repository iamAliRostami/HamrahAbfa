package com.app.leon.moshtarak.requests.contact_us;

import static com.app.leon.moshtarak.di.view_model.HttpClientWrapper.callHttpAsyncCached;
import static com.app.leon.moshtarak.enums.FragmentTags.REQUEST_DONE;
import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import androidx.fragment.app.DialogFragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.fragments.contact_us.ContactPhoneBook;
import com.app.leon.moshtarak.fragments.contact_us.PhonebookViewModel;
import com.app.leon.moshtarak.fragments.dialog.MessageErrorRequestFragment;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

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
        }else if (error.status() == 400) {
            warning(context,error.message()).show();
        } else if (error.status() == 500) {
            warning(context, R.string.server_error).show();
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
        showFailedMessage(t, context);
    }
}
