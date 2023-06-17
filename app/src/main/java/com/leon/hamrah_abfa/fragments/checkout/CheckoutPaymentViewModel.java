package com.leon.hamrah_abfa.fragments.checkout;

public class CheckoutPaymentViewModel {
    public final String date;
    public final String price;
    public final String paymentType;
    public final String bank;
    public final boolean submitType;

    public CheckoutPaymentViewModel(String date, String price, String paymentType, String bank, boolean submitType) {
        this.date = date;
        this.price = price;
        this.paymentType = paymentType;
        this.bank = bank;
        this.submitType = submitType;
    }
}
