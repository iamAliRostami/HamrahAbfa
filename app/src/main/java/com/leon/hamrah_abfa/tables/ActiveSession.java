package com.leon.hamrah_abfa.tables;

public class ActiveSession {
    private String id;
    private String deviceInfo;
    private String ip;
    private String insertDayJalali;
    private String insertTime;

    private String mobile;
    private String lastLogin;


    public ActiveSession(String deviceInfo, String mobile, String lastLogin, String ip) {
        this.deviceInfo = deviceInfo;
        this.mobile = mobile;
        this.lastLogin = lastLogin;
        this.ip = ip;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getInsertDayJalali() {
        return insertDayJalali;
    }

    public String getInsertTime() {
        return insertTime;
    }
}
