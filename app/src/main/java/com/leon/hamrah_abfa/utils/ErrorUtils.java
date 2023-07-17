package com.leon.hamrah_abfa.utils;

import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter = getInstance().getApplicationComponent().Retrofit()
                .responseBodyConverter(APIError.class, new Annotation[0]);
        try (ResponseBody errorBody = response.errorBody()) {
            if (errorBody != null) {
                return converter.convert(errorBody);
            }
        } catch (IOException e) {
            return new APIError();
        }
        return new APIError();
    }
}
