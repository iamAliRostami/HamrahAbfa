package com.leon.hamrah_abfa.fragments.pay_bill;

public class PayBillViewModel {
    public final String nickName;
    public final String debt;
    public final String deadline;
    public final String billId;
    public boolean selected;
    public boolean isPayed;

    public PayBillViewModel(String nickName, String debt, String deadline, String billId,boolean isPayed) {
        this.nickName = nickName;
        this.debt = debt;
        this.deadline = deadline;
        this.billId = billId;
        this.isPayed = isPayed;
    }
}
