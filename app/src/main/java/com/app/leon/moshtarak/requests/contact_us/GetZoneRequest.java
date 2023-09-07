package com.app.leon.moshtarak.requests.contact_us;

import static com.app.leon.moshtarak.di.view_model.HttpClientWrapper.callHttpAsyncCached;
import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.fragments.contact_us.BranchViewModel;
import com.app.leon.moshtarak.fragments.contact_us.ContactBranch;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetZoneRequest {

    private final Context context;
    private final ICallback callback;

    public GetZoneRequest(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().NetworkHelperModel().getClientCached(context);
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<ContactBranch> call = iAbfaService.getZone();
        return callHttpAsyncCached(context, call, new ZoneSuccessful(callback),
                new ZoneIncomplete(context, callback), new ZoneFailed(context, callback));
    }

    public interface ICallback {
        void succeed(ArrayList<BranchViewModel> branches);

        void changeUI(boolean show);
    }
}

class ZoneSuccessful implements ICallbackSucceed<ContactBranch> {
    private final GetZoneRequest.ICallback callback;

    public ZoneSuccessful(GetZoneRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<ContactBranch> response) {
        if (response.body() != null) {
            callback.succeed(response.body().zoneInfoDtos);
        }
        callback.changeUI(false);
    }
}

class ZoneIncomplete implements ICallbackIncomplete<ContactBranch> {

    private final Context context;
    private final GetZoneRequest.ICallback callback;

    public ZoneIncomplete(Context context, GetZoneRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<ContactBranch> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
        }
    }
}

class ZoneFailed implements ICallbackFailure {
    private final Context context;
    private final GetZoneRequest.ICallback callback;

    public ZoneFailed(Context context, GetZoneRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}
