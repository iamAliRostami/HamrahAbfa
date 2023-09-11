package com.app.leon.moshtarak.helpers;

import org.osmdroid.util.GeoPoint;

import java.util.regex.Pattern;

public class Constants {
    public static final String DB_NAME = "Database_6";
    public static final String FONT_NAME = "fonts/iranian_sans_b.ttf";
    public static final String HOST_P_CHECK_CONNECTION = "https://iran.ir";
    public static final String HOST_CHECK_CONNECTION = "iran.ir";
    public static final int CHECK_INTERVAL = 24 * 60 * 60 * 1000;
    public static final int DELAY = 60 * 60 * 1000;
    public static final int HOUR_OF_DAY = 8;
    public static final int MINUTE = 0;
    public static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    public static final long MIN_TIME_BW_UPDATES = 100;
    public static final int TOAST_TEXT_SIZE = 15;
    public static final int SUBMIT_PHONE_FRAGMENT = 0;
    public static final int VERIFICATION_FRAGMENT = 1;
    public static final int INCIDENT_BASE_FRAGMENT = 2;
    public static final int INCIDENT_COMPLETE_FRAGMENT = 3;
    public static final int SERVICE_FORM_FRAGMENT = 4;
    public static final int SERVICE_INTRODUCTION_FRAGMENT = 5;
    public static final int SERVICE_SUBMIT_INFORMATION_FRAGMENT = 6;
    public static final int SERVICE_LOCATION_FRAGMENT = 7;
    public static final int SERVICE_PERSONAL_INFORMATION_FRAGMENT = 8;
    public static final int COUNTER_BASE_FRAGMENT = 7;
    public static final int COUNTER_VERIFICATION_CODE_FRAGMENT = 8;
    public static final int CHANGE_MOBILE_BASE_FRAGMENT = 9;
    public static final int CHANGE_MOBILE_VERIFICATION_CODE_FRAGMENT = 10;
    public static final int CONTACT_BASE_FRAGMENT = 11;
    public static final int CONTACT_SUGGESTION_FRAGMENT = 12;
    public static final int CONTACT_COMPLAINT_FRAGMENT = 13;
    public static final int CONTACT_FAQ_FRAGMENT = 14;
    public static final int CONTACT_BRANCH_FRAGMENT = 15;
    public static final int CONTACT_FORBIDDEN_FRAGMENT = 16;
    public static final int CONTACT_DEVELOPER_FRAGMENT = 16;
    public static final int CONTACT_PHONEBOOK_FRAGMENT = 17;
    public static final int CONTACT_FORBIDDEN_COMPLETE_FRAGMENT = 18;
    public static final int CONTACT_FORBIDDEN_DESCRIPTION_FRAGMENT = 21;
    public static final int PAY_BILL_BASE_FRAGMENT = 19;
    public static final int CITIZEN_LIST_FRAGMENT = 20;
    public static final int CITIZEN_BASE_FRAGMENT = 24;
    public static final int CITIZEN_ACCOUNT_FRAGMENT = 25;
    public static final int CITIZEN_COMPLETE_FRAGMENT = 26;
    public static final int MAX_IMAGE_SIZE = 100000;
    public static final int THEME_DARK = -1;
    public static final int THEME_DEFAULT = 0;
    public static final int THEME_LIGHT = 1;
    public static final int SMALL = -1;
    public static final int MEDIUM = 0;
    public static final int LARGE = 1;
    public static final int HUGE = 2;
    public static final GeoPoint POINT = new GeoPoint(32.65836947131902, 51.667315643280745);
    public static final Pattern MOBILE_REGEX = Pattern.compile("^((\\+98|0)9\\d{9})$");
}