package com.ironhorse.service;

public interface RecoveryPasswordService {
    void passwordRecovery(String email);
    void resetPassword(String token, String password);
}
