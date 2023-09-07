package com.app.leon.moshtarak.fragments.ui.help;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.leon.moshtarak.BR;

public class HelpViewModel extends BaseObservable {
    private int position;
    private int animSrc;
    private String title;
    private String content;

    @Bindable
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
        notifyPropertyChanged(BR.position);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    @Bindable
    public int getAnimSrc() {
        return animSrc;
    }

    public void setAnimSrc(int animSrc) {
        this.animSrc = animSrc;
        notifyPropertyChanged(BR.animSrc);
    }
}