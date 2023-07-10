package com.leon.hamrah_abfa.utils;

import com.leon.hamrah_abfa.helpers.MyApplication;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                MyApplication.getInstance().getApplicationComponent().Retrofit()
                        .responseBodyConverter(APIError.class, new Annotation[0]);
        APIError error;
        try (ResponseBody errorBody = response.errorBody()) {
            assert errorBody != null;
            error = converter.convert(errorBody);
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}
