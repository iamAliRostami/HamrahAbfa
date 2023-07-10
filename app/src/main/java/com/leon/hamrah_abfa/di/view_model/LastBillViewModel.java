package com.leon.hamrah_abfa.di.view_model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leon.hamrah_abfa.BR;

public class LastBillViewModel extends BaseObservable {
    private String id;
    private int zoneId;
    private String zoneTitle;
    private String fullName;
    private int karbariId;
    private String karbariTitle;
    private String ahadMaskooni;
    private String ahadNonMaskooni;
    private String zarfiatQarardadi;
    private int qotrId;
    private String qotr;
    private int qotrSifoonId;
    private String qotrSifoon;
    private int counterStateId;
    private String radif;
    private String eshterak;
    private String barge;
    private String preCounterReadingDate;
    private String currentCounterReadingDate;
    private String preCounterNumber;
    private String currentCounterNumber;
    private String masraf;
    private String masrafLiter;
    private String masrafAverage;
    private String abBaha;
    private String karmozdFazelab;
    private String maliat;
    private String budget;
    private String lavazemKahande;
    private String jam;
    private String taxfif;
    private String preBedOrBes;
    private String payable;
    private String deadLine;
    private String days;
    private String billId;
    private int payId;
    private String barCode;
    private boolean isPayed;
    private String payDate;
    private int payBankId;
    private String payBank;
    private int payTypeId;
    private String payType;
    private String address;
    private String counterState;
    private String payablePersian;
    private String abonmanAb;
    private String abonmanFa;
    private String tabsare3;
    private String jalaliDate;
    private String empty;
    private boolean isFree;

    public String getId() {
        return id;
    }

    public int getZoneId() {
        return zoneId;
    }

    @Bindable
    public String getZoneTitle() {
        return zoneTitle;
    }

    @Bindable
    public String getFullName() {
        return fullName;
    }

    public int getKarbariId() {
        return karbariId;
    }

    @Bindable
    public String getKarbariTitle() {
        return karbariTitle;
    }

    @Bindable
    public String getAhadMaskooni() {
        return ahadMaskooni;
    }

    @Bindable
    public String getZarfiatQarardadi() {
        return zarfiatQarardadi;
    }

    public int getQotrId() {
        return qotrId;
    }

    @Bindable
    public String getQotr() {
        return qotr;
    }

    public int getQotrSifoonId() {
        return qotrSifoonId;
    }

    @Bindable
    public String getQotrSifoon() {
        return qotrSifoon;
    }

    public int getCounterStateId() {
        return counterStateId;
    }

    @Bindable
    public String getPreCounterReadingDate() {
        return preCounterReadingDate;
    }

    @Bindable
    public String getCurrentCounterReadingDate() {
        return currentCounterReadingDate;
    }

    @Bindable
    public String getPreCounterNumber() {
        return preCounterNumber;
    }

    @Bindable
    public String getCurrentCounterNumber() {
        return currentCounterNumber;
    }

    @Bindable
    public String getMasraf() {
        return masraf;
    }

    @Bindable
    public String getMasrafLiter() {
        return masrafLiter;
    }

    @Bindable
    public String getMasrafAverage() {
        return masrafAverage;
    }

    @Bindable
    public String getAbBaha() {
        return abBaha;
    }

    @Bindable
    public String getKarmozdFazelab() {
        return karmozdFazelab;
    }

    @Bindable
    public String getMaliat() {
        return maliat;
    }

    @Bindable
    public String getBudget() {
        return budget;
    }

    @Bindable
    public String getLavazemKahande() {
        return lavazemKahande;
    }

    @Bindable
    public String getJam() {
        return jam;
    }

    @Bindable
    public String getTaxfif() {
        return taxfif;
    }

    @Bindable
    public String getPreBedOrBes() {
        return preBedOrBes;
    }

    @Bindable
    public String getPayable() {
        return payable;
    }

    @Bindable
    public String getDeadLine() {
        return deadLine;
    }

    public int getPayId() {
        return payId;
    }

    public String getBarCode() {
        return barCode;
    }

    public boolean getIsPayed() {
        return isPayed;
    }

    @Bindable
    public String getPayDate() {
        return payDate;
    }

    public int getPayBankId() {
        return payBankId;
    }

    @Bindable
    public String getPayBank() {
        return payBank;
    }

    public int getPayTypeId() {
        return payTypeId;
    }

    @Bindable
    public String getPayType() {
        return payType;
    }

    public String getAddress() {
        return address;
    }

    @Bindable
    public String getCounterState() {
        return counterState;
    }

    @Bindable
    public String getPayablePersian() {
        return payablePersian;
    }

    @Bindable
    public String getAbonmanAb() {
        return abonmanAb;
    }

    @Bindable
    public String getAbonmanFa() {
        return abonmanFa;
    }

    @Bindable
    public String getTabsare3() {
        return tabsare3;
    }

    @Bindable
    public String getJalaliDate() {
        return jalaliDate;
    }

    @Bindable
    public String getEmpty() {
        return empty;
    }

    public boolean getIsFree() {
        return isFree;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public void setZoneTitle(String zoneTitle) {
        this.zoneTitle = zoneTitle;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setKarbariId(int karbariId) {
        this.karbariId = karbariId;
    }

    public void setKarbariTitle(String karbariTitle) {
        this.karbariTitle = karbariTitle;
    }

    public void setAhadMaskooni(String ahadMaskooni) {
        this.ahadMaskooni = ahadMaskooni;
        notifyPropertyChanged(BR.ahadMaskooni);
    }

    public void setZarfiatQarardadi(String zarfiatQarardadi) {
        this.zarfiatQarardadi = zarfiatQarardadi;
        notifyPropertyChanged(BR.zarfiatQarardadi);
    }

    public void setQotrId(int qotrId) {
        this.qotrId = qotrId;
    }

    public void setQotr(String qotr) {
        this.qotr = qotr;
    }

    public void setQotrSifoonId(int qotrSifoonId) {
        this.qotrSifoonId = qotrSifoonId;
    }

    public void setQotrSifoon(String qotrSifoon) {
        this.qotrSifoon = qotrSifoon;
    }

    public void setCounterStateId(int counterStateId) {
        this.counterStateId = counterStateId;
    }

    public void setPreCounterReadingDate(String preCounterReadingDate) {
        this.preCounterReadingDate = preCounterReadingDate;
    }

    public void setCurrentCounterReadingDate(String currentCounterReadingDate) {
        this.currentCounterReadingDate = currentCounterReadingDate;
    }

    public void setPreCounterNumber(String preCounterNumber) {
        this.preCounterNumber = preCounterNumber;
    }

    public void setCurrentCounterNumber(String currentCounterNumber) {
        this.currentCounterNumber = currentCounterNumber;
        notifyPropertyChanged(BR.currentCounterNumber);
    }

    public void setMasraf(String masraf) {
        this.masraf = masraf;
    }

    public void setMasrafLiter(String masrafLiter) {
        this.masrafLiter = masrafLiter;
        notifyPropertyChanged(BR.masrafLiter);
    }

    public void setMasrafAverage(String masrafAverage) {
        this.masrafAverage = masrafAverage;
        notifyPropertyChanged(BR.masrafAverage);
    }

    public void setAbBaha(String abBaha) {
        this.abBaha = abBaha;
        notifyPropertyChanged(BR.abBaha);
    }

    public void setKarmozdFazelab(String karmozdFazelab) {
        this.karmozdFazelab = karmozdFazelab;
        notifyPropertyChanged(BR.karmozdFazelab);
    }

    public void setMaliat(String maliat) {
        this.maliat = maliat;
        notifyPropertyChanged(BR.maliat);
    }

    public void setBudget(String budget) {
        this.budget = budget;
        notifyPropertyChanged(BR.budget);
    }

    public void setLavazemKahande(String lavazemKahande) {
        this.lavazemKahande = lavazemKahande;
        notifyPropertyChanged(BR.lavazemKahande);
    }

    public void setJam(String jam) {
        this.jam = jam;
        notifyPropertyChanged(BR.jam);
    }

    public void setTaxfif(String taxfif) {
        this.taxfif = taxfif;
        notifyPropertyChanged(BR.taxfif);
    }

    public void setPreBedOrBes(String preBedOrBes) {
        this.preBedOrBes = preBedOrBes;
        notifyPropertyChanged(BR.preBedOrBes);
    }

    public void setPayable(String payable) {
        this.payable = payable;
        notifyPropertyChanged(BR.payable);
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
        notifyPropertyChanged(BR.deadLine);
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setIsPayed(boolean isPayed) {
        this.isPayed = isPayed;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
        notifyPropertyChanged(BR.payDate);
    }

    public void setPayBankId(int payBankId) {
        this.payBankId = payBankId;
    }

    public void setPayBank(String payBank) {
        this.payBank = payBank;
        notifyPropertyChanged(BR.payBank);
    }

    public void setPayTypeId(int payTypeId) {
        this.payTypeId = payTypeId;
    }

    public void setPayType(String payType) {
        this.payType = payType;
        notifyPropertyChanged(BR.payType);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCounterState(String counterState) {
        this.counterState = counterState;
    }

    public void setPayablePersian(String payablePersian) {
        this.payablePersian = payablePersian;
    }

    public void setAbonmanAb(String abonmanAb) {
        this.abonmanAb = abonmanAb;
        notifyPropertyChanged(BR.abonmanAb);
    }

    public void setAbonmanFa(String abonmanFa) {
        this.abonmanFa = abonmanFa;
        notifyPropertyChanged(BR.abonmanFa);
    }

    public void setTabsare3(String tabsare3) {
        this.tabsare3 = tabsare3;
        notifyPropertyChanged(BR.tabsare3);
    }

    public void setJalaliDate(String jalaliDate) {
        this.jalaliDate = jalaliDate;
        notifyPropertyChanged(BR.jalaliDate);
    }

    public void setEmpty(String empty) {
        this.empty = empty;
        notifyPropertyChanged(BR.empty);
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
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
    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
        notifyPropertyChanged(BR.days);
    }

    @Bindable
    public String getAhadNonMaskooni() {
        return ahadNonMaskooni;
    }

    public void setAhadNonMaskooni(String ahadNonMaskooni) {
        this.ahadNonMaskooni = ahadNonMaskooni;
        notifyPropertyChanged(BR.ahadNonMaskooni);
    }
}