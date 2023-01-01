package com.leon.hamrah_abfa.di.component;

import com.google.gson.Gson;
import com.leon.hamrah_abfa.di.module.CustomProgressModule;
import com.leon.hamrah_abfa.di.module.MyDatabaseModule;
import com.leon.hamrah_abfa.di.module.NetworkModule;
import com.leon.hamrah_abfa.di.module.SharedPreferenceModule;
import com.leon.hamrah_abfa.di.view_model.CustomProgressModel;
import com.leon.hamrah_abfa.di.view_model.NetworkHelperModel;
import com.leon.hamrah_abfa.di.view_model.SharedPreferenceModel;
import com.leon.hamrah_abfa.helpers.MyApplication;
import com.leon.hamrah_abfa.utils.MyDatabase;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {MyDatabaseModule.class, SharedPreferenceModule.class, NetworkModule.class,
        CustomProgressModule.class})
public interface ApplicationComponent {

    void inject(MyApplication myApplication);

    MyDatabase MyDatabase();

    SharedPreferenceModel SharedPreferenceModel();

    Gson Gson();

    Retrofit Retrofit();

    NetworkHelperModel NetworkHelperModel();

    CustomProgressModel CustomProgressModel();
}