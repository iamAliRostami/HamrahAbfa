package com.leon.hamrah_abfa.fragments.contact_us;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class SuggestionViewModel extends BaseObservable {
    private String description;
    private String suggestionType;
    private String proposal;

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getSuggestionType() {
        return suggestionType;
    }

    public void setSuggestionType(String suggestionType) {
        this.suggestionType = suggestionType;
        notifyPropertyChanged(BR.suggestionType);
    }

    @Bindable
    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
        notifyPropertyChanged(BR.proposal);
    }
}