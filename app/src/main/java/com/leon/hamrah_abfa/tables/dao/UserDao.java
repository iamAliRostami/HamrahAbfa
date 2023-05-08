package com.leon.hamrah_abfa.tables.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.leon.hamrah_abfa.tables.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getAllUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<User> users);

    @Query("DELETE FROM User")
    void deleteAllUsers();

    @Query("DELETE FROM User WHERE id = :id")
    void deleteUserById(int id);
}
