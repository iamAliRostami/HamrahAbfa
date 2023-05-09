package com.leon.hamrah_abfa.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//@Entity(tableName = "News", indices = {@Index(value = {"customId", "id"}, unique = true)})
//@Entity(tableName = "News", primaryKeys = {"customId", "billId"}, indices = {@Index(value = {"customId"}, unique = true),
//        @Index(value = "id", unique = true), @Index(value = "billId", unique = true)})
@Entity(tableName = "News", indices = {@Index(value = {"customId"}, unique = true),
        @Index(value = {"id", "billId"}, unique = true)})
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
    public String imageAddress;

    @ColumnInfo(name = "billId")
    @NonNull
    public String billId;
    public String date;
    public boolean seen;

    @Ignore
    public News(int id, @NonNull String billId, String title, String summary, String text, String date, int category) {
        this.id = id;
        this.billId = billId;
        this.summary = summary;
        this.title = title;
        this.text = text;
        this.date = date;
        this.category = category;
    }

    public News(int id, @NonNull String billId, String title, String summary, String text, String date, String imageAddress, int category) {
        this.id = id;
        this.billId = billId;
        this.summary = summary;
        this.title = title;
        this.text = text;
        this.date = date;
        this.imageAddress = imageAddress;
        this.category = category;
    }
}
