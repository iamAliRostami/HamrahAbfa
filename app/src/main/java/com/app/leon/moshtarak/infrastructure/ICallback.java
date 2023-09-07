package com.app.leon.moshtarak.infrastructure;

import retrofit2.Callback;
import retrofit2.Response;

public interface ICallback<T> extends Callback<T> {
    void executeFailed(Throwable t);

    void executeDismissed(Response<T> response);

    void executeCompleted(Response<T> response);
}
