package com.leon.hamrah_abfa.utils;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.leon.hamrah_abfa.tables.News;
import com.leon.hamrah_abfa.tables.Notification;
import com.leon.hamrah_abfa.tables.User;
import com.leon.hamrah_abfa.tables.dao.NewsDao;
import com.leon.hamrah_abfa.tables.dao.NotificationDao;
import com.leon.hamrah_abfa.tables.dao.UserDao;

@Database(entities = {User.class, Notification.class, News.class},
        version = 2, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract NewsDao newsDao();

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

//            database.execSQL("CREATE UNIQUE INDEX id ON news (id)");
//            database.execSQL("CREATE UNIQUE INDEX id ON Notification (id)");

//            database.execSQL("CREATE UNIQUE INDEX [IF NOT EXISTS] ON news (id)");
//            database.execSQL("CREATE UNIQUE INDEX [IF NOT EXISTS] ON Notification (id)");
//            database.execSQL("CREATE TABLE t1_backup AS SELECT * FROM News");
//            database.execSQL("DROP TABLE News");
//            database.execSQL("ALTER TABLE t1_backup RENAME TO News");
//            database.execSQL("DROP TABLE t1_backup");
        }
    };

    public abstract NotificationDao notificationDao();
}
