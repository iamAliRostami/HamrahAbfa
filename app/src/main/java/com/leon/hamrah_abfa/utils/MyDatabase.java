package com.leon.hamrah_abfa.utils;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.leon.hamrah_abfa.tables.User;
import com.leon.hamrah_abfa.tables.dao.UserDao;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
