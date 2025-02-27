package com.artisan_market_place.constants;

public class ApplicationConstants {
    public static final int MIN_LENGTH = 8;
    public static final String UPPERCASE_PATTERN = ".*[A-Z].*";
    public static final String LOWERCASE_PATTERN = ".*[a-z].*";
    public static final String DIGIT_PATTERN = ".*[0-9].*";
    public static final String SPECIAL_CHARACTER_PATTERN = ".*[@$!%*?&].*";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$";
    public static final String MOBILE_PATTERN = "\\d{10}";
    public static final String IFSC_PATTERN = "^[A-Z]{4}0[A-Z0-9]{6}$";
}
