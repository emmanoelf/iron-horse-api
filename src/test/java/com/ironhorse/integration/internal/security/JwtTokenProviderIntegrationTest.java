package com.ironhorse.integration.internal.security;

import com.ironhorse.model.UserRole;
import com.ironhorse.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Map;

@SpringBootTest
@Tag("integration")
@Tag("internal")
@Tag("slow")
@ActiveProfiles("test")
public class JwtTokenProviderIntegrationTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String token;
    private final String email = "user@test.com";
    private final Long userId = 1L;
    private final UserRole userRole = UserRole.USER;

    @BeforeEach
    public void setup(){
        Map<String, Object> extraClaims = Map.of("extraClaims", "claim");
        token = this.jwtTokenProvider.generateToken(email, userId, userRole, extraClaims);
    }

    @Test
    @DisplayName("Should be able to generate a non-null token")
    void shouldBeAbleToGenerateANonNullToken() {
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    @DisplayName("Should be able to extract email from token")
    public void shouldBeAbleToExtractEmailFromToken(){
        String extractedEmail = this.jwtTokenProvider.extractEmail(token);
        assertEquals(email, extractedEmail);
    }

    @Test
    @DisplayName("Should be able to extract userId from token")
    public void shouldBeABleToExtractUserIdFromToken(){
        Long extractedUserId = this.jwtTokenProvider.extractUserId(token);
        assertEquals(userId, extractedUserId);
    }

    @Test
    @DisplayName("Should be able to extract role from token")
    public void shouldBeAbleToExtractRoleFromToken(){
        UserRole extractedRole = this.jwtTokenProvider.extractRole(token);
        assertEquals(userRole, extractedRole);
    }

    @Test
    @DisplayName("Should be able to extract expiration from token")
    public void shouldBeAbleToExtractExpirationFromToken(){
        Instant extractedExpiration = this.jwtTokenProvider.extractExpiration(token);
        assertNotNull(extractedExpiration);
        assertTrue(extractedExpiration.isAfter(Instant.now()));
    }

    @Test
    @DisplayName("Should be able to validate token")
    public void shouldBeAbleToValidateToken(){
        boolean isValid = this.jwtTokenProvider.isTokenValid(token, email);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should be able generate refresh token from access token")
    public void shouldBeAbleToGenerateRefreshTokenFromAccessToken(){
        String refreshToken = this.jwtTokenProvider.generateRefreshTokenFromAccess(token);

        assertNotNull(refreshToken);
        assertFalse(refreshToken.isBlank());
        assertNotEquals(token, refreshToken);

        String extractedEmail = this.jwtTokenProvider.extractEmail(refreshToken);
        Long extractedUserId = this.jwtTokenProvider.extractUserId(refreshToken);
        UserRole extractedUserRole = this.jwtTokenProvider.extractRole(refreshToken);
        Instant extractedExpiration = this.jwtTokenProvider.extractExpiration(refreshToken);

        assertEquals(email, extractedEmail);
        assertEquals(userId, extractedUserId);
        assertEquals(userRole, extractedUserRole);
        assertTrue(extractedExpiration.isAfter(Instant.now()));

        assertTrue(this.jwtTokenProvider.isTokenValid(refreshToken, email));
    }
}
