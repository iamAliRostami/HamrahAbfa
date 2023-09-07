package com.app.leon.moshtarak.fragments.follow_request;

import java.util.ArrayList;

public class MasterHistory {
    public final ArrayList<RequestInfo> finisheds = new ArrayList<>();
    public final ArrayList<RequestInfo> unfinisheds = new ArrayList<>();

    public int status;
    public String message;
    public String generationDateTime;
    public boolean isValid;
}
