package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.Address;
import com.artisan_market_place.repository.AddressRepository;
import com.artisan_market_place.requestDto.AddressRequestDto;
import com.artisan_market_place.responseDto.AddressResponseDto;
import com.artisan_market_place.service.AddressService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public AddressResponseDto createAddress(AddressRequestDto dto,String loginUser) {
        Address address = setAddressDetails(new Address(), dto,loginUser);
        addressRepository.saveAndFlush(address);
        return getAddressDetails(address);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public AddressResponseDto updateAddress(AddressRequestDto dto, Long addressId, String loginUser) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found"));
        address = setAddressDetails(address, dto,loginUser);
        addressRepository.saveAndFlush(address);
        return getAddressDetails(address);
    }

    @Override
    public AddressResponseDto getAddressById(Long addressId, String loginUser) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found"));
        return getAddressDetails(address);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public HashMap<String, String> deleteAddress(Long addressId, String loginUser) {
        HashMap<String, String> response = new HashMap<>();
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found"));
        addressRepository.delete(address);
        response.put("addressId", addressId.toString());
        response.put("Status", "Success");
        return response;
    }

    @Override
    public List<AddressResponseDto> getAllAddresses(Long userId) {
        List<Address> addressList = addressRepository.findAll();
        return addressList.stream().map(this::getAddressDetails).collect(Collectors.toList());
    }

    private Address setAddressDetails(Address address, AddressRequestDto dto,String loginUser) {
        address.setUserId(dto.getUserId());
        address.setAddress1(dto.getAddress1());
        address.setAddress2(dto.getAddress2());
        address.setRawAddress(dto.getRawAddress());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setZipCode(dto.getZipCode());
        address.setLatitude(dto.getLatitude());
        address.setLongitude(dto.getLongitude());
        address.setAuditInfo(loginUser);
        return address;
    }

    private AddressResponseDto getAddressDetails(Address address) {
        AddressResponseDto responseDto = new AddressResponseDto();
        responseDto.setUserId(address.getUserId());
        responseDto.setAddressId(address.getAddressId());
        responseDto.setAddress1(address.getAddress1());
        responseDto.setAddress2(address.getAddress2());
        responseDto.setRawAddress(address.getRawAddress());
        responseDto.setCity(address.getCity());
        responseDto.setState(address.getState());
        responseDto.setCountry(address.getCountry());
        responseDto.setZipCode(address.getZipCode());
        responseDto.setLatitude(address.getLatitude());
        responseDto.setLongitude(address.getLongitude());
        return responseDto;
    }
}