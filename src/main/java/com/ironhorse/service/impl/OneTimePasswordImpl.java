package com.ironhorse.service.impl;

import com.ironhorse.exception.ForbiddenAccessException;
import com.ironhorse.model.Rental;
import com.ironhorse.repository.RentalRepository;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.OneTimePasswordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;

@Service
public class OneTimePasswordImpl implements OneTimePasswordService {
    private final CacheManager cacheManager;
    private final RentalRepository rentalRepository;
    private final AuthenticatedService authenticatedService;

    public OneTimePasswordImpl(CacheManager cacheManager, RentalRepository rentalRepository, AuthenticatedService authenticatedService) {
        this.cacheManager = cacheManager;
        this.rentalRepository = rentalRepository;
        this.authenticatedService = authenticatedService;
    }

    @Override
    public String generateOneTimePassword(Long rentalId) {
        Optional<Rental> rental = this.rentalRepository.findById(rentalId);
        this.validateRentalForGenerateOneTimePassword(rental);

        SecureRandom random = new SecureRandom();
        String oneTimePassword = String.format("%06d", random.nextInt(1000000));

        Cache cache = this.cacheManager.getCache("oneTimePassword");
        if (cache != null) {
            cache.put(rentalId, oneTimePassword);
            cache.put(rental.get().getUser().getId(), oneTimePassword);
            cache.put(rental.get().getCar().getId(), oneTimePassword);
            cache.put(rental.get().getCar().getUser().getId(), oneTimePassword);
        }

        return oneTimePassword;
    }

    @Override
    public boolean validateOneTimePassword(Long rentalId, String oneTimePassword) {
        Cache cache = cacheManager.getCache("oneTimePassword");
        if (cache != null) {
            String cachedOtp = cache.get(rentalId, String.class);
            return cachedOtp != null && cachedOtp.equals(oneTimePassword);
        }
        return false;
    }

    private void validateRentalForGenerateOneTimePassword(Optional<Rental> rental) {
        if(rental.isEmpty()){
            throw new EntityNotFoundException("Locação não encontrada");
        }

        Long userId = this.authenticatedService.getCurrentUserId();
        if(!Objects.equals(userId, rental.get().getCar().getId())){
            throw new ForbiddenAccessException("Você não tem privilégios para executar esta ação");
        }
    }
}
