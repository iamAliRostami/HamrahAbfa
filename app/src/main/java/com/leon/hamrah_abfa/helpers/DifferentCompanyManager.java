package com.leon.hamrah_abfa.helpers;

import com.leon.hamrah_abfa.enums.CompanyNames;

public class DifferentCompanyManager {

    public static String getCompanyName(CompanyNames companyName) {
        switch (companyName) {
            case ZONE1:
                return "آبقا منطقه یک";
            case ZONE2:
                return "آبفا منطقه دو";
            case ZONE3:
                return "آبفا منطقه سه";
            case ZONE4:
                return "آبفا منطقه چهار";
            case ZONE5:
                return "آبفا منطقه پنج";
            case ZONE6:
                return "آبقا منطقه شش";
            case TE:
                return "آبفا شرق";
            case TSW:
                return "آبفا جنوب غربی";
            case TSE:
                return "آبفا جنوب شرقی";
            case TW:
                return "آبفا غرب";
            case KSH:
                return "آبفا استان کرمانشاه";
            case ESF:
            default:
                return "آبفا استان اصفهان";
        }
    }
}
