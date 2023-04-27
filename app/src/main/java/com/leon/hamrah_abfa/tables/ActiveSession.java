package com.leon.hamrah_abfa.tables;

public class ActiveSession {
    private String deviceName;
    private String mobile;
    private String lastLogin;
    private String ip;


    public ActiveSession(String deviceName, String mobile, String lastLogin, String ip) {
        this.deviceName = deviceName;
        this.mobile = mobile;
        this.lastLogin = lastLogin;
        this.ip = ip;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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

}
