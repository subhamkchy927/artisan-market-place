package com.artisan_market_place.validators;

import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.entity.Users;
import com.artisan_market_place.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GlobalValidator {

    private final UserRepository userRepository;

    public GlobalValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUserId(Long userId){
      Optional<Users> user = userRepository.findById(userId);
      if(!user.isPresent()) throw new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND);
    }
}
