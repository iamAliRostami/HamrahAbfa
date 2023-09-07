package com.app.leon.moshtarak.fragments.contact_us;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.leon.moshtarak.BR;

import java.util.ArrayList;

import okhttp3.MultipartBody;

public class FeedbackViewModel extends BaseObservable {
    public final ArrayList<MultipartBody.Part> file = new ArrayList<>();
    private String description;
    private String solution;
    private Integer feedbackTypeId;
    private boolean inComplaint;
    private String message;
    private String generationDateTime;
    private int status;
    private boolean isValid;

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public Integer getFeedbackTypeId() {
        return feedbackTypeId;
    }

    public void setFeedbackTypeId(Integer feedbackTypeId) {
        this.feedbackTypeId = feedbackTypeId;
        notifyPropertyChanged(BR.feedbackTypeId);
    }

    @Bindable
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
        notifyPropertyChanged(BR.solution);
    }

    public boolean isInComplaint() {
        return inComplaint;
    }

    public void setInComplaint(boolean inComplaint) {
        this.inComplaint = inComplaint;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}