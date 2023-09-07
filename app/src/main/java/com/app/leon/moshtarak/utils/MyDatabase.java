package com.app.leon.moshtarak.utils;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.app.leon.moshtarak.tables.News;
import com.app.leon.moshtarak.tables.Notification;
import com.app.leon.moshtarak.tables.User;
import com.app.leon.moshtarak.tables.dao.NewsDao;
import com.app.leon.moshtarak.tables.dao.NotificationDao;
import com.app.leon.moshtarak.tables.dao.UserDao;

@Database(entities = {User.class, Notification.class, News.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public static final Migration MIGRATION_2_3 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE 'News' ADD COLUMN 'test' INTEGER NOT NULL DEFAULT 0");
            database.execSQL("CREATE TABLE t1_backup AS SELECT id, customId, summary, title, text, date, seen, category  FROM News");
            database.execSQL("DROP TABLE News");
            database.execSQL("ALTER TABLE t1_backup RENAME TO News");
        }
    };

    public abstract UserDao userDao();

    public abstract NewsDao newsDao();

    public abstract NotificationDao notificationDao();
}
