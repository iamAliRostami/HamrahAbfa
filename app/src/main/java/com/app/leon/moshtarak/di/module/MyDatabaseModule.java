package com.app.leon.moshtarak.di.module;

import android.content.Context;

import com.app.leon.moshtarak.di.view_model.MyDatabaseClientModel;
import com.app.leon.moshtarak.utils.MyDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MyDatabaseModule {
    private final MyDatabase database;

    public MyDatabaseModule(Context context) {
        this.database = MyDatabaseClientModel.getInstance(context).getMyDatabase();
    }

    @Singleton
    @Provides
    public MyDatabase providesMyDatabase() {
        return database;
    }
}
