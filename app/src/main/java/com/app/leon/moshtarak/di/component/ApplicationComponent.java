package com.app.leon.moshtarak.di.component;

import com.app.leon.moshtarak.di.module.MyDatabaseModule;
import com.app.leon.moshtarak.di.module.NetworkModule;
import com.app.leon.moshtarak.di.module.SharedPreferenceModule;
import com.app.leon.moshtarak.di.view_model.APIClientModel;
import com.app.leon.moshtarak.di.view_model.SharedPreferenceModel;
import com.app.leon.moshtarak.helpers.MyApplication;
import com.app.leon.moshtarak.utils.MyDatabase;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {MyDatabaseModule.class, SharedPreferenceModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(MyApplication myApplication);

    MyDatabase MyDatabase();

    SharedPreferenceModel SharedPreferenceModel();

    Gson Gson();

    Retrofit Retrofit();

    APIClientModel NetworkHelperModel();

    HttpLoggingInterceptor HttpLoggingInterceptor();
}
