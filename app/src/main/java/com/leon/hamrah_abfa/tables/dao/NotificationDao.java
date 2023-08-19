package com.leon.hamrah_abfa.tables.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.leon.hamrah_abfa.tables.Notification;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotificationDao {

    @Query("SELECT * FROM Notification")
    List<Notification> getAllNotifications();

    @Query("SELECT * FROM Notification WHERE billId = :billId")
    List<Notification> getNotificationsByBillId(String billId);

    @Query("SELECT COUNT(*) FROM Notification WHERE billId = :billId AND seen = :seen")
    int getUnseenNotificationNumber(String billId, boolean seen);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNotification(Notification notification);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNotifications(ArrayList<Notification> notifications);

    @Query("DELETE FROM Notification")
    void deleteAllNotifications();

    @Query("DELETE FROM Notification WHERE id = :id")
    void deleteNotificationById(int id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNotification(Notification notification);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNotifications(List<Notification> notifications);

    @Query("UPDATE Notification set seen = :seen WHERE customId = :id")
    void updateOnOffLoadSeen(int id, boolean seen);
}
