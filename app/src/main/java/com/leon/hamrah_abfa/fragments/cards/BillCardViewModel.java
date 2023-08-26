package com.leon.hamrah_abfa.fragments.cards;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.IS_PAYED;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class BillCardViewModel extends BaseObservable {
    private String id;
    private String billId;
    private String alias;
    private String oldAlias;
    private String debt;
    private String deadline;
    private boolean isPayed;
    private int status;
    private String message;
    private String generationDateTime;
    private boolean isValid;


    public BillCardViewModel(String id, String billId, String alias, String debt) {
        setId(id);
        setBillId(billId);
        setAlias(alias);
        setDebt(debt);
        setPayed(getInstance().getApplicationComponent().SharedPreferenceModel().getBoolData(IS_PAYED.getValue().concat(billId)));
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
    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
        notifyPropertyChanged(BR.debt);
    }

    @SuppressLint("DefaultLocale")
    @Bindable
    public String getDebtFormatted() {
        return String.format("%,d", Long.parseLong(getDebt()));
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public String getOldAlias() {
        return oldAlias;
    }

    public void setOldAlias(String oldAlias) {
        this.oldAlias = oldAlias;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
