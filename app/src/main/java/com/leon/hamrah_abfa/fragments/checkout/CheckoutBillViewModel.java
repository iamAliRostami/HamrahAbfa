package com.leon.hamrah_abfa.fragments.checkout;

public class CheckoutBillViewModel {
    public String price;
    public String days;
    public String dateEnd;
    public String usage;
    public String dateStart;
    public int usageType;

    public CheckoutBillViewModel(String dateStart, String dateEnd, String usage, String price) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.usage = usage;
        this.price = price;
    }

    public CheckoutBillViewModel(String dateStart, String dateEnd, String usage, String price,String days,int usageType) {
        this.price = price;
        this.days = days;
        this.dateEnd = dateEnd;
        this.usage = usage;
        this.usageType = usageType;
        this.dateStart = dateStart;
    }
}
