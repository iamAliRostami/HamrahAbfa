package com.app.leon.moshtarak.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "News", indices = {@Index(value = {"customId"}, unique = true),
        @Index(value = {"id", "billId"}, unique = true)})
public class News {
    @ColumnInfo(name = "id")
    public final int id;
    public final int category;
    public final String summary;
    public final String title;
    public final String text;
    @ColumnInfo(name = "billId")
    @NonNull
    public final String billId;
    public final String date;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "customId")
    public int customId;
    public String imageAddress;
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

    public News(int id, @NonNull String billId, String title, String summary, String text, String date, int category, String imageAddress) {
        this.id = id;
        this.billId = billId;
        this.summary = summary;
        this.title = title;
        this.text = text;
        this.date = date;
        this.category = category;
        this.imageAddress = imageAddress;
    }
}
