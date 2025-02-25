package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.Users;
import com.artisan_market_place.entity.UsersLoginInfo;
import com.artisan_market_place.repository.LoginUserRepository;
import com.artisan_market_place.repository.UserRepository;
import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import com.artisan_market_place.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LoginUserRepository usersLoginInfoRepository;

    @Autowired
    PasswordEncoder encoder;
    public UserServiceImpl(UserRepository userRepository, LoginUserRepository usersLoginInfoRepository) {
        this.userRepository = userRepository;
        this.usersLoginInfoRepository = usersLoginInfoRepository;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        Users users = setSellerDetails(dto);
        userRepository.saveAndFlush(users);
        setLoginInfo(users.getUserId(),dto);
        return getSellerDetails(users);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserResponseDto updateUser(UserRequestDto dto, Long sellerId) {
        Optional<Users> optionalSeller = userRepository.findById(sellerId);
        if (optionalSeller.isPresent()) {
            Users users = optionalSeller.get();
            users = setSellerDetails(dto);
            userRepository.save(users);
            return getSellerDetails(users);
        }
        return null;
    }

    @Override
    public UserResponseDto getUserById(Long sellerId) {
        Optional<Users> optionalSeller = userRepository.findById(sellerId);
        return optionalSeller.map(this::getSellerDetails).orElse(null);
    }

    @Override
    public HashMap<String, String> deleteUser(Long sellerId) {
        HashMap<String, String> response = new HashMap<>();
        Optional<Users> optionalSeller = userRepository.findById(sellerId);
        if (optionalSeller.isPresent()) {
            Users users = optionalSeller.get();
            userRepository.delete(users);
        }
        response.put("Seller Id", sellerId.toString());
        response.put("Status", "Success");
        return response;
    }

    @Override
    public List<UserResponseDto> getAllUser(Boolean isApplicationAdmin) {
        List<Users> sellers = userRepository.findAll();
        return sellers.stream()
                .filter(seller -> isApplicationAdmin == null || seller.getIsAdmin().equals(isApplicationAdmin))
                .map(this::getSellerDetails)
                .collect(Collectors.toList());
    }


    private Users setSellerDetails(UserRequestDto dto) {
        Users users = new Users();
        users.setFirirstName(dto.getFirstName());
        users.setMiddleName(dto.getMiddleName());
        users.setLastName(dto.getLastName());
        users.setPhoneNumber(dto.getPhoneNumber());
        users.setCompanyName(dto.getCompanyName());
        users.setGstNumber(dto.getGstNumber());
        users.setEmail(dto.getEmail());
        users.setStatus(dto.getStatus());
        users.setRating(dto.getSellerRating());
        users.setIsAdmin(dto.getIsApplicationAdmin());
        users.setCountryCode(dto.getCountryCode());
        users.setRole(dto.getUserRole());
        users.setAuditInfo("system");
        return users;
    }

    private UserResponseDto getSellerDetails(Users user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setFirstName(user.getFirirstName());
        responseDto.setMiddleName(user.getMiddleName());
        responseDto.setLastName(user.getLastName());
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
        loginInfo.setUserId(1L);
        loginInfo.setLoginId(dto.getEmail());
        loginInfo.setPassword(encoder.encode(dto.getPassword()));
        loginInfo.setAuditInfo("system");
        usersLoginInfoRepository.saveAndFlush(loginInfo);
    }
}
