package com.artisan_market_place.validators;

import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.Exception.ValidationException;
import com.artisan_market_place.constants.ApplicationConstants;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.entity.Users;
import com.artisan_market_place.repository.UserRepository;
import com.artisan_market_place.requestDto.UserRequestDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserValidator{

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void validateMandatory(UserRequestDto user) {
        if (user == null) throw new ValidationException(MessageConstants.USER_OBJECT_NULL);
        if (!StringUtils.hasText(user.getFirstName()))throw new ValidationException(MessageConstants.FIRST_NAME_MANDATORY);
        if (!StringUtils.hasText(user.getEmail()))throw new ValidationException(MessageConstants.EMAIL_MANDATORY);
        if (!StringUtils.hasText(user.getPhoneNumber()))throw new ValidationException(MessageConstants.PHONE_NUMBER_MANDATORY);
        if (user.getStatus() == null)throw new ValidationException(MessageConstants.STATUS_MANDATORY);
        if (user.getUserRole() == null)throw new ValidationException(MessageConstants.ROLE_MANDATORY);
    }
    public void validateCreateUserRequest(UserRequestDto user){
        if (userRepository.existsByEmail(user.getEmail()))throw new ValidationException(MessageConstants.EMAIL_ALREADY_EXISTS);
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber()))throw new ValidationException(MessageConstants.PHONE_NUMBER_ALREADY_EXISTS);
        validatePassword(user.getPassword());
    }
    public void validateUpdateUserRequest(Long userId, UserRequestDto user){
        if (userRepository.existsByEmailAndUserIdNot(user.getEmail(),userId)) throw new ValidationException(MessageConstants.EMAIL_ALREADY_EXISTS);
        if (userRepository.existsByPhoneNumberAndUserIdNot(user.getPhoneNumber(),userId)) throw new ValidationException(MessageConstants.PHONE_NUMBER_ALREADY_EXISTS);
    }
    public void validatePassword(String password) throws ValidationException {
        if (password.length() < ApplicationConstants.MIN_LENGTH) throw new ValidationException((MessageConstants.PASSWORD_LENGTH_MESSAGE));
        if (!password.matches(ApplicationConstants.UPPERCASE_PATTERN)) throw new ValidationException(MessageConstants.UPPERCASE_MESSAGE);
        if (!password.matches(ApplicationConstants.LOWERCASE_PATTERN))throw new ValidationException(MessageConstants.LOWERCASE_MESSAGE);
        if (!password.matches(ApplicationConstants.DIGIT_PATTERN))throw new ValidationException(MessageConstants.DIGIT_MESSAGE);
        if (!password.matches(ApplicationConstants.SPECIAL_CHARACTER_PATTERN))throw new ValidationException(MessageConstants.SPECIAL_CHARACTER_MESSAGE);
    }
    public Users validateUserIdAndReturn(Long userId){
        Optional<Users> userOpt = Optional.empty();
        if(!userOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND);
        Users user = userOpt.get();
        return user;
    }
}
