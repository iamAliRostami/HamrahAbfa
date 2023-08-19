package com.leon.hamrah_abfa.fragments.follow_request;

public class RequestDetail {
    public final int level;
    public int flowState;
    public final String flowTitle;
    public final String jalaliDay;
    public String id;

    public RequestDetail(int level, String flowTitle, String jalaliDay) {
        this.level = level;
        this.flowTitle = flowTitle;
        this.jalaliDay = jalaliDay;
    }
}
