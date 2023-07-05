package com.leon.hamrah_abfa.infrastructure;

import retrofit2.Response;

public interface ICallbackIncomplete<T> {
    void executeDismissed(Response<T> response);
}
