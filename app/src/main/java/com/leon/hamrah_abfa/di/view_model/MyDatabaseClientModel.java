package com.leon.hamrah_abfa.di.view_model;


import static com.leon.hamrah_abfa.helpers.Constants.DBName;

import android.content.Context;

import androidx.room.Room;

import com.leon.hamrah_abfa.utils.MyDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class MyDatabaseClientModel {
    private static MyDatabaseClientModel instance;
    private final MyDatabase myDatabase;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    @Inject
    public MyDatabaseClientModel(Context context) {
        myDatabase = Room.databaseBuilder(context, MyDatabase.class, DBName)
                .allowMainThreadQueries().build();
    }

    public static synchronized MyDatabaseClientModel getInstance(Context context) {
        if (instance == null) instance = new MyDatabaseClientModel(context);
        return instance;
    }

    public MyDatabase getMyDatabase() {
        return myDatabase;
    }
}