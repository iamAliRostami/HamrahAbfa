package com.app.leon.moshtarak.fragments.pay_bill;

import com.app.leon.moshtarak.enums.SharedReferenceKeys;
import com.app.leon.moshtarak.helpers.MyApplication;

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
        this.isPayed = MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getBoolData(SharedReferenceKeys.IS_PAYED.getValue().concat(billId));
    }
}
