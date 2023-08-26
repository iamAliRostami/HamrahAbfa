package com.leon.hamrah_abfa.fragments.pay_bill;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.IS_PAYED;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

public class PayBillViewModel {
    public final String nickName;
    public final String debt;
    public final String deadline;
    public final String billId;
    public boolean selected;
    public boolean isPayed;

    public PayBillViewModel(String nickName, String debt, String deadline, String billId) {
        this.nickName = nickName;
        this.debt = debt;
        this.deadline = deadline;
        this.billId = billId;
        this.isPayed = getInstance().getApplicationComponent().SharedPreferenceModel().getBoolData(IS_PAYED.getValue().concat(billId));
    }
}
