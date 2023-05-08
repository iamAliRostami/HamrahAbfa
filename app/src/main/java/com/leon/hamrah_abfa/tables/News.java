package com.leon.hamrah_abfa.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//@Entity(tableName = "News", indices = {@Index(value = {"customId", "id"}, unique = true)})
@Entity(tableName = "News", indices = {@Index(value = {"customId"}, unique = true),
        @Index(value = "id", unique = true)})
public class News {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "customId")
    public int customId;
    @ColumnInfo(name = "id")
    public int id;
    public int category;
    public String summary;
    public String title;
    public String text;
    public String date;
    public boolean seen;

    public News(int id, String title, String summary, String text, String date, int category) {
        this.id = id;
        this.summary = summary;
        this.title = title;
        this.text = text;
        this.date = date;
        this.category = category;
    }
}
