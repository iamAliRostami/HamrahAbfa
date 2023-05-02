package com.leon.hamrah_abfa.helpers;

import org.osmdroid.util.GeoPoint;

import java.util.regex.Pattern;

public class Constants {
    public static final String DBName = "MyDatabase_1";
    public static final String FONT_NAME = "fonts/font_1.ttf";
    public static final int TOAST_TEXT_SIZE = 15;
    public static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    public static final long MIN_TIME_BW_UPDATES = 100;
    public static final int SUBMIT_PHONE_FRAGMENT = 0;
    public static final int VERIFICATION_FRAGMENT = 1;
    public static final int MAX_IMAGE_SIZE = 100000;
    public static final GeoPoint POINT = new GeoPoint(32.65836947131902, 51.667315643280745);
    public static final Pattern MOBILE_REGEX = Pattern.compile("^((\\+98|0)9\\d{9})$");
    public static final int THEME_DARK = -1;
    public static final int THEME_DEFAULT = 0;
    public static final int THEME_LIGHT = 1;
    public static final int SMALL = -1;
    public static final int MEDIUM = 0;
    public static final int LARGE = 1;
    public static final int HUGE = 2;
}