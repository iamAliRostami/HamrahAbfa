package com.leon.hamrah_abfa.fragments.checkout;

public class CheckoutPaymentViewModel {
    public String date;
    public String price;
    public String paymentType;
    public String bank;
    public String submitType;

    public CheckoutPaymentViewModel(String date, String price, String paymentType, String bank, String submitType) {
        this.date = date;
        this.price = price;
        this.paymentType = paymentType;
        this.bank = bank;
        this.submitType = submitType;
    }
}
