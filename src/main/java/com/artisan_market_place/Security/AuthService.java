package com.artisan_market_place.Security;

import com.artisan_market_place.constants.ApplicationConstants;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.entity.Users;
import com.artisan_market_place.enums.UserStatusEnums;
import com.artisan_market_place.repository.UserRepository;
import com.artisan_market_place.requestDto.LoginRequestDto;
import com.artisan_market_place.responseDto.LoginResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.artisan_market_place.Exception.LoginException;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public LoginResponseDto login(@RequestBody LoginRequestDto authRequestDTO) throws LoginException {
           Users loginUser = userRepository.findByEmail(authRequestDTO.getUserName());
           if(UserStatusEnums.BANNED.equals(loginUser.getStatus())){
               throw new LoginException(MessageConstants.USER_BANNED);
           }
           LoginResponseDto response = new  LoginResponseDto();
           Authentication authentication = null;
           try {
             authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUserName(), authRequestDTO.getPassword()));
           }catch(Exception e){
            throw new com.artisan_market_place.Exception.LoginException(e.getLocalizedMessage(), MessageConstants.L1000);
           }
           if(authentication.isAuthenticated()){
            String token = jwtUtil.GenerateToken(authRequestDTO.getUserName());
            response.setAccessToken(token);
            response.setAccessToken(token);
            response.setUserId(loginUser.getUserId());
            response.setType("Bearer");
            response.setCompanyName(loginUser.getCompanyName());
            response.setRole(loginUser.getRole());
            response.setIsAdmin(loginUser.getIsAdmin());
            }
            return response;
    }
}

