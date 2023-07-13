package com.leon.hamrah_abfa.fragments.follow_request;

public class RequestLevel {
    public int id;
    public int level;
    public int flowState;
    public String flowTitle;
    public String jalaliDay;

    public RequestLevel(int level, String flowTitle, String jalaliDay) {
        this.level = level;
        this.flowTitle = flowTitle;
        this.jalaliDay = jalaliDay;
    }
}
