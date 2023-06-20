package com.leon.hamrah_abfa.fragments.usage_history;

public class UsageHistoryViewModel {
    public String billId;
    public String date;
    public String number;
    public String statusMessage;
    public String price;

    public boolean status;

    public UsageHistoryViewModel(String billId, String price, String date, String number, String statusMessage, boolean status) {
        this.billId = billId;
        this.date = date;
        this.number = number;
        this.statusMessage = statusMessage;
        this.status = status;
        this.price = price;
    }

    public UsageHistoryViewModel(String date, String number, String statusMessage, boolean status) {
        this.date = date;
        this.number = number;
        this.statusMessage = statusMessage;
        this.status = status;
    }

    public UsageHistoryViewModel(String date, String number,String price) {
        this.date = date;
        this.number = number;
        this.price = price;
    }
}
