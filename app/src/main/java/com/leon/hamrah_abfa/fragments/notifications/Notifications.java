package com.leon.hamrah_abfa.fragments.notifications;

import com.leon.hamrah_abfa.tables.Notification;

import java.util.ArrayList;

public class Notifications {
    public ArrayList<NotificationsViewModel> customerNotifications = new ArrayList<>();
    public int status;
    public String message;
    public String generationDateTime;
    public boolean isValid;
}
