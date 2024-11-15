package com.ironhorse.service;

public interface OneTimePasswordService {
    String generateOneTimePassword(Long rentalId);
    boolean validateOneTimePassword(Long rentalId, String oneTimePassword);
}
