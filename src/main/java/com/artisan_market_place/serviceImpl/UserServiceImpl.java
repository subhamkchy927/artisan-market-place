package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.Exception.InternalServerErrorException;
import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.Exception.ValidationException;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.constants.NotificationConstant;
import com.artisan_market_place.entity.Users;
import com.artisan_market_place.entity.UsersLoginInfo;
import com.artisan_market_place.enums.UserRolesEnums;
import com.artisan_market_place.enums.UserStatusEnums;
import com.artisan_market_place.repository.LoginUserRepository;
import com.artisan_market_place.repository.UserRepository;
import com.artisan_market_place.requestDto.ResetPasswordRequestDto;
import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.AddressResponseDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import com.artisan_market_place.service.UserService;
import com.artisan_market_place.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressServiceImpl addressService;
    private final OtpServiceImpl otpServiceImpl;
    private final LoginUserRepository usersLoginInfoRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder encoder;
    private final TwilioServiceImpl twilioService;
    private final SendGridApiServiceImpl sendGridApiService;

    public UserServiceImpl(UserRepository userRepository, AddressServiceImpl addressServiceImpl, AddressServiceImpl addressService, OtpServiceImpl otpServiceImpl, LoginUserRepository usersLoginInfoRepository, UserValidator userValidator, PasswordEncoder encoder, TwilioServiceImpl twilioService, SendGridApiServiceImpl sendGridApiService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.otpServiceImpl = otpServiceImpl;
        this.usersLoginInfoRepository = usersLoginInfoRepository;
        this.userValidator = userValidator;
        this.encoder = encoder;
        this.twilioService = twilioService;
        this.sendGridApiService = sendGridApiService;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserResponseDto createUser(UserRequestDto dto) {
        userValidator.validateCreateUserRequest(dto);
        Users user = new Users();
        user = setUserDetails(user, dto,dto.getEmail());
        userRepository.saveAndFlush(user);
        AddressResponseDto address;
        if(dto.getAddress() != null){
        dto.getAddress().setUserId(user.getUserId());
        addressService.createAddress(dto.getAddress(),dto.getEmail());
        }
        setLoginInfo(user.getUserId(),dto);
        return getUserDetails(user);
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto dto, Long userId,String loginUser) {
        Users user = userValidator.validateUserIdAndReturn(userId);
        userValidator.validateUpdateUserRequest(userId,dto);
        user = setUserDetails(user,dto,loginUser);
        userRepository.saveAndFlush(user);
        return getUserDetails(user);
    }

    @Override
    public UserResponseDto getUserById(Long userId,String loginUser) {
        Users user = userValidator.validateUserIdAndReturn(userId);
        UserResponseDto response = new UserResponseDto();
        response = getUserDetails(user);
        return response;
    }

    @Override
    public HashMap<String, String> deleteUser(Long userId,String loginUser) {
        HashMap<String, String> response = new HashMap<>();
        Users user = userValidator.validateUserIdAndReturn(userId);
        userRepository.delete(user);
        response.put("userId", userId.toString());
        response.put("Status", "User deleted successfully");
        return response;
    }

    @Override
    public List<UserResponseDto> getAllUser(Boolean isApplicationAdmin,String loginUser) {
        List<Users> usersList = userRepository.findAll();
        return usersList.stream()
        .filter(user -> isApplicationAdmin == null || user.getIsAdmin().equals(isApplicationAdmin))
        .map(this::getUserDetails)
        .collect(Collectors.toList());
    }

    @Override
    public HashMap<String, String> sendVerificationOtpToUser(String email,String phonNumber) throws IOException {
        Users user = userRepository.findByEmailOrPhoneNumber(email,phonNumber);
        if (user == null) throw new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND);
        UsersLoginInfo userLoginInfo = usersLoginInfoRepository.findByLoginId(user.getEmail());
        String otp = otpServiceImpl.generateOtp();
        String phoneNumber = user.getCountryCode().concat(user.getPhoneNumber());
        userLoginInfo.setLastOtp(otp);
        usersLoginInfoRepository.save(userLoginInfo);
        String subject = NotificationConstant.USER_OTP_SUBJECT;
        String otpContent = String.format(MessageConstants.VERIFICATION_OTP_CONTENT, otp);
        try {
        sendGridApiService.sendEmail(user.getEmail(), subject, otpContent,user.getUserId());
        } catch (Exception e) {
        log.info (MessageConstants.ERRROR_SENDING_EMAIL,e.getMessage());
        }
        try {
        twilioService.sendSms(user.getUserId(),subject,email, phoneNumber, otpContent);
        } catch (Exception e) {
        log.info (MessageConstants.ERROR_SENDING_SMS,e.getMessage());
        }
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "OTP sent successfully.");
        return response;
    }

    @Override
    public HashMap<String, String> verifyUserOtp(String email,String phonNumber, String otp) {
        Users user = userRepository.findByEmailOrPhoneNumber(email,phonNumber);
        if (user == null) throw new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND);
        UsersLoginInfo userLoginInfo = usersLoginInfoRepository.findByLoginId(user.getEmail());
        if (!otp.equals(userLoginInfo.getLastOtp())) throw new ValidationException(MessageConstants.INVALID_OTP);
        userLoginInfo.setLastOtp(null);
        user.setStatus(UserStatusEnums.ACTIVE);
        usersLoginInfoRepository.save(userLoginInfo);
        userRepository.save(user);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "OTP verified successfully.");
        return response;
    }

    @Override
    public HashMap<String, String> resetUserPassword(ResetPasswordRequestDto request,Boolean isForgot) {
        UsersLoginInfo userLoginInfo = usersLoginInfoRepository.findByLoginId(request.getEmail());
        if (userLoginInfo == null) throw new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND);
        if (!isForgot && !encoder.matches(request.getOldPassword(), userLoginInfo.getPassword()))
            throw new ValidationException(MessageConstants.INVALID_OLD_PASSWORD);
        if (!request.getNewPassword().equals(request.getConfirmPassword()))
            throw new ValidationException(MessageConstants.PASSWORD_MISMATCH);
        userLoginInfo.setPassword(encoder.encode(request.getNewPassword()));
        usersLoginInfoRepository.save(userLoginInfo);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Password reset successfully.");
        return response;
    }

    private Users setUserDetails(Users users,UserRequestDto dto,String loginUser) {
        users.setFirirstName(dto.getFirstName());
        users.setMiddleName(dto.getMiddleName());
        users.setLastName(dto.getLastName());
        users.setPhoneNumber(dto.getPhoneNumber());
        users.setCompanyName(dto.getCompanyName());
        users.setGstNumber(dto.getGstNumber());
        users.setEmail(dto.getEmail());
        users.setStatus(UserStatusEnums.PENDING);
        users.setRating(dto.getUserRating());
        users.setIsAdmin(dto.getIsApplicationAdmin());
        users.setCountryCode(dto.getCountryCode());
        users.setRole(UserRolesEnums.valueOf(dto.getUserRole()));
        users.setAuditInfo(loginUser);
        return users;
    }

    private UserResponseDto getUserDetails(Users user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setUserId(user.getUserId());
        responseDto.setUserRole(user.getRole());
        responseDto.setFirstName(user.getFirirstName());
        responseDto.setMiddleName(user.getMiddleName());
        responseDto.setLastName(user.getLastName());
        responseDto.setEmail(user.getEmail());
        responseDto.setEmail(user.getEmail());
        responseDto.setEmail(user.getEmail());
        responseDto.setPhoneNumber(user.getPhoneNumber());
        responseDto.setCompanyName(user.getCompanyName());
        responseDto.setGstNumber(user.getGstNumber());
        responseDto.setStatus(user.getStatus());
        responseDto.setRating(user.getRating());
        responseDto.setIsApplicationAdmin(user.getIsAdmin());
        responseDto.setCountryCode(user.getCountryCode());
        responseDto.setAddress(addressService.getAllAddresses(user.getUserId()));
        return responseDto;
    }

    public void setLoginInfo(Long userId, UserRequestDto dto){
        UsersLoginInfo loginInfo = new UsersLoginInfo();
        loginInfo.setUserId(userId);
        loginInfo.setLoginId(dto.getEmail());
        loginInfo.setPassword(encoder.encode(dto.getPassword()));
        loginInfo.setAuditInfo("system");
        usersLoginInfoRepository.saveAndFlush(loginInfo);
    }
}
