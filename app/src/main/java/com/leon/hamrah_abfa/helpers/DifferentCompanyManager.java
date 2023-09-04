package com.leon.hamrah_abfa.helpers;

import com.leon.hamrah_abfa.BuildConfig;
import com.leon.hamrah_abfa.enums.CompaniesName;

public class DifferentCompanyManager {

    public static CompaniesName getActiveCompanyName() {
        return BuildConfig.COMPANY_NAME;
    }

    public static String getCompanyName(CompaniesName companyName) {
        switch (companyName) {
            case ZONE1:
                return "آبفا منطقه یک";
            case ZONE2:
                return "آبفا منطقه دو";
            case ZONE3:
                return "آبفا منطقه سه";
            case ZONE4:
                return "آبفا منطقه چهار";
            case ZONE5:
                return "آبفا منطقه پنج";
            case ZONE6:
                return "آبفا منطقه شش";
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
            case DEBUG:
            default:
                return "آبفا استان اصفهان";
        }
    }

    public static String getBaseUrl() {
        switch (getActiveCompanyName()) {
            case ZONE1:
                return "http://217.146.220.33:50011/";
            case ZONE2:
                return "http://212.16.75.194:8080/";
            case ZONE3:
                return "http://46.209.219.36:90/";
            case ZONE4:
                return "http://81.12.106.167:8081/";
            case ZONE5:
                return "http://80.69.252.151/";
            case ZONE6:
                return "http://85.133.190.221:4121/";
            case TSW:
                return "http://81.90.148.25/";
            case TE:
                return "http://185.120.137.254";
            case TSE:
                return "http://172.28.5.40/";
            case TW:
                return "http://217.66.195.75/";
            case KSH:
                return "http://46.225.241.211:25123/";
            case DEBUG:
            case ESF:
            default:
                return "https://37.191.92.157/";
        }
    }
}
