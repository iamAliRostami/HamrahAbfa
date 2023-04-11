package com.leon.hamrah_abfa.fragments.ui.cards;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class CardViewModel extends BaseObservable {
    private String owner;
    private String nickname;
    private String billId;
    private String debtString;
    private int debt;

    public CardViewModel(String owner, String nickname, String billId, int debt) {
        setOwner(owner);
        setNickname(nickname);
        setBillId(billId);
        setDebt(debt);
        setDebtString(String.valueOf(getDebt()));
    }

    @Bindable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
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
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
        notifyPropertyChanged(BR.owner);
    }

    @Bindable
    public String getDebtString() {
        return debtString;
    }

    public void setDebtString(String debtString) {
        this.debtString = debtString;
        notifyPropertyChanged(BR.debtString);
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }
}
