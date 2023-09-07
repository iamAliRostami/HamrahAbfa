package com.app.leon.moshtarak.fragments.contact_us;

public class PhonebookViewModel {
    private final String title;
    private final String number;

    public PhonebookViewModel(String title, String number) {
        this.title = title;
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public String getNumber() {
        return number;
    }
}
