package com.ironhorse.service.impl;

import com.ironhorse.dto.*;
import com.ironhorse.exception.ForbiddenAccessException;
import com.ironhorse.mapper.RentalMapper;
import com.ironhorse.model.Car;
import com.ironhorse.model.Rental;
import com.ironhorse.model.RentalStatus;
import com.ironhorse.repository.CarRepository;
import com.ironhorse.repository.RentalRepository;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.repository.projection.RentalDetailsProjection;
import com.ironhorse.service.*;
import com.stripe.exception.StripeException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final AuthenticatedService authenticatedService;
    private final CarOverviewService carOverviewService;
    private final PaymentService paymentService;
    private final OneTimePasswordService oneTimePasswordService;

    @Override
    @Transactional
    public RentalResponseDto save(RentalDto rentalDto, Long carId) {
        Long userId = this.authenticatedService.getCurrentUserId();

        this.validateDates(rentalDto.startDate(), rentalDto.expectedEndDate());

        Optional<Car> car = this.carRepository.isCarAvailableWithinDates(carId, rentalDto.startDate(), rentalDto.expectedEndDate());
        if(car.isEmpty()) {
            throw new IllegalArgumentException("Este carro não está disponível para locação na data escolhida.");
        }

        Rental rental = RentalMapper.toModel(rentalDto);
        rental.setCar(car.get());
        rental.setUser(userRepository.findById(userId).orElseThrow());

        this.carOverviewService.setIsAvailable(carId, false);
        try{
            long rentalDays = this.calculateDays(rentalDto.startDate(), rentalDto.expectedEndDate());
            PaymentDto paymentDto = this.mountPaymentDto(car.get(), rentalDays);
            PaymentResponseDto url = this.paymentService.createPaymentLink(paymentDto);
            this.rentalRepository.save(rental);

            return RentalMapper.toDtoWithPaymentUrl(rental, url.url());
        }catch (StripeException e){
            this.carOverviewService.setIsAvailable(carId, true);
            throw new IllegalStateException("Erro ao processar o pagamento: " + e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public void confirmRental(Long carId) {
        Optional<Rental> rentalOptional = this.findPendingOrCanceledRental(carId);
        if (rentalOptional.isEmpty()) {
            throw new IllegalStateException("Locação não encontrada ou já confirmada.");
        }

        Rental rental = rentalOptional.get();
        rental.setStatus(RentalStatus.ACTIVE);
        this.rentalRepository.save(rental);

        this.carOverviewService.setIsAvailable(carId, false);
    }

    @Transactional
    @Override
    public void cancelRentalByCarId(Long carId) {
        Optional<Rental> rentalOptional = this.findPendingOrCanceledRental(carId);
        if(rentalOptional.isEmpty()) {
            throw new IllegalArgumentException("Locação não encontrada ou já confirmada.");
        }

        Rental rental = rentalOptional.get();
        rental.setStatus(RentalStatus.CANCELED);
        this.rentalRepository.save(rental);
        this.carOverviewService.setIsAvailable(carId, true);
    }

    @Transactional
    @Override
    public void expiredRental(Long carId) {
        Optional<Rental> rentalOptional = this.findPendingOrCanceledRental(carId);
        if(rentalOptional.isEmpty()) {
            throw new IllegalArgumentException("Locação não encontrada ou já confirmada.");
        }

        Rental rental = rentalOptional.get();
        rental.setStatus(RentalStatus.EXPIRED);
        this.rentalRepository.save(rental);
        this.carOverviewService.setIsAvailable(carId, true);
    }

    @Override
    public List<RentalResponseDto> getAllRentalsByLoggedUser() {
        Long userId = this.authenticatedService.getCurrentUserId();
        List<Rental> rentals = this.rentalRepository.findByUserId(userId);

        return rentals.stream().map(RentalMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void cancelRental(Long rentalId) {
        Long userId = this.authenticatedService.getCurrentUserId();
        Rental rental = this.rentalRepository.findById(rentalId).orElseThrow();

        if(!Objects.equals(userId, rental.getUser().getId()) || !Objects.equals(userId, rental.getCar().getUser().getId())) {
            throw new ForbiddenAccessException("Você não tem privilégios para cancelar esta locação");
        }

        this.carOverviewService.setIsAvailable(rental.getCar().getId(), true);
        rental.setStatus(RentalStatus.CANCELED);
        this.rentalRepository.save(rental);
    }

    @Override
    public RentalResponseDetailsDto getRentalDetails(Long rentalId) {
        Long userId = this.authenticatedService.getCurrentUserId();
        Optional<RentalDetailsProjection> rentalDetails = this.rentalRepository.findRentalWithDetails(rentalId, userId);

        if(rentalDetails.isEmpty()){
            throw new EntityNotFoundException("Locação não encontrada");
        }

        BigDecimal totalPrice = rentalDetails.get().getPrice()
                .multiply(BigDecimal.valueOf(rentalDetails.get().getDaysRented()));
        rentalDetails.get().setTotalPrice(totalPrice);

        return RentalMapper.toDto(rentalDetails.get());
    }

    @Transactional
    @Override
    public RentalResponseDto finishRental(Long rentalId, String oneTimePassword) {
        Rental rental  = this.rentalRepository.findById(rentalId).orElseThrow(
                () -> new EntityNotFoundException("Locação não encontrada"));

        if(!this.oneTimePasswordService.validateOneTimePassword(rentalId, oneTimePassword)){
            throw new IllegalArgumentException("Código inválido");
        }

        Long carId = rental.getCar().getId();
        this.carOverviewService.setIsAvailable(carId, true);
        this.carOverviewService.increaseNumberOfTrips(carId);

        LocalDateTime realEndDate = LocalDateTime.now();

        boolean isOverdue = this.validateDeliveryDate(rental.getExpectedEndDate(), realEndDate);
        if(isOverdue){
            rental.setStatus(RentalStatus.FINISHED_LATE);
        }
        rental.setStatus(RentalStatus.FINISHED);

        rental.setRealEndDate(realEndDate);
        this.rentalRepository.save(rental);
        return RentalMapper.toDto(rental);
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if(endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("A data de devolução não pode ser maior que a data de início");
        }
    }

    private boolean validateDeliveryDate(LocalDateTime expectedEndDate, LocalDateTime realEndDate){
        return !realEndDate.isBefore(expectedEndDate) && !realEndDate.isEqual(expectedEndDate);
    }

    private long calculateDays(LocalDateTime startDate, LocalDateTime expectedEndDate){
        return ChronoUnit.DAYS.between(startDate, expectedEndDate);
    }

    private PaymentDto mountPaymentDto(Car car, long rentalDays){
        Long id = car.getId();
        String name = car.getModel() + " " + car.getBrand() + " " + car.getManufactureYear();
        Long totalPrice = car.getCarOverview().getPrice().longValue() * rentalDays;
        String description = car.getCarOverview().getDescription();
        String image = car.getCarInfo().getCarImages().get(0).getPath();

        return new PaymentDto(id, name, 1L, totalPrice, description, image);
    }

    private Optional<Rental> findPendingOrCanceledRental(Long carId) {
        List<RentalStatus> statuses = List.of(RentalStatus.PENDING, RentalStatus.CANCELED);
        return rentalRepository.findByCarIdAndStatus(carId, statuses);
    }

}
