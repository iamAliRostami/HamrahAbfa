package com.leon.hamrah_abfa.fragments.follow_request;

import java.util.ArrayList;

public class MasterHistory {
    public ArrayList<RequestInfo> finisheds = new ArrayList<>();
    public ArrayList<RequestInfo> unfinisheds = new ArrayList<>();

    public int status;
    public String message;
    public String generationDateTime;
    public boolean isValid;
}
