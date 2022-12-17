package com.leon.hamrah_abfa.tables;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "User", indices = @Index(value = "id", unique = true))
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
}
