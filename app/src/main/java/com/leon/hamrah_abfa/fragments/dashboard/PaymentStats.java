package com.leon.hamrah_abfa.fragments.dashboard;

import java.util.ArrayList;

public class PaymentStats {
    public String firstBillDate;
    public float totalBills;
    public float totalPayments;
    public float unpayedBills;
    public float payAverageTime;
    public final ArrayList<Integer> payDeadlineValues = new ArrayList<>();
    public final ArrayList<String> payDeadlineKeys = new ArrayList<>();
}
