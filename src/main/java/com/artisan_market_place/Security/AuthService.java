package com.artisan_market_place.Security;

import com.artisan_market_place.entity.Users;
import com.artisan_market_place.repository.UserRepository;
import com.artisan_market_place.requestDto.LoginRequestDto;
import com.artisan_market_place.responseDto.LoginResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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

    public LoginResponseDto login(@RequestBody LoginRequestDto authRequestDTO){
        Users loginUser = authRequestDTO.getUserName() != null ? userRepository.findByEmail(authRequestDTO.getUserName()) : null;
        LoginResponseDto response = new  LoginResponseDto();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUserName(), authRequestDTO.getPassword()));
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
        else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
        return response;
    }
}

