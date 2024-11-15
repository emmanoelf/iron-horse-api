package com.ironhorse.service.impl;

import com.ironhorse.dto.EmailDto;
import com.ironhorse.model.PasswordRecovery;
import com.ironhorse.model.User;
import com.ironhorse.repository.PasswordRecoveryRepository;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.EmailService;
import com.ironhorse.service.RecoveryPasswordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecoveryPasswordServiceImpl implements RecoveryPasswordService {
    private final UserRepository userRepository;
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public RecoveryPasswordServiceImpl(UserRepository userRepository, PasswordRecoveryRepository passwordRecoveryRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordRecoveryRepository = passwordRecoveryRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public void passwordRecovery(String email) {
        User user = this.userRepository.findByEmail(email);

        if (user == null) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        String token = this.generateToken();
        LocalDateTime expiresIn = LocalDateTime.now().plusMinutes(60);

        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.setUser(user);
        passwordRecovery.setToken(token);
        passwordRecovery.setExpiresIn(expiresIn);
        this.passwordRecoveryRepository.save(passwordRecovery);
        this.sendPasswordRecovery(email, token);
    }

    @Override
    public void resetPassword(String token, String password) {
        Optional<PasswordRecovery> recovery = this.passwordRecoveryRepository.findByToken(token);

        if (!recovery.isPresent()) {
            throw new EntityNotFoundException("Token não encontrado");
        }

        if (recovery.get().getExpiresIn().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("O token está expirado");
        }

        User user = recovery.get().getUser();

        user.setPassword(passwordEncoder.encode(password));
        this.passwordRecoveryRepository.delete(recovery.get());
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private void sendPasswordRecovery(String email, String token) {
        String recoveryLink = "http://localhost:8080/reset-password?token=" + token;
        HashMap<String, String> templateVariables = new HashMap<>();
        templateVariables.put("recoveryLink", recoveryLink);

        EmailDto emailDto = new EmailDto(email, "Recuperação de senha", "email-template-recovery-password", templateVariables);

        this.emailService.sendEmail(emailDto);
    }
}
