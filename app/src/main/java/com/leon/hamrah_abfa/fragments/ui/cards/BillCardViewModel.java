package com.leon.hamrah_abfa.fragments.ui.cards;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class BillCardViewModel extends BaseObservable {
    private String id;
    private String billId;
    private String alias;
    private String fullName;
    //TODO
    private long debt;
    private String debtString;
    private int status;
    private String message;
    private String generationDateTime;
    private boolean isValid;


    public BillCardViewModel(String id, String billId, String alias, String debt) {
        setId(alias);
        setBillId(billId);
        setAlias(alias);
        setDebtString(debt);
        setDebt(Long.parseLong(debt));
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGenerationDateTime() {
        return generationDateTime;
    }

    public void setGenerationDateTime(String generationDateTime) {
        this.generationDateTime = generationDateTime;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
