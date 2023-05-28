package com.leon.hamrah_abfa.fragments.contact_us;

public class PhonebookViewModel {
    private final String title;
    private final String phoneNumber;

    public PhonebookViewModel(String title, String phoneNumber) {
        this.title = title;
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
