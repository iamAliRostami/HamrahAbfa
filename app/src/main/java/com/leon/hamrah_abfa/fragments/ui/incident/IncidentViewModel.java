package com.leon.hamrah_abfa.fragments.ui.incident;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IncidentViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public IncidentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is incident fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}