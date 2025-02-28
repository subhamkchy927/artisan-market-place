package com.artisan_market_place.validators;

import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.Exception.ValidationException;
import com.artisan_market_place.constants.ApplicationConstants;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.entity.Address;
import com.artisan_market_place.entity.BankAccount;
import com.artisan_market_place.repository.AddressRepository;
import com.artisan_market_place.requestDto.AddressRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class AddressValidator {
    private final AddressRepository addressRepository;

    public AddressValidator(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void validateAddress(AddressRequestDto dto) {
        if (dto == null) throw new ValidationException(MessageConstants.ADDRESS_DATA_CANNOT_BE_NULL);
        if (dto.getUserId() == null) throw new ValidationException(MessageConstants.USER_ID_REQUIRED);
        if (!StringUtils.hasText(dto.getAddress1())) throw new ValidationException(MessageConstants.ADDRESS1_REQUIRED);
        if (!StringUtils.hasText(dto.getCity())) throw new ValidationException(MessageConstants.CITY_REQUIRED);
        if (!StringUtils.hasText(dto.getState())) throw new ValidationException(MessageConstants.STATE_REQUIRED);
        if (!StringUtils.hasText(dto.getCountry())) throw new ValidationException(MessageConstants.COUNTRY_REQUIRED);
        if (!StringUtils.hasText(dto.getZipCode())) throw new ValidationException(MessageConstants.ZIP_CODE_REQUIRED);
        validateZipCode(dto.getZipCode());
        validateCoordinates(dto.getLatitude(), dto.getLongitude());
    }
    private void validateZipCode(String zipCode) {
        if (!zipCode.matches(ApplicationConstants.ZIP_CODE_FORMAT)) {
            throw new ValidationException(MessageConstants.INVALID_ZIP_CODE_FORMAT);
        }
    }

    private void validateCoordinates(Double latitude, Double longitude) {
        if (latitude != null && (latitude < -90 || latitude > 90)) {
            throw new ValidationException(MessageConstants.INVALID_LATITUDE);
        }
        if (longitude != null && (longitude < -180 || longitude > 180)) {
            throw new ValidationException(MessageConstants.INVALID_LONGITUDE);
        }
    }

    public Address validateAddressIdAndReturn(Long addressId) {
        Optional<Address> addressOpt = Optional.empty();
        addressOpt = addressRepository.findById(addressId);
        if (!addressOpt.isPresent())throw new ResourceNotFoundException(MessageConstants.ADDRESS_NOT_FOUND);
        return addressOpt.get();
    }
}
