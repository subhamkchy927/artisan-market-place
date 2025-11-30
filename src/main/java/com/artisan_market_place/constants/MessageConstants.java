package com.artisan_market_place.constants;

public class MessageConstants {
    public static final String USER_OBJECT_NULL = "User object cannot be null.";
    public static final String FIRST_NAME_MANDATORY = "First name is mandatory.";
    public static final String EMAIL_MANDATORY = "Email is mandatory.";
    public static final String PHONE_NUMBER_MANDATORY = "Phone number is mandatory.";
    public static final String ROLE_MANDATORY = "User role is mandatory.";
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
    public static final String INVALID_IFSC_CODE = "Invalid IFSC code. it should be 11 charcaters Example of a valid IFSC code: 'SBIN0001234'.";
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
    public static final String ASSOCIATED_USER_ID_MANDATORY = "User Id is mandatory";
    public static final String ADDRESS_DATA_CANNOT_BE_NULL = "Address data cannot be null.";
    public static final String USER_ID_REQUIRED = "User ID is required.";
    public static final String ADDRESS1_REQUIRED = "Address1 is required.";
    public static final String CITY_REQUIRED = "City is required.";
    public static final String STATE_REQUIRED = "State is required.";
    public static final String COUNTRY_REQUIRED = "Country is required.";
    public static final String ZIP_CODE_REQUIRED = "Zip code is required.";
    public static final String INVALID_ZIP_CODE_FORMAT = "Invalid zip code format. Example: 123456";
    public static final String INVALID_LATITUDE = "Latitude must be between -90 and 90.";
    public static final String INVALID_LONGITUDE = "Longitude must be between -180 and 180.";
    public static final String ADDRESS_NOT_FOUND = "Address not found with provided address Id.";
    public static final String ERRROR_SENDING_EMAIL = "Error in sending email";
    public static final String VERIFICATION_OTP_CONTENT = "Dear User, your OTP for verification is: %s. Please do not share this with anyone. This OTP is valid for 10 minutes.";
    public static final String ERRROR_STORING_EMAIL_EMAIL = "Error in storing email details";
    public static final String INVALID_OTP = "Invalid/Expired Otp";
    public static final String INVALID_OLD_PASSWORD = "Invalid old password";
    public static final String PASSWORD_MISMATCH = "Entered passwrod and confirmed password mismatch";
    public static final String ERROR_SENDING_SMS = "Error sending text sms";
    public static final String COUNTRY_CODE_MANDATORY = "Country code is mandatory";
    public static final String PRODUCT_OBJECT_NULL = "Product object cannot be null.";
    public static final String SELLER_ID_MANDATORY = "Seller ID is mandatory.";
    public static final String PRODUCT_NAME_MANDATORY = "Product name is mandatory.";
    public static final String PRODUCT_CATEGORY_MANDATORY = "Product category is mandatory.";
    public static final String PRODUCT_BRAND_MANDATORY = "Product brand is mandatory.";
    public static final String PRODUCT_PRICE_INVALID = "Product price must be greater than zero.";
    public static final String PRODUCT_QUANTITY_INVALID = "Product quantity cannot be negative.";
    public static final String PRODUCT_ALREADY_EXISTS = "A product with the same values already exists.";
    public static final String ONLY_ADMIN_SELLER_CAN_ADD_UPDATE_PRODUCT = "Only Admin, Seller, or Seller-Customer can add or update a product.";
    public static final String PRODUCT_NOT_FOUND = "Product not found.";
    
    // Booking constants
    public static final String BOOKING_OBJECT_NULL = "Booking object cannot be null.";
    public static final String PRODUCT_ID_MANDATORY = "Product ID is mandatory.";
    public static final String CUSTOMER_ID_MANDATORY = "Customer ID is mandatory.";
    public static final String BOOKING_QUANTITY_INVALID = "Booking quantity must be greater than zero.";
    public static final String DELIVERY_ADDRESS_ID_MANDATORY = "Delivery address ID is mandatory.";
    public static final String INSUFFICIENT_STOCK = "Insufficient stock available for this product.";
    public static final String ONLY_CUSTOMER_CAN_BOOK = "Only customers can create bookings.";
    public static final String BOOKING_NOT_FOUND = "Booking not found.";
    public static final String CANNOT_UPDATE_COMPLETED_BOOKING = "Cannot update a completed or delivered booking.";
    public static final String CANNOT_CANCEL_COMPLETED_BOOKING = "Cannot cancel a completed or delivered booking.";
    public static final String BOOKING_ALREADY_CANCELLED = "Booking is already cancelled.";
    
    // Offer constants
    public static final String OFFER_OBJECT_NULL = "Offer object cannot be null.";
    public static final String OFFER_NAME_MANDATORY = "Offer name is mandatory.";
    public static final String OFFER_DESCRIPTION_MANDATORY = "Offer description is mandatory.";
    public static final String OFFER_START_DATE_MANDATORY = "Offer start date is mandatory.";
    public static final String OFFER_END_DATE_MANDATORY = "Offer end date is mandatory.";
    public static final String OFFER_END_DATE_BEFORE_START = "Offer end date must be after start date.";
    public static final String OFFER_NOT_FOUND = "Offer not found.";
    public static final String INVALID_DISCOUNT_AMOUNT = "Discount amount must be greater than zero.";
    public static final String INVALID_PERCENTAGE_DISCOUNT = "Percentage discount must be between 0 and 100.";
    
    // Review constants
    public static final String REVIEW_OBJECT_NULL = "Review object cannot be null.";
    public static final String REVIEW_TEXT_MANDATORY = "Review text is mandatory.";
    public static final String RATING_MANDATORY = "Rating is mandatory.";
    public static final String RATING_INVALID = "Rating must be between 1 and 5.";
    public static final String REVIEW_NOT_FOUND = "Review not found.";
    public static final String CUSTOMER_MUST_HAVE_BOOKING = "Customer must have a completed booking to review a product.";

}
