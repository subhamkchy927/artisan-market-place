# Artisan Marketplace - Service Documentation

This document provides comprehensive documentation for all services in the Artisan Marketplace application.

---

## Table of Contents

1. [Booking Service](#1-booking-service)
2. [Product Service](#2-product-service)
3. [Product Offer Service](#3-product-offer-service)
4. [Product Review Service](#4-product-review-service)
5. [User Service](#5-user-service)
6. [Address Service](#6-address-service)
7. [Bank Account Service](#7-bank-account-service)
8. [Credit Card Service](#8-credit-card-service)
9. [Notification Service](#9-notification-service)
10. [OTP Service](#10-otp-service)
11. [Application Details Service](#11-application-details-service)
12. [SendGrid API Service](#12-sendgrid-api-service)
13. [Twilio Service](#13-twilio-service)
14. [Auth Service](#14-auth-service)

---

## 1. Booking Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.BookingServiceImpl`

### Overview
Manages booking/order operations for products. Handles booking creation, updates, cancellations, and status management with automatic discount calculation from active offers.

### Methods

#### `createBooking(BookingRequestDto dto, String loginUser)`
- **Description:** Creates a new booking for a product. Automatically calculates discounts from active offers, updates product stock, and sets initial booking status to PENDING.
- **Parameters:**
  - `dto` (BookingRequestDto): Contains productId, customerId, quantity, deliveryAddressId, paymentMethod, notes, deliveryDate
  - `loginUser` (String): Username of the user creating the booking
- **Returns:** `BookingResponseDto` - Complete booking details with calculated prices and discount
- **Business Logic:**
  - Validates product availability and stock
  - Calculates total price (unit price × quantity)
  - Applies active offers to calculate discount
  - Updates product stock quantity
  - Sets booking status to PENDING

#### `updateBooking(BookingRequestDto dto, Long bookingId, String loginUser)`
- **Description:** Updates an existing booking. Can modify quantity, delivery address, delivery date, payment method, and notes.
- **Parameters:**
  - `dto` (BookingRequestDto): Updated booking information
  - `bookingId` (Long): ID of the booking to update
  - `loginUser` (String): Username of the user updating the booking
- **Returns:** `BookingResponseDto` - Updated booking details
- **Business Logic:**
  - Validates booking exists and is not completed/delivered
  - Updates stock if quantity changes
  - Recalculates prices if quantity is modified

#### `getBookingById(Long bookingId, String loginUser)`
- **Description:** Retrieves a specific booking by its ID.
- **Parameters:**
  - `bookingId` (Long): ID of the booking
  - `loginUser` (String): Username of the requesting user
- **Returns:** `BookingResponseDto` - Booking details with product, customer, seller names

#### `getAllBookings(String loginUser)`
- **Description:** Retrieves all bookings in the system.
- **Parameters:**
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<BookingResponseDto>` - List of all bookings

#### `getBookingsByCustomerId(Long customerId, String loginUser)`
- **Description:** Retrieves all bookings for a specific customer.
- **Parameters:**
  - `customerId` (Long): ID of the customer
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<BookingResponseDto>` - List of customer's bookings

#### `getBookingsBySellerId(Long sellerId, String loginUser)`
- **Description:** Retrieves all bookings for a specific seller.
- **Parameters:**
  - `sellerId` (Long): ID of the seller
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<BookingResponseDto>` - List of seller's bookings

#### `getBookingsByStatus(String status, String loginUser)`
- **Description:** Retrieves all bookings with a specific status.
- **Parameters:**
  - `status` (String): Booking status (PENDING, CONFIRMED, CANCELLED, COMPLETED, DELIVERED, REFUNDED)
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<BookingResponseDto>` - List of bookings with the specified status

#### `cancelBooking(Long bookingId, String loginUser)`
- **Description:** Cancels a booking and restores product stock.
- **Parameters:**
  - `bookingId` (Long): ID of the booking to cancel
  - `loginUser` (String): Username of the user canceling the booking
- **Returns:** `HashMap<String, String>` - Success message with booking ID
- **Business Logic:**
  - Validates booking can be cancelled (not completed/delivered)
  - Restores product stock quantity
  - Sets booking status to CANCELLED

#### `confirmBooking(Long bookingId, String loginUser)`
- **Description:** Confirms a pending booking and marks payment as paid.
- **Parameters:**
  - `bookingId` (Long): ID of the booking to confirm
  - `loginUser` (String): Username of the user confirming the booking
- **Returns:** `HashMap<String, String>` - Success message with booking ID
- **Business Logic:**
  - Validates booking is in PENDING status
  - Sets status to CONFIRMED
  - Sets payment status to PAID

#### `completeBooking(Long bookingId, String loginUser)`
- **Description:** Marks a booking as completed.
- **Parameters:**
  - `bookingId` (Long): ID of the booking to complete
  - `loginUser` (String): Username of the user completing the booking
- **Returns:** `HashMap<String, String>` - Success message with booking ID
- **Business Logic:**
  - Validates booking is CONFIRMED or DELIVERED
  - Sets status to COMPLETED

---

## 2. Product Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.ProductServiceImpl`

### Overview
Manages product catalog operations including creation, updates, retrieval, deletion, and search functionality.

### Methods

#### `createProduct(ProductRequestDto dto, String loginUser)`
- **Description:** Creates a new product in the catalog.
- **Parameters:**
  - `dto` (ProductRequestDto): Product details (name, description, price, quantity, category, brand, etc.)
  - `loginUser` (String): Username of the user creating the product
- **Returns:** `ProdcutResponseDto` - Created product details with images
- **Business Logic:**
  - Validates product doesn't already exist
  - Validates user has SELLER or ADMIN role
  - Creates product and associated images

#### `updateProduct(ProductRequestDto dto, Long productId, String loginUser)`
- **Description:** Updates an existing product.
- **Parameters:**
  - `dto` (ProductRequestDto): Updated product information
  - `productId` (Long): ID of the product to update
  - `loginUser` (String): Username of the user updating the product
- **Returns:** `ProdcutResponseDto` - Updated product details
- **Business Logic:**
  - Replaces existing product images with new ones

#### `getProductById(Long productId, String loginUser)`
- **Description:** Retrieves a specific product by its ID.
- **Parameters:**
  - `productId` (Long): ID of the product
  - `loginUser` (String): Username of the requesting user
- **Returns:** `ProdcutResponseDto` - Product details with images

#### `getAllProducts(String loginUser)`
- **Description:** Retrieves all products in the catalog.
- **Parameters:**
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<ProdcutResponseDto>` - List of all products

#### `deleteProduct(Long productId, String loginUser)`
- **Description:** Deletes a product and its associated images.
- **Parameters:**
  - `productId` (Long): ID of the product to delete
  - `loginUser` (String): Username of the user deleting the product
- **Returns:** `HashMap<String, String>` - Success message with product ID

#### `getProductsByCategory(Long userId, String category, String loginUser)`
- **Description:** Retrieves products filtered by seller and category.
- **Parameters:**
  - `userId` (Long): Optional seller ID filter (can be null)
  - `category` (String): Product category
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<ProdcutResponseDto>` - List of products matching the criteria

---

## 3. Product Offer Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.ProductOfferServiceImpl`

### Overview
Manages product offers and discounts. Supports percentage-based and flat discounts with date-based activation.

### Methods

#### `createOffer(ProductOfferRequestDto dto, String loginUser)`
- **Description:** Creates a new product offer with discount rules.
- **Parameters:**
  - `dto` (ProductOfferRequestDto): Offer details (name, description, discount type, dates, productId, etc.)
  - `loginUser` (String): Username of the user creating the offer
- **Returns:** `ProductOfferResponseDto` - Created offer details
- **Business Logic:**
  - Validates offer dates (end date must be after start date)
  - Validates discount amounts (percentage 0-100, flat discount > 0)
  - Sets default status to ACTIVE if not provided

#### `updateOffer(ProductOfferRequestDto dto, Long offerId, String loginUser)`
- **Description:** Updates an existing offer.
- **Parameters:**
  - `dto` (ProductOfferRequestDto): Updated offer information
  - `offerId` (Long): ID of the offer to update
  - `loginUser` (String): Username of the user updating the offer
- **Returns:** `ProductOfferResponseDto` - Updated offer details

#### `getOfferById(Long offerId, String loginUser)`
- **Description:** Retrieves a specific offer by its ID.
- **Parameters:**
  - `offerId` (Long): ID of the offer
  - `loginUser` (String): Username of the requesting user
- **Returns:** `ProductOfferResponseDto` - Offer details with product name

#### `getAllOffers(String loginUser)`
- **Description:** Retrieves all offers in the system.
- **Parameters:**
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<ProductOfferResponseDto>` - List of all offers

#### `getOffersByProductId(Long productId, String loginUser)`
- **Description:** Retrieves all offers for a specific product.
- **Parameters:**
  - `productId` (Long): ID of the product
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<ProductOfferResponseDto>` - List of product offers

#### `getActiveOffers(String loginUser)`
- **Description:** Retrieves all currently active offers (within date range and status ACTIVE).
- **Parameters:**
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<ProductOfferResponseDto>` - List of active offers
- **Business Logic:**
  - Filters offers where current date is between start_date and end_date
  - Filters by offer_status = 'ACTIVE'

#### `getActiveOffersByProductId(Long productId, String loginUser)`
- **Description:** Retrieves active offers for a specific product.
- **Parameters:**
  - `productId` (Long): ID of the product
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<ProductOfferResponseDto>` - List of active product offers

#### `deleteOffer(Long offerId, String loginUser)`
- **Description:** Deletes an offer.
- **Parameters:**
  - `offerId` (Long): ID of the offer to delete
  - `loginUser` (String): Username of the user deleting the offer
- **Returns:** `HashMap<String, String>` - Success message with offer ID

---

## 4. Product Review Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.ProductReviewServiceImpl`

### Overview
Manages product reviews and ratings. Ensures only customers with completed bookings can review products.

### Methods

#### `createReview(ProductReviewRequestDto dto, String loginUser)`
- **Description:** Creates a new product review with rating.
- **Parameters:**
  - `dto` (ProductReviewRequestDto): Review details (productId, customerId, reviews text, rating 1-5)
  - `loginUser` (String): Username of the user creating the review
- **Returns:** `ProductReviewResponseDto` - Created review with calculated averages
- **Business Logic:**
  - Validates customer has completed booking for the product
  - Validates rating is between 1 and 5
  - Calculates and updates review count and average rating
  - Updates product rating summary

#### `updateReview(ProductReviewRequestDto dto, Long reviewId, String loginUser)`
- **Description:** Updates an existing review.
- **Parameters:**
  - `dto` (ProductReviewRequestDto): Updated review information
  - `reviewId` (Long): ID of the review to update
  - `loginUser` (String): Username of the user updating the review
- **Returns:** `ProductReviewResponseDto` - Updated review details
- **Business Logic:**
  - Recalculates review counts and average rating after update

#### `getReviewById(Long reviewId, String loginUser)`
- **Description:** Retrieves a specific review by its ID.
- **Parameters:**
  - `reviewId` (Long): ID of the review
  - `loginUser` (String): Username of the requesting user
- **Returns:** `ProductReviewResponseDto` - Review details with product and customer names

#### `getAllReviews(String loginUser)`
- **Description:** Retrieves all reviews in the system.
- **Parameters:**
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<ProductReviewResponseDto>` - List of all reviews

#### `getReviewsByProductId(Long productId, String loginUser)`
- **Description:** Retrieves all reviews for a specific product, ordered by most recent.
- **Parameters:**
  - `productId` (Long): ID of the product
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<ProductReviewResponseDto>` - List of product reviews

#### `getProductRatingSummary(Long productId, String loginUser)`
- **Description:** Retrieves aggregated rating statistics for a product.
- **Parameters:**
  - `productId` (Long): ID of the product
  - `loginUser` (String): Username of the requesting user
- **Returns:** `ProductReviewResponseDto` - Contains average rating and total review count
- **Business Logic:**
  - Calculates average rating from all reviews
  - Counts total number of reviews

#### `deleteReview(Long reviewId, String loginUser)`
- **Description:** Deletes a review and updates product rating summary.
- **Parameters:**
  - `reviewId` (Long): ID of the review to delete
  - `loginUser` (String): Username of the user deleting the review
- **Returns:** `HashMap<String, String>` - Success message with review ID
- **Business Logic:**
  - Recalculates product rating after deletion

---

## 5. User Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.UserServiceImpl`

### Overview
Manages user account operations including registration, updates, authentication, and password management.

### Methods

#### `createUser(UserRequestDto dto)`
- **Description:** Creates a new user account.
- **Parameters:**
  - `dto` (UserRequestDto): User details (name, email, phone, role, etc.)
- **Returns:** `UserResponseDto` - Created user details
- **Business Logic:**
  - Validates email and phone number uniqueness
  - Creates user login credentials
  - Sets default status based on role

#### `updateUser(UserRequestDto dto, Long userId, String loginUser)`
- **Description:** Updates an existing user's information.
- **Parameters:**
  - `dto` (UserRequestDto): Updated user information
  - `userId` (Long): ID of the user to update
  - `loginUser` (String): Username of the user performing the update
- **Returns:** `UserResponseDto` - Updated user details

#### `getUserById(Long userId, String loginUser)`
- **Description:** Retrieves a specific user by their ID.
- **Parameters:**
  - `userId` (Long): ID of the user
  - `loginUser` (String): Username of the requesting user
- **Returns:** `UserResponseDto` - User details

#### `deleteUser(Long userId, String loginUser)`
- **Description:** Deletes a user account.
- **Parameters:**
  - `userId` (Long): ID of the user to delete
  - `loginUser` (String): Username of the user performing the deletion
- **Returns:** `HashMap<String, String>` - Success message with user ID

#### `getAllUser(Boolean isApplicationAdmin, String loginUser)`
- **Description:** Retrieves all users, optionally filtered by admin status.
- **Parameters:**
  - `isApplicationAdmin` (Boolean): Filter by admin status (can be null)
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<UserResponseDto>` - List of users

#### `sendVerificationOtpToUser(String email, String phoneNumber)`
- **Description:** Sends OTP to user's email and/or phone for verification.
- **Parameters:**
  - `email` (String): User's email address
  - `phoneNumber` (String): User's phone number
- **Returns:** `HashMap<String, String>` - OTP sent confirmation
- **Throws:** `IOException` - If email/SMS sending fails
- **Business Logic:**
  - Generates 4-digit OTP
  - Sends OTP via email (SendGrid) and/or SMS (Twilio)
  - Stores OTP in user login info

#### `verifyUserOtp(String email, String phoneNumber, String otp)`
- **Description:** Verifies the OTP sent to the user.
- **Parameters:**
  - `email` (String): User's email address
  - `phoneNumber` (String): User's phone number
  - `otp` (String): OTP code to verify
- **Returns:** `HashMap<String, String>` - Verification result
- **Business Logic:**
  - Validates OTP matches stored OTP
  - Checks OTP expiration (10 minutes)
  - Updates user verification status

#### `resetUserPassword(ResetPasswordRequestDto request, Boolean isForgot)`
- **Description:** Resets user password. Can be used for forgot password or regular password change.
- **Parameters:**
  - `request` (ResetPasswordRequestDto): Contains email, old password (if not forgot), new password, confirm password
  - `isForgot` (Boolean): True for forgot password flow, false for regular password change
- **Returns:** `HashMap<String, String>` - Password reset confirmation
- **Business Logic:**
  - For forgot password: sends OTP, verifies OTP, then resets password
  - For regular change: validates old password, then updates to new password
  - Validates password strength requirements

---

## 6. Address Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.AddressServiceImpl`

### Overview
Manages user addresses for delivery and billing purposes.

### Methods

#### `createAddress(AddressRequestDto dto, String loginUser)`
- **Description:** Creates a new address for a user.
- **Parameters:**
  - `dto` (AddressRequestDto): Address details (address1, address2, city, state, country, zipCode, latitude, longitude, userId)
  - `loginUser` (String): Username of the user creating the address
- **Returns:** `AddressResponseDto` - Created address details
- **Business Logic:**
  - Validates required fields (address1, city, state, country, zipCode)
  - Validates zip code format
  - Validates latitude (-90 to 90) and longitude (-180 to 180)

#### `updateAddress(AddressRequestDto dto, Long addressId, String loginUser)`
- **Description:** Updates an existing address.
- **Parameters:**
  - `dto` (AddressRequestDto): Updated address information
  - `addressId` (Long): ID of the address to update
  - `loginUser` (String): Username of the user updating the address
- **Returns:** `AddressResponseDto` - Updated address details

#### `getAddressById(Long addressId, String loginUser)`
- **Description:** Retrieves a specific address by its ID.
- **Parameters:**
  - `addressId` (Long): ID of the address
  - `loginUser` (String): Username of the requesting user
- **Returns:** `AddressResponseDto` - Address details

#### `deleteAddress(Long addressId, String loginUser)`
- **Description:** Deletes an address.
- **Parameters:**
  - `addressId` (Long): ID of the address to delete
  - `loginUser` (String): Username of the user deleting the address
- **Returns:** `HashMap<String, String>` - Success message with address ID

#### `getAllAddresses(Long userId)`
- **Description:** Retrieves all addresses for a specific user.
- **Parameters:**
  - `userId` (Long): ID of the user
- **Returns:** `List<AddressResponseDto>` - List of user's addresses

---

## 7. Bank Account Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.BankAccountServiceImpl`

### Overview
Manages user bank account information for payment processing.

### Methods

#### `addBankAccount(BankAccountRequestDto dto, String loginUser)`
- **Description:** Adds a new bank account for a user.
- **Parameters:**
  - `dto` (BankAccountRequestDto): Bank account details (accountHolderName, accountNumber, ifscCode, bankName, branchName, accountType, userId)
  - `loginUser` (String): Username of the user adding the account
- **Returns:** `BankAccountResponseDto` - Created bank account details
- **Business Logic:**
  - Validates IFSC code format (11 characters)
  - Validates account number length (6-18 digits)
  - Ensures account number uniqueness

#### `updateBankAccount(BankAccountRequestDto dto, Long bankAccountId, String loginUser)`
- **Description:** Updates an existing bank account.
- **Parameters:**
  - `dto` (BankAccountRequestDto): Updated bank account information
  - `bankAccountId` (Long): ID of the bank account to update
  - `loginUser` (String): Username of the user updating the account
- **Returns:** `BankAccountResponseDto` - Updated bank account details

#### `getBankAccountById(Long bankAccountId, String loginUser)`
- **Description:** Retrieves a specific bank account by its ID.
- **Parameters:**
  - `bankAccountId` (Long): ID of the bank account
  - `loginUser` (String): Username of the requesting user
- **Returns:** `BankAccountResponseDto` - Bank account details

#### `getAllBankAccounts(Long userId, String loginUser)`
- **Description:** Retrieves all bank accounts for a specific user.
- **Parameters:**
  - `userId` (Long): ID of the user
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<BankAccountResponseDto>` - List of user's bank accounts

#### `deleteBankAccount(Long bankAccountId, String loginUser)`
- **Description:** Deletes a bank account.
- **Parameters:**
  - `bankAccountId` (Long): ID of the bank account to delete
  - `loginUser` (String): Username of the user deleting the account
- **Returns:** `Map<String, String>` - Success message with bank account ID

---

## 8. Credit Card Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.CreditCardServiceImpl`

### Overview
Manages user credit card information for payment processing.

### Methods

#### `addCreditCard(CreditCardRequestDto dto, String loginUser)`
- **Description:** Adds a new credit card for a user.
- **Parameters:**
  - `dto` (CreditCardRequestDto): Credit card details (cardHolderName, cardNumber, expiryDate, cvv, cardType, userId)
  - `loginUser` (String): Username of the user adding the card
- **Returns:** `CreditCardResponseDto` - Created credit card details
- **Business Logic:**
  - Validates card number length (15 for Amex, 16 for others)
  - Validates CVV length (3 for most cards, 4 for Amex)
  - Ensures card number uniqueness

#### `updateCreditCard(CreditCardRequestDto dto, Long cardId, String loginUser)`
- **Description:** Updates an existing credit card.
- **Parameters:**
  - `dto` (CreditCardRequestDto): Updated credit card information
  - `cardId` (Long): ID of the credit card to update
  - `loginUser` (String): Username of the user updating the card
- **Returns:** `CreditCardResponseDto` - Updated credit card details

#### `getCreditCardById(Long cardId, String loginUser)`
- **Description:** Retrieves a specific credit card by its ID.
- **Parameters:**
  - `cardId` (Long): ID of the credit card
  - `loginUser` (String): Username of the requesting user
- **Returns:** `CreditCardResponseDto` - Credit card details

#### `getAllCreditCards(Long userId, String loginUser)`
- **Description:** Retrieves all credit cards for a specific user.
- **Parameters:**
  - `userId` (Long): ID of the user
  - `loginUser` (String): Username of the requesting user
- **Returns:** `List<CreditCardResponseDto>` - List of user's credit cards

#### `deleteCreditCard(Long cardId, String loginUser)`
- **Description:** Deletes a credit card.
- **Parameters:**
  - `cardId` (Long): ID of the credit card to delete
  - `loginUser` (String): Username of the user deleting the card
- **Returns:** `Map<String, String>` - Success message with card ID

---

## 9. Notification Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.NotificationServiceImpl`

### Overview
Manages notification creation and storage. Integrates with email and SMS services.

### Methods

#### `addIntoNotification(Long userId, String subject, String content, String email, Boolean isSms)`
- **Description:** Creates a notification record and sends via email and/or SMS.
- **Parameters:**
  - `userId` (Long): ID of the user to notify
  - `subject` (String): Notification subject
  - `content` (String): Notification content/message
  - `email` (String): User's email address
  - `isSms` (Boolean): Whether to send SMS notification
- **Returns:** `void`
- **Business Logic:**
  - Creates notification record in database
  - Sends email via SendGrid if email provided
  - Sends SMS via Twilio if isSms is true
  - Updates notification status (SUCCESS/FAIL)

---

## 10. OTP Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.OtpServiceImpl`

### Overview
Generates OTP (One-Time Password) codes for user verification.

### Methods

#### `generateOtp()`
- **Description:** Generates a random 4-digit OTP code.
- **Parameters:** None
- **Returns:** `String` - 4-digit OTP code (e.g., "1234")
- **Business Logic:**
  - Generates random number between 1000 and 9999
  - Returns as string with leading zeros if needed

---

## 11. Application Details Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.ApplicationDetailsServiceImpl`

### Overview
Provides application information and terms & conditions.

### Methods

#### `getApplicationDetails()`
- **Description:** Returns application overview, features, and terms & conditions.
- **Parameters:** None
- **Returns:** `String` - Formatted text containing:
  - Application overview
  - Key features
  - Terms and conditions
  - User registration & eligibility
  - Platform usage rules
  - Payment & refund policies
  - Delivery & logistics information
  - Liability & disclaimers
  - Privacy & data protection
  - Changes to terms

---

## 12. SendGrid API Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.SendGridApiServiceImpl`

### Overview
Handles email sending via SendGrid API.

### Methods

#### `sendEmail(String toEmail, String subject, String message, Long userId)`
- **Description:** Sends an email using SendGrid API.
- **Parameters:**
  - `toEmail` (String): Recipient email address
  - `subject` (String): Email subject
  - `message` (String): Email body/content
  - `userId` (Long): ID of the user (for notification tracking)
- **Returns:** `void`
- **Throws:** `IOException` - If email sending fails
- **Business Logic:**
  - Uses SendGrid API to send email
  - Creates notification record
  - Handles errors and updates notification status

---

## 13. Twilio Service

**Package:** `com.artisan_market_place.service`  
**Implementation:** `com.artisan_market_place.serviceImpl.TwilioServiceImpl`

### Overview
Handles SMS sending via Twilio API.

### Methods

#### `sendSms(Long userId, String subject, String email, String toMobile, String messageContent)`
- **Description:** Sends an SMS using Twilio API.
- **Parameters:**
  - `userId` (Long): ID of the user (for notification tracking)
  - `subject` (String): SMS subject/title
  - `email` (String): User's email (for notification record)
  - `toMobile` (String): Recipient phone number (with country code)
  - `messageContent` (String): SMS message text
- **Returns:** `String` - Twilio message SID or error message
- **Business Logic:**
  - Uses Twilio API to send SMS
  - Creates notification record
  - Handles errors and updates notification status

---

## 14. Auth Service

**Package:** `com.artisan_market_place.Security`  
**Implementation:** `com.artisan_market_place.Security.AuthService`

### Overview
Handles user authentication and JWT token generation.

### Methods

#### `login(LoginRequestDto authRequestDTO)`
- **Description:** Authenticates user and generates JWT access token.
- **Parameters:**
  - `authRequestDTO` (LoginRequestDto): Contains userName (email) and password
- **Returns:** `LoginResponseDto` - Contains access token, user ID, role, company name, admin status
- **Throws:** `LoginException` - If authentication fails or user is banned
- **Business Logic:**
  - Validates user exists and is not banned
  - Authenticates credentials using Spring Security
  - Generates JWT token with user email as subject
  - Returns token with user details

---

## Common Patterns

### Authentication
All service methods (except Auth Service) require a `loginUser` parameter which is extracted from the JWT token in the Authorization header.

### Validation
All services use validator classes to ensure:
- Required fields are present
- Data formats are correct
- Business rules are enforced
- Related entities exist

### Error Handling
Services throw custom exceptions:
- `ValidationException` - For invalid input data
- `ResourceNotFoundException` - For missing resources
- `LoginException` - For authentication failures

### Audit Trail
All entities extend `BaseEntity` which automatically tracks:
- `creationDate` - When record was created
- `createdBy` - Who created the record
- `lastUpdateDate` - When record was last updated
- `lastUpdatedBy` - Who last updated the record

### Transaction Management
Services use `@Transactional` annotation for operations that modify multiple entities to ensure data consistency.

---

## Service Dependencies

- **Booking Service** depends on: Product Service, Offer Service, User Service
- **Product Service** depends on: User Service, Image Repository
- **Offer Service** depends on: Product Service
- **Review Service** depends on: Product Service, Booking Service, User Service
- **User Service** depends on: OTP Service, Notification Service, SendGrid Service, Twilio Service
- **Notification Service** depends on: SendGrid Service, Twilio Service

---

## Version Information

- **Document Version:** 1.0
- **Last Updated:** 2024
- **Spring Boot Version:** 3.3.8
- **Java Version:** 17

---

## Notes

- All monetary values use `BigDecimal` for precision
- All dates are stored in UTC
- JWT tokens expire after 1 day (configurable)
- OTP codes expire after 10 minutes
- Product stock is automatically managed during booking operations
- Discounts are calculated automatically from active offers during booking creation

