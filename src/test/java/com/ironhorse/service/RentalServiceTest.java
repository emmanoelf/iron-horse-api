package com.ironhorse.service;

import com.ironhorse.dto.PaymentResponseDto;
import com.ironhorse.dto.RentalDto;
import com.ironhorse.dto.RentalResponseDetailsDto;
import com.ironhorse.dto.RentalResponseDto;
import com.ironhorse.exception.ForbiddenAccessException;
import com.ironhorse.model.*;
import com.ironhorse.repository.CarRepository;
import com.ironhorse.repository.RentalRepository;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.repository.projection.RentalDetailsProjection;
import com.ironhorse.service.impl.AuthenticatedServiceImpl;
import com.ironhorse.service.impl.CarOverviewServiceImpl;
import com.ironhorse.service.impl.RentalServiceImpl;
import com.ironhorse.service.impl.StripeServiceImpl;
import com.stripe.exception.ApiException;
import com.stripe.exception.StripeException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    @Mock
    private OneTimePasswordService oneTimePasswordService;

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

    @Test
    public void shouldCancelRentalSuccessfully(){
        Rental pendingRental = Rental.builder()
                .id(1L)
                .user(this.mockUser)
                .car(this.mockCar)
                .status(RentalStatus.PENDING)
                .build();

        when(this.authenticatedService.getCurrentUserId()).thenReturn(this.mockUser.getId());
        when(this.rentalRepository.findById(pendingRental.getId())).thenReturn(Optional.of(pendingRental));

        this.rentalService.cancelRental(pendingRental.getId());

        verify(this.carOverviewService).setIsAvailable(pendingRental.getCar().getId(), true);
        verify(this.rentalRepository).save(pendingRental);

        assertEquals(RentalStatus.CANCELED, pendingRental.getStatus());
    }

    @Test
    public void shouldThrowForbiddenExceptionWhenUserIsNotOwner(){
        Rental rental = Rental.builder()
                .id(1L)
                .status(RentalStatus.PENDING)
                .user(User.builder()
                        .id(2L)
                        .build())
                .car(Car.builder()
                        .id(2L)
                        .user(User.builder()
                                .id(3L)
                                .build())
                        .build())
                .build();

        when(this.authenticatedService.getCurrentUserId()).thenReturn(this.mockUser.getId());
        when(this.rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        ForbiddenAccessException exception = assertThrows(ForbiddenAccessException.class,
                () -> this.rentalService.cancelRental(rental.getId()));

        assertEquals("Você não tem privilégios para cancelar esta locação", exception.getMessage());
    }

    @Test
    public void shouldExpiredRentalSuccessfully(){
        Rental pendingRental = Rental.builder()
                .id(1L)
                .status(RentalStatus.PENDING)
                .car(this.mockCar)
                .build();

        when(this.rentalRepository.findByCarIdAndStatus(eq(this.mockCar.getId()), any()))
                .thenReturn(Optional.of(pendingRental));

        this.rentalService.expiredRental(this.mockCar.getId());

        verify(this.rentalRepository).save(pendingRental);
        verify(this.carOverviewService).setIsAvailable(this.mockCar.getId(), true);
        assertEquals(RentalStatus.EXPIRED, pendingRental.getStatus());
    }

    @Test
    public void shouldThrowExceptionWhenExpiredRentalIsNotFound(){
        when(this.rentalRepository.findByCarIdAndStatus(eq(this.mockCar.getId()), any()))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.rentalService.expiredRental(this.mockCar.getId()));

        assertEquals("Locação não encontrada ou já confirmada.", exception.getMessage());
    }

    @Test
    public void shouldReturnRentalDetailsSuccessfully(){
        Long rentalId = 1L;

        RentalDetailsProjection rentalDetailsProjection = new RentalDetailsProjection(
                1L, LocalDateTime.now(),
                LocalDateTime.now().plusDays(3),
                RentalStatus.PENDING,
                this.mockCar.getId(),
                this.mockCar.getBrand(),
                this.mockCar.getModel(),
                this.mockCar.getManufactureYear(),
                this.mockCar.getCarOverview().getPrice()
        );

        when(this.authenticatedService.getCurrentUserId()).thenReturn(this.mockUser.getId());
        when(this.rentalRepository.findRentalWithDetails(eq(rentalId), eq(this.mockUser.getId())))
                .thenReturn(Optional.of(rentalDetailsProjection));

        RentalResponseDetailsDto result = this.rentalService.getRentalDetails(rentalId);
        assertEquals(0, result.totalPrice().compareTo(new BigDecimal("450.00")));
        assertEquals(3, result.daysRented());
    }

    @Test
    public void shouldThrowExceptionWhenRentalDetailsIsNotFound(){
        Long rentalId = 1L;
        when(this.authenticatedService.getCurrentUserId()).thenReturn(this.mockUser.getId());
        when(this.rentalRepository.findRentalWithDetails(rentalId, this.mockUser.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> rentalService.getRentalDetails(rentalId));
    }

    @Test
    public void shouldBeAbleToShowAllRentalsByLoggedUser(){
        Rental pendingRental = Rental.builder()
                .id(1L)
                .status(RentalStatus.PENDING)
                .car(this.mockCar)
                .user(this.mockUser)
                .build();

        Rental confirmedRental = Rental.builder()
                .id(1L)
                .status(RentalStatus.ACTIVE)
                .car(this.mockCar)
                .user(this.mockUser)
                .build();

        List<Rental> mockRentals = Arrays.asList(pendingRental, confirmedRental);
        when(this.authenticatedService.getCurrentUserId()).thenReturn(this.mockUser.getId());
        when(this.rentalRepository.findByUserId(this.mockUser.getId())).thenReturn(mockRentals);

        List<RentalResponseDto> result = this.rentalService.getAllRentalsByLoggedUser();

        verify(this.authenticatedService).getCurrentUserId();
        verify(this.rentalRepository).findByUserId(this.mockUser.getId());

        assertEquals(mockRentals.size(), result.size());
        assertEquals(confirmedRental.getId(), result.get(1).id());
    }

    @Test
    public void shouldThrowExceptionWhenRentalIsNotFound(){
        Long rentalIdNotExists = 77L;

        when(this.rentalRepository.findById(eq(rentalIdNotExists))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> this.rentalService.finishRental(rentalIdNotExists, "123456"));
    }

    @Test
    public void shouldThrowExceptionWhenOtpIsInvalid(){
        Rental rental = Rental.builder()
                .id(1L)
                .car(this.mockCar)
                .expectedEndDate(LocalDateTime.now().plusDays(3))
                .build();

        when(this.rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(this.oneTimePasswordService.validateOneTimePassword(1L, "123456")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.rentalService.finishRental(1L, "123456"));

        assertEquals("Código inválido", exception.getMessage());
    }

    @Test
    public void shouldFinishRentalSuccessfully(){
        String otp = "123456";
        Rental rental = Rental.builder()
                .id(1L)
                .car(this.mockCar)
                .user(this.mockUser)
                .status(RentalStatus.ACTIVE)
                .expectedEndDate(LocalDateTime.now().plusDays(1))
                .build();

        when(this.rentalRepository.findById(rental.getId())).thenReturn(Optional.of(rental));
        when(this.oneTimePasswordService.validateOneTimePassword(rental.getId(), otp))
                .thenReturn(true);

        RentalResponseDto result = this.rentalService.finishRental(rental.getId(), otp);

        verify(this.carOverviewService).setIsAvailable(this.mockCar.getId(), true);
        verify(this.carOverviewService).increaseNumberOfTrips(this.mockCar.getId());
        verify(this.rentalRepository).save(rental);

        assertEquals(RentalStatus.FINISHED.name(), result.status());
        assertEquals(rental.getId(), result.id());
        assertNotNull(rental.getRealEndDate());
    }

    @Test
    public void shouldFinishARentalAfterExpectedEndDate(){
        String otp = "123456";
        Rental rental = Rental.builder()
                .id(1L)
                .car(this.mockCar)
                .user(this.mockUser)
                .status(RentalStatus.ACTIVE)
                .expectedEndDate(LocalDateTime.now().minusDays(2))
                .build();

        when(this.rentalRepository.findById(rental.getId())).thenReturn(Optional.of(rental));
        when(this.oneTimePasswordService.validateOneTimePassword(rental.getId(), otp))
                .thenReturn(true);

        RentalResponseDto result = this.rentalService.finishRental(rental.getId(), otp);

        verify(this.carOverviewService).setIsAvailable(this.mockCar.getId(), true);
        verify(this.carOverviewService).increaseNumberOfTrips(this.mockCar.getId());
        verify(this.rentalRepository).save(rental);

        assertEquals(RentalStatus.FINISHED_LATE.name(), result.status());
        assertEquals(rental.getId(), result.id());
        assertNotNull(rental.getRealEndDate());
    }

    @Test
    public void shouldCancelPendingRentalWithWebhookEventSuccessfully() {
        Rental rental = Rental.builder()
                .id(1L)
                .car(this.mockCar)
                .user(this.mockUser)
                .status(RentalStatus.PENDING)
                .build();

        when(this.rentalRepository.findByCarIdAndStatus(eq(this.mockCar.getId()), any()))
                .thenReturn(Optional.of(rental));

        this.rentalService.cancelRentalByCarId(this.mockCar.getId());

        verify(this.rentalRepository).save(rental);
        verify(this.carOverviewService).setIsAvailable(this.mockCar.getId(), true);

        assertEquals(RentalStatus.CANCELED, rental.getStatus());
    }

    @Test
    public void shouldThrowExceptionIfNoPendingOrCanceledRentalFoundWithWebhookEvent() {
        when(this.rentalRepository.findByCarIdAndStatus(eq(this.mockCar.getId()), any()))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                this.rentalService.cancelRentalByCarId(this.mockCar.getId())
        );

        verify(this.rentalRepository, never()).save(any());
        verify(this.carOverviewService, never()).setIsAvailable(this.mockCar.getId(), true);

        assertEquals("Locação não encontrada ou já confirmada.", exception.getMessage());
    }
}
