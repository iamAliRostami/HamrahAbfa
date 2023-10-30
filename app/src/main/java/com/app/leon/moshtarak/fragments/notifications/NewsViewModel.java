package com.app.leon.moshtarak.fragments.notifications;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.leon.moshtarak.BR;

public class NewsViewModel extends BaseObservable {
    private String dayJalali;
    private String title;
    private String description;
    private String link;
    private int type;

    public String getDayJalali() {
        return dayJalali;
    }

    public String getLink() {
        return link;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}