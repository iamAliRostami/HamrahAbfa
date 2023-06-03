package com.leon.hamrah_abfa.di.view_model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class LastBillViewModel extends BaseObservable {

    private String username;
    private String nickname;
    private String billId;
    private String price;
    private String deadline;

    private String usingM3;
    private String usingLiter;
    private String average;

    private String bahaAb;
    private String bahaFazelab;
    private String tax;
    private String taklif;
    private String kahande;
    private String total;
    private String offPrice;
    private String debt;


    private String radif;
    private String barge;
    private String eshterak;
    private String preDate;
    private String currentDate;
    private String days;
    private String preNumber;
    private String currentNumber;

    private String karbari;
    private String ahahdMaskooni;
    private String ahadNonMaskooni;
    private String zarfiat;
    private String qotrEnsheab;
    private String qotrSiphon;

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
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
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
        notifyPropertyChanged(BR.deadline);
    }

    @Bindable
    public String getUsingM3() {
        return usingM3;
    }

    public void setUsingM3(String usingM3) {
        this.usingM3 = usingM3;
        notifyPropertyChanged(BR.usingM3);
    }

    @Bindable
    public String getUsingLiter() {
        return usingLiter;
    }

    public void setUsingLiter(String usingLiter) {
        this.usingLiter = usingLiter;
        notifyPropertyChanged(BR.usingLiter);
    }

    @Bindable
    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
        notifyPropertyChanged(BR.average);
    }

    @Bindable
    public String getBahaAb() {
        return bahaAb;
    }

    public void setBahaAb(String bahaAb) {
        this.bahaAb = bahaAb;
        notifyPropertyChanged(BR.bahaAb);
    }

    @Bindable
    public String getBahaFazelab() {
        return bahaFazelab;
    }

    public void setBahaFazelab(String bahaFazelab) {
        this.bahaFazelab = bahaFazelab;
        notifyPropertyChanged(BR.bahaFazelab);
    }

    @Bindable
    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
        notifyPropertyChanged(BR.tax);
    }

    @Bindable
    public String getTaklif() {
        return taklif;
    }

    public void setTaklif(String taklif) {
        this.taklif = taklif;
        notifyPropertyChanged(BR.taklif);
    }

    @Bindable
    public String getKahande() {
        return kahande;
    }

    public void setKahande(String kahande) {
        this.kahande = kahande;
        notifyPropertyChanged(BR.kahande);
    }

    @Bindable
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
        notifyPropertyChanged(BR.total);
    }

    @Bindable
    public String getOffPrice() {
        return offPrice;
    }

    public void setOffPrice(String offPrice) {
        this.offPrice = offPrice;
        notifyPropertyChanged(BR.offPrice);
    }

    @Bindable
    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
        notifyPropertyChanged(BR.debt);
    }

    @Bindable
    public String getRadif() {
        return radif;
    }

    public void setRadif(String radif) {
        this.radif = radif;
        notifyPropertyChanged(BR.radif);
    }

    @Bindable
    public String getBarge() {
        return barge;
    }

    public void setBarge(String barge) {
        this.barge = barge;
        notifyPropertyChanged(BR.barge);
    }

    @Bindable
    public String getEshterak() {
        return eshterak;
    }

    public void setEshterak(String eshterak) {
        this.eshterak = eshterak;
        notifyPropertyChanged(BR.eshterak);
    }

    @Bindable
    public String getPreDate() {
        return preDate;
    }

    public void setPreDate(String preDate) {
        this.preDate = preDate;
        notifyPropertyChanged(BR.preDate);
    }

    @Bindable
    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
        notifyPropertyChanged(BR.currentDate);
    }

    @Bindable
    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
        notifyPropertyChanged(BR.days);
    }

    @Bindable
    public String getPreNumber() {
        return preNumber;
    }

    public void setPreNumber(String preNumber) {
        this.preNumber = preNumber;
        notifyPropertyChanged(BR.preNumber);
    }

    @Bindable
    public String getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(String currentNumber) {
        this.currentNumber = currentNumber;
        notifyPropertyChanged(BR.currentNumber);
    }

    @Bindable
    public String getKarbari() {
        return karbari;
    }

    public void setKarbari(String karbari) {
        this.karbari = karbari;
        notifyPropertyChanged(BR.karbari);
    }

    @Bindable
    public String getAhahdMaskooni() {
        return ahahdMaskooni;
    }

    public void setAhahdMaskooni(String ahahdMaskooni) {
        this.ahahdMaskooni = ahahdMaskooni;
        notifyPropertyChanged(BR.ahahdMaskooni);
    }

    @Bindable
    public String getAhadNonMaskooni() {
        return ahadNonMaskooni;
    }

    public void setAhadNonMaskooni(String ahadNonMaskooni) {
        this.ahadNonMaskooni = ahadNonMaskooni;
        notifyPropertyChanged(BR.ahadNonMaskooni);
    }

    @Bindable
    public String getZarfiat() {
        return zarfiat;
    }

    public void setZarfiat(String zarfiat) {
        this.zarfiat = zarfiat;
        notifyPropertyChanged(BR.zarfiat);
    }

    @Bindable
    public String getQotrEnsheab() {
        return qotrEnsheab;
    }

    public void setQotrEnsheab(String qotrEnsheab) {
        this.qotrEnsheab = qotrEnsheab;
        notifyPropertyChanged(BR.qotrEnsheab);
    }

    @Bindable
    public String getQotrSiphon() {
        return qotrSiphon;
    }

    public void setQotrSiphon(String qotrSiphon) {
        this.qotrSiphon = qotrSiphon;
        notifyPropertyChanged(BR.qotrSiphon);
    }
}
