package com.app.leon.moshtarak.fragments.dashboard;

import java.util.ArrayList;

public class PaymentStats {
    public String firstBillDate;
    public int totalBills;
    public int totalPayments;
    public int unpayedBills;
    public int payAverageTime;
    public final ArrayList<Integer> payDeadlineValues = new ArrayList<>();
    public final ArrayList<String> payDeadlineKeys = new ArrayList<>();
}
