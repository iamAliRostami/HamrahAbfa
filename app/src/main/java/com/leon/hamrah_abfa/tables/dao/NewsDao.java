package com.leon.hamrah_abfa.tables.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.leon.hamrah_abfa.tables.News;
import com.leon.hamrah_abfa.tables.User;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM News")
    List<User> getAllNews();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNews(News news);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNews(List<News> news);

    @Query("DELETE FROM News")
    void deleteAllNews();

    @Query("DELETE FROM News WHERE id = :id")
    void deleteNewsById(int id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNews(News news);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNews(List<News> news);

    @Query("UPDATE News set seen = :seen WHERE id = :id")
    void updateNewsSeen(int id, boolean seen);
}
