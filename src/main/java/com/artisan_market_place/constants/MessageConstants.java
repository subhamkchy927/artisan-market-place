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
    public static final String BANK_ACCOUNT_OBJECT_NULL = "Bank account object cannot be null.";
    public static final String ACCOUNT_HOLDER_NAME_MANDATORY = "Account holder name is mandatory.";
    public static final String ACCOUNT_NUMBER_MANDATORY = "Account number is mandatory.";
    public static final String IFSC_CODE_MANDATORY = "IFSC code is mandatory.";
    public static final String BANK_NAME_MANDATORY = "Bank name is mandatory.";
    public static final String BRANCH_NAME_MANDATORY = "Branch name is mandatory.";
    public static final String ACCOUNT_TYPE_MANDATORY = "Account type is mandatory.";
    public static final String ACCOUNT_NUMBER_ALREADY_EXISTS = "Account number already exists.";
    public static final String INVALID_IFSC_CODE = "Invalid IFSC code. Example of a valid IFSC code: 'SBIN0001234'.";
    public static final String BANK_ACCOUNT_NOT_FOUND = "Bank account not found.";
    public static final String INVALID_ACCOUNT_NUMBER_LENGTH = "Invalid account number length. Bank account numbers typically range from 6 to 18 digits, depending on the bank.";

    public static final String CARD_NUMBER_ALREADY_EXISTS = "Card number already exists.";
    public static final String CARD_OBJECT_NULL = "Card object cannot be null.";
    public static final String CARD_HOLDER_NAME_MANDATORY = "Cardholder name is mandatory.";
    public static final String CARD_NUMBER_MANDATORY = "Card number is mandatory.";
    public static final String EXPIRY_DATE_MANDATORY = "Expiry date is mandatory.";
    public static final String CVV_MANDATORY = "CVV is mandatory.";
    public static final String CARD_TYPE_MANDATORY = "Card type is mandatory.";
    public static final String CARD_NOT_FOUND = "Card not found.";
    public static final String INVALID_CVV_LENGTH = "Invalid CVV length. CVV must be 3 digits for Visa, MasterCard, Discover, JCB, and RuPay, and 4 digits for American Express.";
    public static final String INVALID_CARD_NUMBER_LENGTH = "Invalid card number length. Card numbers must be 16 digits for Visa, MasterCard, Discover, JCB, and RuPay, and 15 digits for American Express.";
}
