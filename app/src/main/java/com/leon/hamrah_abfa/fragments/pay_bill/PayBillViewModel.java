package com.leon.hamrah_abfa.fragments.pay_bill;

public class PayBillViewModel {
    public String nickName;
    public String debt;
    public String deadline;
    public String billId;
    public boolean selected;

    public PayBillViewModel(String nickName, String debt, String deadline, String billId) {
        this.nickName = nickName;
        this.debt = debt;
        this.deadline = deadline;
        this.billId = billId;
    }
}
