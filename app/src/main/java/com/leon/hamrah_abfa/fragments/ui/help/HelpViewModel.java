package com.leon.hamrah_abfa.fragments.ui.help;

import android.graphics.drawable.Drawable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class HelpViewModel extends BaseObservable {
    private int position;
    private int bgColor;
    private int logo;
    private Drawable logoDrawable;
    private String title;
    private String content;

    public HelpViewModel() {
    }

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
    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        notifyPropertyChanged(BR.bgColor);
    }

    @Bindable
    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
        notifyPropertyChanged(BR.logo);
    }

    @Bindable
    public Drawable getLogoDrawable() {
        return logoDrawable;
    }

    public void setLogoDrawable(Drawable logoDrawable) {
        this.logoDrawable = logoDrawable;
        notifyPropertyChanged(BR.logoDrawable);
    }
}