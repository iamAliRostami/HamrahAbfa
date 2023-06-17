package com.leon.hamrah_abfa.fragments.checkout;

public class CheckoutBillViewModel {
    public final String price;
    public final String dateEnd;
    public final String dateStart;
    public int days;
    public final int usage;
    public int usageType;

    public CheckoutBillViewModel(String dateStart, String dateEnd, String price, int usage) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.usage = usage;
        this.price = price;
    }

    public CheckoutBillViewModel(String dateStart, String dateEnd, String price, int usage,int days,int usageType) {
        this.price = price;
        this.days = days;
        this.dateEnd = dateEnd;
        this.usage = usage;
        this.usageType = usageType;
        this.dateStart = dateStart;
    }
}
