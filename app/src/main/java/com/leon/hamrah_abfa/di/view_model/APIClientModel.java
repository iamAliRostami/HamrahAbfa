package com.leon.hamrah_abfa.di.view_model;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.TOKEN;
import static com.leon.hamrah_abfa.helpers.DifferentCompanyManager.getBaseUrl;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.PermissionManager.isNetworkAvailable;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leon.hamrah_abfa.R;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public final class APIClientModel {
    private final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private final boolean RETRY_ENABLED = false;
    private final long READ_TIMEOUT = 20;
    private final long WRITE_TIMEOUT = 10;
    private final long CONNECT_TIMEOUT = 10;
//    @Inject
    static OkHttpClient okHttpClient;
//    @Inject
    static Gson gson;
//    @Inject
    static Retrofit retrofit;
//    @Inject
    static HttpLoggingInterceptor interceptor;
    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = chain -> {
        Response originalResponse = chain.proceed(chain.request());
        String cacheControl = originalResponse.header("Cache-Control");

        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 10)
                    .build();
        } else {
            return originalResponse;
        }
    };

    @Inject
    public HttpLoggingInterceptor getInterceptor() {
        if (interceptor == null) {
            interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
        }
        return interceptor;
    }

    @Inject
    public OkHttpClient getHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder().readTimeout(READ_TIMEOUT, TIME_UNIT)
                    .writeTimeout(WRITE_TIMEOUT, TIME_UNIT).connectTimeout(CONNECT_TIMEOUT, TIME_UNIT)
                    .retryOnConnectionFailure(RETRY_ENABLED)
                    .addInterceptor(getToken()).addInterceptor(getInterceptor()).build();
        }
        return okHttpClient;
    }

    @Inject
    public OkHttpClient getHttpClient(int timeDivider) {
        if (timeDivider == 1 || timeDivider <= 0) return getHttpClient();
        return new OkHttpClient.Builder().readTimeout(READ_TIMEOUT / timeDivider, TIME_UNIT)
                .writeTimeout(WRITE_TIMEOUT / timeDivider, TIME_UNIT)
                .connectTimeout(CONNECT_TIMEOUT, TIME_UNIT).retryOnConnectionFailure(RETRY_ENABLED)
                .addInterceptor(getInterceptor()).addInterceptor(getInterceptor()).build();
    }

    @Inject
    public OkHttpClient getHttpClient(int readTimeout, int writeTimeout, int connectTimeout) {
        if (readTimeout <= 1 || writeTimeout <= 1 || connectTimeout <= 1) return getHttpClient();
        return new OkHttpClient.Builder().readTimeout(readTimeout, TIME_UNIT)
                .writeTimeout(writeTimeout, TIME_UNIT).connectTimeout(connectTimeout, TIME_UNIT)
                .retryOnConnectionFailure(RETRY_ENABLED)
                .addInterceptor(getToken()).addInterceptor(getInterceptor()).build();
    }

    private Interceptor getToken() {
        return chain -> {
            Request request = chain.request().newBuilder().addHeader("X-Authorization",
                            getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(TOKEN.getValue()))
                    .build();
            return chain.proceed(request);
        };
    }

    @Inject
    public Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(getBaseUrl())
                    .client(getInstance().getApplicationComponent().NetworkHelperModel().getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(getInstance().getApplicationComponent().Gson()))
                    .addConverterFactory(ScalarsConverterFactory.create()).build();
        }
        return retrofit;
    }

    @Inject
    public Retrofit getClientCached(Context context) {

        int cacheSize = 50 * 1024 * 1024;// 50 MB
        File httpCacheDirectory = new File(context.getCacheDir(), context.getString(R.string.cache_folder));
        Cache cache = new Cache(httpCacheDirectory, cacheSize);


        return new Retrofit.Builder().baseUrl(getBaseUrl())
                .client(new OkHttpClient.Builder().readTimeout(READ_TIMEOUT, TIME_UNIT)
                        .writeTimeout(WRITE_TIMEOUT, TIME_UNIT).connectTimeout(CONNECT_TIMEOUT, TIME_UNIT)
                        .retryOnConnectionFailure(RETRY_ENABLED)
                        .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                        .addInterceptor(getOfflineInterceptor(context))
                        .addInterceptor(getToken()).addInterceptor(getInterceptor())
                        .cache(cache)
                        .build())
                .addConverterFactory(GsonConverterFactory.create(getInstance().getApplicationComponent().Gson()))
                .addConverterFactory(ScalarsConverterFactory.create()).build();
    }

    private Interceptor getOfflineInterceptor(Context context) {
        return chain -> {
            Request request = chain.request();

            if (!isNetworkAvailable(context)) {
                int maxStale = 60 * 10;
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .header("Cache-Control", "public, only-if-cached, max-age=" + maxStale)
                        .build();
            } else
                request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();

            return chain.proceed(request);
        };
    }

    @Inject
    public Retrofit getClient(int timeDivider) {
        if (timeDivider == 1 || timeDivider <= 0) return getClient();
        return new Retrofit.Builder().baseUrl(getBaseUrl())
                .client(getInstance().getApplicationComponent().NetworkHelperModel().getHttpClient(timeDivider))
                .addConverterFactory(GsonConverterFactory.create(getInstance().getApplicationComponent().Gson()))
                .addConverterFactory(ScalarsConverterFactory.create()).build();
    }


    @Inject
    public Retrofit getClient(int readTimeout, int writeTimeout, int connectTimeout) {
        if (readTimeout == 1 || readTimeout <= 0 || writeTimeout == 1 || writeTimeout <= 0 ||
                connectTimeout == 1 || connectTimeout <= 0) return getClient();
        return new Retrofit.Builder().baseUrl(getBaseUrl())
                .client(new OkHttpClient.Builder().readTimeout(readTimeout, TIME_UNIT)
                        .writeTimeout(writeTimeout, TIME_UNIT).connectTimeout(connectTimeout, TIME_UNIT)
                        .retryOnConnectionFailure(RETRY_ENABLED).addInterceptor(getInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create(getInstance().getApplicationComponent().Gson()))
                .build();
    }

    @Inject
    public Gson getGson() {
        if (gson == null) gson = new GsonBuilder().setLenient().create();
        return gson;
    }
}