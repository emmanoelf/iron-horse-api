package com.ironhorse.unit.service;

import com.ironhorse.exception.ForbiddenAccessException;
import com.ironhorse.model.Car;
import com.ironhorse.model.Rental;
import com.ironhorse.model.User;
import com.ironhorse.repository.RentalRepository;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.impl.OneTimePasswordImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class OneTimePasswordServiceTest {
    @InjectMocks
    private OneTimePasswordImpl oneTimePassword;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private AuthenticatedService authenticatedService;

    private User mockUser;
    private Rental mockRental;

    @BeforeEach
    public void setUp(){
        this.mockUser = User.builder()
                .id(1L)
                .build();

        Car car = Car.builder()
                .id(1L)
                .user(this.mockUser)
                .build();

        this.mockRental = Rental.builder()
                .id(3L)
                .car(car)
                .build();

        lenient().when(this.cacheManager.getCache("oneTimePassword")).thenReturn(cache);
    }

    @Test
    @DisplayName("Should be able to generate OTP and store in chace")
    public void shouldBeAbleTOGenerateOTPAndStoreInCache(){
        Long rentalId = this.mockRental.getId();
        Long userId = this.mockUser.getId();

        when(this.rentalRepository.findById(rentalId)).thenReturn(Optional.of(this.mockRental));
        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);

        String otp = this.oneTimePassword.generateOneTimePassword(rentalId);

        verify(cache).put(eq(otp), eq(rentalId));

        assertNotNull(otp);
        assertEquals(6, otp.length());
    }

    @Test
    @DisplayName("Should be throw error when rental is not found")
    public void shouldBeThrowErrorWhenRentalIsNotFound(){
        Long rentalId = 9L;
        when(this.rentalRepository.findById(rentalId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> this.oneTimePassword.generateOneTimePassword(rentalId));

        assertEquals("Locação não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw error when user is not owner")
    public void shouldThrowErrorWhenUserIsNotOwner(){
        Long rentalId = this.mockRental.getId();
        Long userId = 9L;

        when(this.rentalRepository.findById(rentalId)).thenReturn(Optional.of(this.mockRental));
        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);

        ForbiddenAccessException exception = assertThrows(ForbiddenAccessException.class,
                () -> this.oneTimePassword.generateOneTimePassword(rentalId));

        assertEquals("Você não tem privilégios para executar esta ação", exception.getMessage());
    }

    @Test
    @DisplayName("Should validate OTP successfully")
    public void shouldValidateOTPSuccessfully(){
        Long rentalId = this.mockRental.getId();
        String otp = "123456";

        when(this.cache.get(otp, Long.class)).thenReturn(rentalId);

        boolean isValid = this.oneTimePassword.validateOneTimePassword(rentalId, otp);

        verify(cache, times(1)).evict("123456");
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should validate fail when otp does not match")
    public void shouldValidateFailWhenOtpDoesNotMatch(){
        Long rentalId = this.mockRental.getId();
        String otp = "123456";

        when(this.cache.get(otp, Long.class)).thenReturn(9L);

        boolean isValid = this.oneTimePassword.validateOneTimePassword(rentalId, otp);

        verify(cache, never()).evict(any());
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should validate fail when otp is not found in cache")
    public void shouldValidateFailWhenOtpIsNotFoundInCache(){
        Long rentalId = this.mockRental.getId();
        String otp = "123456";

        when(this.cache.get(otp, Long.class)).thenReturn(null);

        boolean isValid = this.oneTimePassword.validateOneTimePassword(rentalId, otp);

        verify(cache, never()).evict(any());
        assertFalse(isValid);
    }

}
