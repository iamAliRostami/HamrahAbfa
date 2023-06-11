package com.leon.hamrah_abfa.fragments.checkout;

public class CheckoutBillViewModel {
    public String dateStart;
    public String dateEnd;
    public String usage;
    public String price;

    public CheckoutBillViewModel(String dateStart, String dateEnd, String usage, String price) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.usage = usage;
        this.price = price;
    }
}
