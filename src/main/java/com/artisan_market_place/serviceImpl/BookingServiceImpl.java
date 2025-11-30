package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.Booking;
import com.artisan_market_place.entity.ProductOffers;
import com.artisan_market_place.entity.Products;
import com.artisan_market_place.entity.Users;
import com.artisan_market_place.enums.BookingStatusEnums;
import com.artisan_market_place.enums.PaymentStatusEnum;
import com.artisan_market_place.repository.BookingRepository;
import com.artisan_market_place.repository.ProductOffersRepository;
import com.artisan_market_place.repository.ProductsRepositoryImpl;
import com.artisan_market_place.repository.UserRepository;
import com.artisan_market_place.requestDto.BookingRequestDto;
import com.artisan_market_place.responseDto.BookingResponseDto;
import com.artisan_market_place.service.BookingService;
import com.artisan_market_place.utils.DateTimeUtil;
import com.artisan_market_place.validators.BookingValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingValidator bookingValidator;
    private final ProductsRepositoryImpl productsRepository;
    private final UserRepository userRepository;
    private final ProductOffersRepository productOffersRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingValidator bookingValidator,
                              ProductsRepositoryImpl productsRepository, UserRepository userRepository,
                              ProductOffersRepository productOffersRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingValidator = bookingValidator;
        this.productsRepository = productsRepository;
        this.userRepository = userRepository;
        this.productOffersRepository = productOffersRepository;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BookingResponseDto createBooking(BookingRequestDto dto, String loginUser) {
        bookingValidator.validateCreateBookingRequest(dto);
        
        Products product = productsRepository.findById(dto.getProductId()).orElseThrow();
        Users customer = userRepository.findById(dto.getCustomerId()).orElseThrow();
        Users seller = userRepository.findById(product.getUserId()).orElseThrow();
        
        Booking booking = new Booking();
        booking.setProductId(dto.getProductId());
        booking.setCustomerId(dto.getCustomerId());
        booking.setSellerId(product.getUserId());
        booking.setQuantity(dto.getQuantity());
        booking.setUnitPrice(product.getPrice());
        booking.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
        
        // Calculate discount from active offers
        BigDecimal discountAmount = calculateDiscount(product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())), dto.getProductId(), customer);
        booking.setDiscountAmount(discountAmount);
        booking.setFinalPrice(booking.getTotalPrice().subtract(discountAmount));
        
        booking.setBookingDate(DateTimeUtil.getCurrentUTCDate());
        booking.setDeliveryDate(dto.getDeliveryDate());
        booking.setDeliveryAddressId(dto.getDeliveryAddressId());
        booking.setStatus(BookingStatusEnums.PENDING);
        booking.setPaymentMethod(dto.getPaymentMethod());
        booking.setPaymentStatus(PaymentStatusEnum.UNPAID);
        booking.setNotes(dto.getNotes());
        booking.setAuditInfo(loginUser);
        
        // Update product stock
        product.setStockQuantity(product.getStockQuantity() - dto.getQuantity());
        productsRepository.save(product);
        
        bookingRepository.save(booking);
        return getBookingResponse(booking);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BookingResponseDto updateBooking(BookingRequestDto dto, Long bookingId, String loginUser) {
        bookingValidator.validateUpdateBookingRequest(bookingId, dto);
        
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        Products product = productsRepository.findById(booking.getProductId()).orElseThrow();
        
        if (dto.getQuantity() != null && !dto.getQuantity().equals(booking.getQuantity())) {
            int quantityDiff = dto.getQuantity() - booking.getQuantity();
            if (product.getStockQuantity() < quantityDiff) {
                throw new RuntimeException("Insufficient stock for quantity update");
            }
            booking.setQuantity(dto.getQuantity());
            booking.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
            booking.setFinalPrice(booking.getTotalPrice().subtract(booking.getDiscountAmount()));
            product.setStockQuantity(product.getStockQuantity() - quantityDiff);
            productsRepository.save(product);
        }
        
        if (dto.getDeliveryAddressId() != null) {
            booking.setDeliveryAddressId(dto.getDeliveryAddressId());
        }
        if (dto.getDeliveryDate() != null) {
            booking.setDeliveryDate(dto.getDeliveryDate());
        }
        if (dto.getPaymentMethod() != null) {
            booking.setPaymentMethod(dto.getPaymentMethod());
        }
        if (dto.getNotes() != null) {
            booking.setNotes(dto.getNotes());
        }
        
        booking.setAuditInfo(loginUser);
        bookingRepository.save(booking);
        return getBookingResponse(booking);
    }

    @Override
    public BookingResponseDto getBookingById(Long bookingId, String loginUser) {
        Booking booking = bookingValidator.validateBookingIdAndReturn(bookingId);
        return getBookingResponse(booking);
    }

    @Override
    public List<BookingResponseDto> getAllBookings(String loginUser) {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::getBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> getBookingsByCustomerId(Long customerId, String loginUser) {
        List<Booking> bookings = bookingRepository.findByCustomerId(customerId);
        return bookings.stream()
                .map(this::getBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> getBookingsBySellerId(Long sellerId, String loginUser) {
        List<Booking> bookings = bookingRepository.findBySellerId(sellerId);
        return bookings.stream()
                .map(this::getBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> getBookingsByStatus(String status, String loginUser) {
        BookingStatusEnums statusEnum = BookingStatusEnums.valueOf(status.toUpperCase());
        List<Booking> bookings = bookingRepository.findByStatus(statusEnum);
        return bookings.stream()
                .map(this::getBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public HashMap<String, String> cancelBooking(Long bookingId, String loginUser) {
        bookingValidator.validateBookingCancellation(bookingId, loginUser);
        
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        Products product = productsRepository.findById(booking.getProductId()).orElseThrow();
        
        // Restore stock
        product.setStockQuantity(product.getStockQuantity() + booking.getQuantity());
        productsRepository.save(product);
        
        booking.setStatus(BookingStatusEnums.CANCELLED);
        booking.setAuditInfo(loginUser);
        bookingRepository.save(booking);
        
        HashMap<String, String> response = new HashMap<>();
        response.put("bookingId", bookingId.toString());
        response.put("status", "Booking cancelled successfully");
        return response;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public HashMap<String, String> confirmBooking(Long bookingId, String loginUser) {
        Booking booking = bookingValidator.validateBookingIdAndReturn(bookingId);
        if (booking.getStatus() != BookingStatusEnums.PENDING) {
            throw new RuntimeException("Only pending bookings can be confirmed");
        }
        
        booking.setStatus(BookingStatusEnums.CONFIRMED);
        booking.setPaymentStatus(PaymentStatusEnum.PAID);
        booking.setAuditInfo(loginUser);
        bookingRepository.save(booking);
        
        HashMap<String, String> response = new HashMap<>();
        response.put("bookingId", bookingId.toString());
        response.put("status", "Booking confirmed successfully");
        return response;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public HashMap<String, String> completeBooking(Long bookingId, String loginUser) {
        Booking booking = bookingValidator.validateBookingIdAndReturn(bookingId);
        if (booking.getStatus() != BookingStatusEnums.CONFIRMED && booking.getStatus() != BookingStatusEnums.DELIVERED) {
            throw new RuntimeException("Only confirmed or delivered bookings can be completed");
        }
        
        booking.setStatus(BookingStatusEnums.COMPLETED);
        booking.setAuditInfo(loginUser);
        bookingRepository.save(booking);
        
        HashMap<String, String> response = new HashMap<>();
        response.put("bookingId", bookingId.toString());
        response.put("status", "Booking completed successfully");
        return response;
    }

    private BookingResponseDto getBookingResponse(Booking booking) {
        if (booking == null) return null;
        
        BookingResponseDto response = new BookingResponseDto();
        response.setBookingId(booking.getBookingId());
        response.setProductId(booking.getProductId());
        response.setCustomerId(booking.getCustomerId());
        response.setSellerId(booking.getSellerId());
        response.setDeliveryPersonId(booking.getDeliveryPersonId());
        response.setQuantity(booking.getQuantity());
        response.setUnitPrice(booking.getUnitPrice());
        response.setTotalPrice(booking.getTotalPrice());
        response.setDiscountAmount(booking.getDiscountAmount());
        response.setFinalPrice(booking.getFinalPrice());
        response.setBookingDate(booking.getBookingDate());
        response.setDeliveryDate(booking.getDeliveryDate());
        response.setDeliveryAddressId(booking.getDeliveryAddressId());
        response.setStatus(booking.getStatus().toString());
        response.setPaymentMethod(booking.getPaymentMethod());
        response.setPaymentStatus(booking.getPaymentStatus().toString());
        response.setNotes(booking.getNotes());
        response.setCreationDate(booking.getCreationDate());
        response.setLastUpdateDate(booking.getLastUpdateDate());
        
        // Fetch and set names
        Optional<Products> productOpt = productsRepository.findById(booking.getProductId());
        if (productOpt.isPresent()) {
            response.setProductName(productOpt.get().getName());
        }
        
        Optional<Users> customerOpt = userRepository.findById(booking.getCustomerId());
        if (customerOpt.isPresent()) {
            Users customer = customerOpt.get();
            response.setCustomerName(customer.getFirirstName() + " " + customer.getLastName());
        }
        
        Optional<Users> sellerOpt = userRepository.findById(booking.getSellerId());
        if (sellerOpt.isPresent()) {
            Users seller = sellerOpt.get();
            response.setSellerName(seller.getFirirstName() + " " + seller.getLastName());
        }
        
        if (booking.getDeliveryPersonId() != null) {
            Optional<Users> deliveryPersonOpt = userRepository.findById(booking.getDeliveryPersonId());
            if (deliveryPersonOpt.isPresent()) {
                Users deliveryPerson = deliveryPersonOpt.get();
                response.setDeliveryPersonName(deliveryPerson.getFirirstName() + " " + deliveryPerson.getLastName());
            }
        }
        
        return response;
    }

    private BigDecimal calculateDiscount(BigDecimal totalPrice, Long productId, Users customer) {
        Date currentDate = DateTimeUtil.getCurrentUTCDate();
        List<ProductOffers> activeOffers = productOffersRepository.findActiveOffersByProductId(productId, currentDate);
        
        if (activeOffers.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal maxDiscount = BigDecimal.ZERO;
        for (ProductOffers offer : activeOffers) {

            // minimum purchase amount
            if (offer.getMinPurchaseAmount() != null && totalPrice.compareTo(offer.getMinPurchaseAmount()) < 0) {
                continue;
            }
            
            BigDecimal discount = BigDecimal.ZERO;
            
            // percentage discount
            if (offer.getPercentageDiscount() != null && offer.getPercentageDiscount() > 0) {
                discount = totalPrice.multiply(BigDecimal.valueOf(offer.getPercentageDiscount()).divide(BigDecimal.valueOf(100)));
                if (offer.getMaxDiscountAmount() != null && discount.compareTo(offer.getMaxDiscountAmount()) > 0) {
                    discount = offer.getMaxDiscountAmount();
                }
            }
            
            // flat discount
            if (offer.getFlatDiscount() != null && offer.getFlatDiscount().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal flatDiscount = offer.getFlatDiscount();
                if (discount.compareTo(flatDiscount) < 0) {
                    discount = flatDiscount;
                }
            }
            
            if (discount.compareTo(maxDiscount) > 0) {
                maxDiscount = discount;
            }
        }
        
        return maxDiscount;
    }
}

