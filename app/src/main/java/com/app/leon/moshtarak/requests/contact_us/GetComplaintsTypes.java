package com.app.leon.moshtarak.requests.contact_us;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.activities.ContactUsActivity;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.contact_us.FeedbackType;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetComplaintsTypes {
    private final Context context;
    private final ICallback callback;

    public GetComplaintsTypes(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<ArrayList<FeedbackType>> call = iAbfaService.getComplaintsTypes();
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new ComplaintTypeSuccessful(callback),
                new ComplaintTypeIncomplete(context, callback), new ComplaintTypeFailed(context, callback));


    }

    public interface ICallback {
        void succeed(ArrayList<FeedbackType> feedbackType);

        void changeUI(boolean done);
    }
}

class ComplaintTypeSuccessful implements ICallbackSucceed<ArrayList<FeedbackType>> {
    private final GetComplaintsTypes.ICallback callback;

    public ComplaintTypeSuccessful(GetComplaintsTypes.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<ArrayList<FeedbackType>> response) {
        callback.succeed(response.body());
        callback.changeUI(false);
    }
}

class ComplaintTypeIncomplete implements ICallbackIncomplete<ArrayList<FeedbackType>> {
    private final GetComplaintsTypes.ICallback callback;
    private final Context context;

    public ComplaintTypeIncomplete(Context context, GetComplaintsTypes.ICallback callback) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    public void executeDismissed(Response<ArrayList<FeedbackType>> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
        }
        ((ContactUsActivity) context).getSupportFragmentManager().popBackStack();
    }
}

class ComplaintTypeFailed implements ICallbackFailure {
    private final Context context;
    private final GetComplaintsTypes.ICallback callback;

    public ComplaintTypeFailed(Context context, GetComplaintsTypes.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
        ((ContactUsActivity) context).getSupportFragmentManager().popBackStack();
    }
}