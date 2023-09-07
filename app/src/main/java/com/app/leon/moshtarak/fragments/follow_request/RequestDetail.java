package com.app.leon.moshtarak.fragments.follow_request;

public class RequestDetail {
    public final int level;
    public final String flowTitle;
    public final String jalaliDay;
    public int flowState;
    public String id;

    public RequestDetail(int level, String flowTitle, String jalaliDay) {
        this.level = level;
        this.flowTitle = flowTitle;
        this.jalaliDay = jalaliDay;
    }
}
