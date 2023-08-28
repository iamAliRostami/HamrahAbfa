package com.leon.hamrah_abfa.di.view_model;


import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.helpers.MyApplication.checkServerConnection;
import static com.leon.hamrah_abfa.helpers.MyApplication.hasServerPing;
import static com.leon.hamrah_abfa.helpers.MyApplication.setServerPing;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.PermissionManager.isNetworkAvailable;
import static com.leon.hamrah_abfa.utils.ShowFragment.dismissDialog;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.infrastructure.ICallback;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpClientWrapper {
    public static Call<?> call;
    public static boolean cancel;

    public static <T> boolean callHttpAsyncCancelable(Context context, Call<T> call, ICallbackSucceed<T> succeed,
                                                      ICallbackIncomplete<T> incomplete, ICallbackFailure failure) {
        boolean isOnline = isNetworkAvailable(context);
        HttpClientWrapper.call = call;
        cancel = false;
        if (isOnline) {
            if (hasServerPing()) {
                call.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                        if (!cancel) {
                            if (response.isSuccessful()) {
                                ((Activity) context).runOnUiThread(() -> succeed.executeCompleted(response));
                            } else {
                                ((Activity) context).runOnUiThread(() -> incomplete.executeDismissed(response));
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                        if (cancel) {
                            warning(context, R.string.request_canceled).show();
                        } else {
                            call.cancel();
                            ((Activity) context).runOnUiThread(() -> failure.executeFailed(t));
                        }
                    }
                });
            } else {
                isOnline = false;
                setServerPing(checkServerConnection());
                error(context, R.string.error_ping).show();
            }
        } else {
            warning(context, R.string.turn_internet_on).show();
        }
        return isOnline;
    }

    public static <T> boolean callHttpAsyncCached(Context context, Call<T> call, ICallbackSucceed<T> succeed,
                                                  ICallbackIncomplete<T> incomplete, ICallbackFailure failure) {
        HttpClientWrapper.call = call;
        HttpClientWrapper.cancel = false;
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    ((Activity) context).runOnUiThread(() -> succeed.executeCompleted(response));
                } else {
                    ((Activity) context).runOnUiThread(() -> incomplete.executeDismissed(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                if (cancel) {
                    warning(context, R.string.request_canceled).show();
                } else {
                    call.cancel();
                    ((Activity) context).runOnUiThread(() -> failure.executeFailed(t));
                }
            }
        });
        if (!isNetworkAvailable(context)) {
            warning(context, R.string.turn_internet_on).show();
            return false;
        }
        if (!hasServerPing()) {
            setServerPing(checkServerConnection());
            error(context, R.string.error_ping).show();
            return false;
        }
        return true;
    }

    public static <T> boolean callHttpAsync(Context context, Call<T> call, ICallbackSucceed<T> succeed,
                                            ICallbackIncomplete<T> incomplete, ICallbackFailure failure) {
        boolean isOnline = isNetworkAvailable(context);
        if (isOnline) {
            if (hasServerPing()) {
                call.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                        if (response.isSuccessful()) {
                            ((Activity) context).runOnUiThread(() -> succeed.executeCompleted(response));
                        } else {
                            ((Activity) context).runOnUiThread(() -> incomplete.executeDismissed(response));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                        call.cancel();
                        ((Activity) context).runOnUiThread(() -> failure.executeFailed(t));
                    }
                });
            } else {
                isOnline = false;
                setServerPing(checkServerConnection());
                error(context, R.string.error_ping).show();
            }
        } else {
            warning(context, R.string.turn_internet_on).show();
        }
        return isOnline;
    }

    public static <T> boolean callHttpAsyncBackground(Context context, Call<T> call, ICallbackSucceed<T> succeed) {
        boolean isOnline = isNetworkAvailable(context);
        if (isOnline) {
            if (hasServerPing()) {
                call.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                        if (response.isSuccessful()) {
                            succeed.executeCompleted(response);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                        call.cancel();
                    }
                });
            } else {
                isOnline = false;
                setServerPing(checkServerConnection());
            }
        }
        return isOnline;
    }


    public static <T> void callHttpAsync(Context context, Call<T> call, ICallback<T> callback) {
        if (isNetworkAvailable(context)) {
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                    if (response.isSuccessful()) {
                        ((Activity) context).runOnUiThread(() -> callback.executeCompleted(response));
                    } else {
                        ((Activity) context).runOnUiThread(() -> callback.executeDismissed(response));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                    call.cancel();
                    ((Activity) context).runOnUiThread(() -> callback.executeFailed(t));
                }
            });
        } else {
            warning(context, R.string.turn_internet_on).show();
        }
    }

    public static <T> void callHttpAsync(Context context, Call<T> call, CallbackModel<T> callback) {
        if (isNetworkAvailable(context)) {
            call.enqueue(callback);
        } else {
            warning(context, R.string.turn_internet_on).show();
        }
    }

    private static <T> boolean failedExecution(Context context, Response<T> response) {
        try {
            APIError error = parseError(response);
            if (error.status() == 401) {
                dismissDialog(context, WAITING.getValue());
                expiredToken(context);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}