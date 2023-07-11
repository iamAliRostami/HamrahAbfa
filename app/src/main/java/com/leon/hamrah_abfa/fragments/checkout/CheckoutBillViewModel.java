package com.leon.hamrah_abfa.fragments.checkout;

public class CheckoutBillViewModel {
    public String amount;
    public String day;
    public String duration;
    public String usage;
    public int zoneId;
    public int usageType;

    public CheckoutBillViewModel(String day, String amount, String usage, String duration, int usageType) {
        this.amount = amount;
        this.duration = duration;
        this.day = day;
        this.usage = usage;

        this.usageType = usageType;
    }
}
