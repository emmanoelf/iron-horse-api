package com.ironhorse.service;

import com.ironhorse.dto.PaymentResponseDto;
import com.ironhorse.dto.RentalDto;
import com.ironhorse.dto.RentalResponseDto;
import com.ironhorse.model.*;
import com.ironhorse.repository.CarRepository;
import com.ironhorse.repository.RentalRepository;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.impl.AuthenticatedServiceImpl;
import com.ironhorse.service.impl.CarOverviewServiceImpl;
import com.ironhorse.service.impl.RentalServiceImpl;
import com.ironhorse.service.impl.StripeServiceImpl;
import com.stripe.exception.ApiException;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {
    @InjectMocks
    private RentalServiceImpl rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticatedServiceImpl authenticatedService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private StripeServiceImpl paymentService;

    @Mock
    private CarOverviewServiceImpl carOverviewService;

    private User mockUser;
    private Car mockCar;
    private RentalDto rentalDto;
    private PaymentResponseDto paymentResponseDto;

    @BeforeEach
    public void setUp() {
        this.mockUser = User.builder()
                .id(1L)
                .email("email@test.com")
                .build();

        CarOverview carOverview = CarOverview.builder()
                .id(1L)
                .price(new BigDecimal("150.00"))
                .description("description")
                .isAvailable(true)
                .isActive(true)
                .car(this.mockCar)
                .numberTrips(0)
                .build();

        List<CarImages> carImages = List.of(
                CarImages.builder().id(1L).path("path-image").name("name-image").build()
        );

        CarInfo carInfo = CarInfo.builder()
                .id(1L)
                .carImages(carImages)
                .build();

        this.mockCar = Car.builder()
                .id(1L)
                .brand("Toyota")
                .model("Corolla")
                .manufactureYear(2022L)
                .user(this.mockUser)
                .carOverview(carOverview)
                .carInfo(carInfo)
                .build();

        this.rentalDto = new RentalDto(LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        this.paymentResponseDto = new PaymentResponseDto("https://link-to-payment.stripe.com");
    }

    @Test
    public void shouldSaveRentalSuccessfully() throws Exception {
        Long userId = this.mockUser.getId();
        Long carId = this.mockCar.getId();

        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);
        when(this.carRepository.isCarAvailableWithinDates(eq(carId), eq(this.rentalDto.startDate()), eq(this.rentalDto.expectedEndDate())))
                .thenReturn(Optional.of(this.mockCar));
        when(this.userRepository.findById(userId)).thenReturn(Optional.of(this.mockUser));

        when(this.paymentService.createPaymentLink(any()))
                .thenReturn(this.paymentResponseDto);

        when(this.rentalRepository.save(any())).thenAnswer(invocation -> {
            Rental rental = invocation.getArgument(0);
            rental.setStatus(RentalStatus.PENDING);
            return rental;
        });

        RentalResponseDto response = this.rentalService.save(this.rentalDto, this.mockCar.getId());

        assertNotNull(response);
        assertEquals("https://link-to-payment.stripe.com", response.url());
        verify(this.carOverviewService).setIsAvailable(carId, false);
        verify(this.paymentService).createPaymentLink(any());
    }

    @Test
    public void shouldThrowExceptionWhenCannotCreatePaymentLink() throws StripeException {
        Long userId = this.mockUser.getId();
        Long carId = this.mockCar.getId();

        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);
        when(this.carRepository.isCarAvailableWithinDates(eq(carId), eq(this.rentalDto.startDate()), eq(this.rentalDto.expectedEndDate())))
                .thenReturn(Optional.of(this.mockCar));
        when(this.userRepository.findById(userId)).thenReturn(Optional.of(this.mockUser));

        ApiException stripeException = new ApiException("Pagamento falhou", null, null, 400, null);
        when(paymentService.createPaymentLink(any())).thenThrow(stripeException);

        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> this.rentalService.save(this.rentalDto, carId));

        assertEquals("Erro ao processar o pagamento: Pagamento falhou", illegalStateException.getMessage());
        verify(this.carOverviewService).setIsAvailable(carId, true);
    }

    @Test
    public void shouldThrowExceptionWhenCarIsUnavailable() {
        Long userId = this.mockUser.getId();
        Long carId = this.mockCar.getId();

        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);
        when(this.carRepository.isCarAvailableWithinDates(eq(carId), eq(this.rentalDto.startDate()), eq(this.rentalDto.expectedEndDate())))
                .thenReturn(Optional.empty());

        IllegalArgumentException illegalException = assertThrows(IllegalArgumentException.class,
                () -> rentalService.save(this.rentalDto, carId));

        assertEquals("Este carro não está disponível para locação na data escolhida.", illegalException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenEndDateIsBeforeStartDate() {
        Long userId = this.mockUser.getId();
        RentalDto invalidDto = new RentalDto(LocalDateTime.now().plusDays(3), LocalDateTime.now());

        when(this.authenticatedService.getCurrentUserId()).thenReturn(userId);
        IllegalArgumentException illegalException = assertThrows(IllegalArgumentException.class,
                () -> rentalService.save(invalidDto, userId));

        assertEquals("A data de devolução não pode ser maior que a data de início", illegalException.getMessage());
    }

    @Test
    public void shouldConfirmRentalSuccessfully(){
        Rental pendingRental = Rental.builder()
                .id(1L)
                .status(RentalStatus.PENDING)
                .car(this.mockCar)
                .build();

        when(this.rentalRepository.findByCarIdAndStatus(eq(this.mockCar.getId()), any()))
                .thenReturn(Optional.of(pendingRental));

        this.rentalService.confirmRental(this.mockCar.getId());

        verify(this.carOverviewService).setIsAvailable(this.mockCar.getId(), false);
        verify(this.rentalRepository).save(pendingRental);

        assertEquals(RentalStatus.ACTIVE, pendingRental.getStatus());
    }

    @Test
    public void shouldThrowExceptionWhenConfirmRentalIsNotFound(){
        when(this.rentalRepository.findByCarIdAndStatus(eq(this.mockCar.getId()), any()))
                .thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> this.rentalService.confirmRental(this.mockCar.getId()));

        assertEquals("Locação não encontrada ou já confirmada.", exception.getMessage());
    }
}
