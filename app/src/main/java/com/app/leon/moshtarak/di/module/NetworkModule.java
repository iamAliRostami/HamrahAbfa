package com.app.leon.moshtarak.di.module;

import com.app.leon.moshtarak.di.view_model.APIClientModel;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

@Module
public class NetworkModule {
    final APIClientModel networkHelper;

    public NetworkModule() {
        networkHelper = new APIClientModel();
    }

    @Singleton
    @Provides
    public APIClientModel providesNetworkHelperModel() {
        return networkHelper;
    }

    @Singleton
    @Provides
    public Gson providesGson() {
        return networkHelper.getGson();
    }

    @Singleton
    @Provides
    public Retrofit providesRetrofit() {
        return networkHelper.getClient();
    }

    @Singleton
    @Provides
    public HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        return networkHelper.getInterceptor();
    }
}
