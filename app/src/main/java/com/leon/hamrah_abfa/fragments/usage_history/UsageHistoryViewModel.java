package com.leon.hamrah_abfa.fragments.usage_history;

public class UsageHistoryViewModel {
    public String billId;
    public String date;
    public String number;
    public String statusMessage;

    public boolean status;

    public UsageHistoryViewModel(String billId, String date, String number, String statusMessage, boolean status) {
        this.billId = billId;
        this.date = date;
        this.number = number;
        this.statusMessage = statusMessage;
        this.status = status;
    }
}
