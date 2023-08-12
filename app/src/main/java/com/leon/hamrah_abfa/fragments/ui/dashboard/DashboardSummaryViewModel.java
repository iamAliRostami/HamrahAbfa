package com.leon.hamrah_abfa.fragments.ui.dashboard;

public class DashboardSummaryViewModel {
    private String id;
    private String zoneId;
    private float amount;
    private String day;
    private int duration;
    private float usage;
    private int rate;
    private float usageType;

    public String getId() {
        return id;
    }

    public String getZoneId() {
        return zoneId;
    }

    public float getAmount() {
        return amount;
    }

    public String getDay() {
        return day;
    }

    public int getDuration() {
        return duration;
    }

    public float getUsage() {
        return usage;
    }

    public int getRate() {
        return rate;
    }

    public float getUsageType() {
        return usageType;
    }
}