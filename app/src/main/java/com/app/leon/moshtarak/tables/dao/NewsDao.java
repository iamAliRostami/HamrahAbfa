package com.app.leon.moshtarak.tables.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.app.leon.moshtarak.tables.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM News")
    List<News> getAllNews();

    @Query("SELECT * FROM News WHERE billId = :billId")
    List<News> getNewsBillId(String billId);

    @Query("SELECT COUNT(*) FROM News WHERE billId = :billId AND seen = :seen")
    int getUnseenNewsNumber(String billId, boolean seen);

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

    @Query("UPDATE News set seen = :seen WHERE customId = :id")
    void updateNewsSeen(int id, boolean seen);
}
