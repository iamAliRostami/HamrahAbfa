package com.leon.hamrah_abfa.fragments.notifications;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.leon.hamrah_abfa.BR;

public class NotificationsViewModel extends BaseObservable {
    private String id;
    private boolean isNews;
    private String imageAddress;
    private String insertDateTime;
    private String billId;
    private String message;
    private String title;
    private String seenDateTime;
    private float trackNumber;
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNews() {
        return isNews;
    }

    public void setNews(boolean news) {
        isNews = news;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getInsertDateTime() {
        return insertDateTime;
    }

    public void setInsertDateTime(String insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(BR.message);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public String getSeenDateTime() {
        return seenDateTime;
    }

    public void setSeenDateTime(String seenDateTime) {
        this.seenDateTime = seenDateTime;
    }

    public float getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(float trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}