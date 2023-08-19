package com.leon.hamrah_abfa.fragments.checkout;

import java.util.ArrayList;

public class Kardex {
    public final ArrayList<CheckoutBillViewModel> bills = new ArrayList<>();
    public final ArrayList<CheckoutPaymentViewModel> payments = new ArrayList<>();

    public int status;
    public String message;
    public String generationDateTime;
    public boolean isValid;
}
