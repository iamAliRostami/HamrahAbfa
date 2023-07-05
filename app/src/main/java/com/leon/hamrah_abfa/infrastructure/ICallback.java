package com.leon.hamrah_abfa.infrastructure;

import retrofit2.Response;

public interface ICallback<T> {
    void executeFailed(Throwable t);

    void executeDismissed(Response<T> response);

    void executeCompleted(Response<T> response);
}
