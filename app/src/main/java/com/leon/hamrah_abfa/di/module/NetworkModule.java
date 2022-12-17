package com.leon.hamrah_abfa.di.module;

import com.google.gson.Gson;
import com.leon.hamrah_abfa.di.view_model.NetworkHelperModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class NetworkModule {
    final NetworkHelperModel networkHelper;

    public NetworkModule() {
        networkHelper = new NetworkHelperModel();
    }

    @Singleton
    @Provides
    public Gson providesGson() {
        return networkHelper.getGson();
    }

    @Singleton
    @Provides
    public Retrofit providesRetrofit() {
        return networkHelper.getInstance();
    }

    @Singleton
    @Provides
    public NetworkHelperModel providesNetworkHelperModel() {
        return networkHelper;
    }
}
