package com.leon.hamrah_abfa.fragments.follow_request;

public class RequestDetail {
    public int level;
    public int flowState;
    public String flowTitle;
    public String jalaliDay;
    public String id;

    public RequestDetail(int level, String flowTitle, String jalaliDay) {
        this.level = level;
        this.flowTitle = flowTitle;
        this.jalaliDay = jalaliDay;
    }
}
