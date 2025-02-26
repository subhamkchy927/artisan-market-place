package com.artisan_market_place.constants;

public class MessageConstants {
    public static final String USER_OBJECT_NULL = "User object cannot be null.";
    public static final String FIRST_NAME_MANDATORY = "First name is mandatory.";
    public static final String EMAIL_MANDATORY = "Email is mandatory.";
    public static final String PHONE_NUMBER_MANDATORY = "Phone number is mandatory.";
    public static final String STATUS_MANDATORY = "User status is mandatory.";
    public static final String ROLE_MANDATORY = "User role is mandatory.";

    public static final String INVALID_LOGIN_CREDENTIAL = "Invalid Login Credential!";
    public static final String L1000 = "L1000";
    public static final String EMAIL_ALREADY_EXISTS = "User Already exist with email";
    public static final String PHONE_NUMBER_ALREADY_EXISTS = "User Already exist with this phone number";
    public static final String PASSWORD_LENGTH_MESSAGE = "Password must be at least " + ApplicationConstants.MIN_LENGTH + " characters long.";
    public static final String UPPERCASE_MESSAGE = "Password must contain at least one uppercase letter.";
    public static final String LOWERCASE_MESSAGE = "Password must contain at least one lowercase letter.";
    public static final String DIGIT_MESSAGE = "Password must contain at least one digit.";
    public static final String SPECIAL_CHARACTER_MESSAGE = "Password must contain at least one special character.";
    public static final String USER_NOT_FOUND = "Uer not found with provided user id";
    public static final String INVALID_EMAIL_FORMAT = "Invalid email. It should be alphanumeric and should contain @ symbol";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number. It should be numer and 10 digits";
    public static final String USER_BANNED = "This user is banned from the application, due to terms & condition voilation";
}
