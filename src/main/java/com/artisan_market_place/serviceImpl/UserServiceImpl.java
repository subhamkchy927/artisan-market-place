package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.Users;
import com.artisan_market_place.entity.UsersLoginInfo;
import com.artisan_market_place.enums.UserRolesEnums;
import com.artisan_market_place.enums.UserStatusEnums;
import com.artisan_market_place.repository.LoginUserRepository;
import com.artisan_market_place.repository.UserRepository;
import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import com.artisan_market_place.service.UserService;
import com.artisan_market_place.validators.UserValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LoginUserRepository usersLoginInfoRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder encoder;
    public UserServiceImpl(UserRepository userRepository, LoginUserRepository usersLoginInfoRepository, UserValidator userValidator, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.usersLoginInfoRepository = usersLoginInfoRepository;
        this.userValidator = userValidator;
        this.encoder = encoder;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserResponseDto createUser(UserRequestDto dto,String loginUser) {
        userValidator.validateCreateUserRequest(dto);
        Users user = new Users();
        user = setUserDetails(user, dto,loginUser);
        userRepository.saveAndFlush(user);
        setLoginInfo(user.getUserId(),dto);
        return getUserDetails(user);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserResponseDto updateUser(UserRequestDto dto, Long userId,String loginUser) {
        Users user = userValidator.validateUserIdAndReturn(userId);
        userValidator.validateUpdateUserRequest(userId,dto);
        user = setUserDetails(user,dto,loginUser);
        userRepository.save(user);
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
        response.put("Status", "Success");
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



    private Users setUserDetails(Users users,UserRequestDto dto,String loginUser) {
        users.setFirirstName(dto.getFirstName());
        users.setMiddleName(dto.getMiddleName());
        users.setLastName(dto.getLastName());
        users.setPhoneNumber(dto.getPhoneNumber());
        users.setCompanyName(dto.getCompanyName());
        users.setGstNumber(dto.getGstNumber());
        users.setEmail(dto.getEmail());
        users.setStatus(UserStatusEnums.valueOf(dto.getStatus()));
        users.setRating(dto.getUserRating());
        users.setIsAdmin(dto.getIsApplicationAdmin());
        users.setCountryCode(dto.getCountryCode());
        users.setRole(UserRolesEnums.valueOf(dto.getUserRole()));
        users.setAuditInfo("system");
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
