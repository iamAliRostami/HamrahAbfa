package com.leon.hamrah_abfa.helpers;

import java.util.regex.Pattern;

public class Constants {
    public static final String DBName = "MyDatabase_1";
    public static final String FONT_NAME = "fonts/font_1.ttf";
    public static final int TOAST_TEXT_SIZE = 15;
    public static final int SPLASH_TIME_OUT = 2000;
    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    public static final long MIN_TIME_BW_UPDATES = 10000;
    public static final long FASTEST_INTERVAL = 10000;
    public static final int SUBMIT_PHONE_FRAGMENT = 0;
    public static final int VERIFICATION_FRAGMENT = 1;
    public static final Pattern MOBILE_REGEX = Pattern.compile("^((\\+98|0)9\\d{9})$");
}