package com.leon.hamrah_abfa.fragments.checkout;

public class CheckoutPaymentViewModel {
    public final String day;
    public final String amount;
    public final String bank;
    public final String method;
    public final boolean submitType;

    public CheckoutPaymentViewModel(String day, String amount, String method, String bank, boolean submitType) {
        this.day = day;
        this.amount = amount;
        this.method = method;
        this.bank = bank;
        this.submitType = submitType;
    }
}
