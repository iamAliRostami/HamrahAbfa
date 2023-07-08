package com.leon.hamrah_abfa.fragments.ui.cards;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class BillCardViewModel extends BaseObservable {
    private String id;
    private String billId;
    private String alias;
    private long debt;
    private String debtString;

    public int status;
    public String message;
    public String generationDateTime;
    public boolean isValid;


    public BillCardViewModel(String alias, String billId, int debt) {
        setAlias(alias);
        setBillId(billId);
        setDebt(debt);
        setDebtString(String.valueOf(getDebt()));
    }

    public BillCardViewModel() {
    }

    @Bindable
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
        notifyPropertyChanged(BR.alias);
    }

    @Bindable
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
        notifyPropertyChanged(BR.billId);
    }

    @Bindable
    public String getDebtString() {
        return debtString;
    }

    public void setDebtString(String debtString) {
        this.debtString = debtString;
        notifyPropertyChanged(BR.debtString);
    }

    public long getDebt() {
        return debt;
    }

    public void setDebt(long debt) {
        this.debt = debt;
    }
}
