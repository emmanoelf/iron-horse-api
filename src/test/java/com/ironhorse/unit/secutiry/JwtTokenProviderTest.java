package com.ironhorse.unit.secutiry;

import com.ironhorse.model.UserRole;
import com.ironhorse.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {
    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private JwtDecoder jwtDecoder;

    private final String token = "token";

    @Test
    @DisplayName("Should be able to extract email from token")
    public void shouldBeAbleToExtractEmailFromToken(){
        Map<String, Object> claims = Map.of("sub", "user@test.com");
        Jwt mockJwt = this.mockJwt(claims);

        when(this.jwtDecoder.decode(token)).thenReturn(mockJwt);

        String email = this.jwtTokenProvider.extractEmail(token);
        assertEquals("user@test.com", email);
    }

    @Test
    @DisplayName("Should be able to extract userId from token")
    public void shouldBeAbleToExtractUserIdFromToken(){
        Map<String, Object> claims = Map.of("userId", 1L);
        Jwt mockJwt = this.mockJwt(claims);

        when(this.jwtDecoder.decode(token)).thenReturn(mockJwt);
        Long userId = this.jwtTokenProvider.extractUserId(token);

        assertEquals(1L, userId);
    }

    @Test
    @DisplayName("Should be able to extract role from token")
    public void shouldBeAbleToExtractRoleFromToken(){
        Map<String, Object> claims = Map.of("role", "ADMIN");
        Jwt mockJwt = this.mockJwt(claims);

        when(this.jwtDecoder.decode(token)).thenReturn(mockJwt);
        UserRole role = this.jwtTokenProvider.extractRole(token);

        assertEquals(UserRole.ADMIN, role);
    }

    @Test
    @DisplayName("Should token is valid")
    public void shouldTokenIsValid(){
        String email = "user@test.com";
        Instant expiration = Instant.now().plus(1, ChronoUnit.HOURS);
        Map<String, Object> claims = Map.of(
                "sub", email,
                "exp", expiration
        );

        Jwt mockJwt = new Jwt(
                token,
                Instant.now(),
                expiration,
                Map.of("alg", "RS256", "typ", "JWT"),
                claims
        );

        when(this.jwtDecoder.decode(token)).thenReturn(mockJwt);

        boolean isValid = this.jwtTokenProvider.isTokenValid(token, email);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should token is expired")
    public void shouldTokenIsExpired(){
        Instant expiration = Instant.now().minus(1, ChronoUnit.HOURS);
        Map<String, Object> claims = Map.of(
                "exp", expiration
        );

        Jwt mockJwt = new Jwt(
                token,
                Instant.now().minus(2, ChronoUnit.HOURS),
                expiration,
                Map.of("alg", "RS256", "typ", "JWT"),
                claims
        );

        when(this.jwtDecoder.decode(token)).thenReturn(mockJwt);

        boolean isValid = this.jwtTokenProvider.isTokenExpired(token);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should be able to generate token successfully")
    public void shouldBeAbleToGenerateTokenSuccessfully(){
        String email = "user@test.com";
        Long userId = 1L;
        UserRole role = UserRole.USER;
        Map<String, Object> claims = Map.of("other_claims", "claims");

        JwtEncoder mockEncoder = parameters -> (
            new Jwt(
                    "generatedToken",
                    Instant.now(),
                    Instant.now().plus(8, ChronoUnit.HOURS),
                    Map.of("alg", "RS256", "typ", "JWT"),
                    Map.of(
                            "sub", "user@test.com",
                            "userId", userId,
                            "role", role.getRole(),
                            "iat", Instant.now().getEpochSecond(),
                            "exp", Instant.now().plus(8, ChronoUnit.HOURS).getEpochSecond(),
                            "other_claims", "claims"
                    )
            )
        );

        this.jwtTokenProvider = new JwtTokenProvider(this.jwtDecoder, mockEncoder);
        String token = this.jwtTokenProvider.generateToken(email, userId, role, claims);

        assertEquals("generatedToken", token);
    }

    @Test
    @DisplayName("Should be able to generate refresh token from access")
    public void shouldBeAbleToGenerateRefreshTokenFromAccess(){
        String accessToken = "accessToken";
        String email = "user@test.com";
        Long userId = 1L;
        String role = "USER";

        Map<String, Object> accessClaims = Map.of(
                "sub", email,
                "userId", userId,
                "role", role
        );

        Jwt accessJwt = new Jwt(
                accessToken,
                Instant.now(),
                Instant.now().plus(8, ChronoUnit.HOURS),
                Map.of("alg", "RS256", "typ", "JWT"),
                accessClaims
        );

        when(this.jwtDecoder.decode(accessToken)).thenReturn(accessJwt);

        Jwt refreshJwt = new Jwt(
                "refresh-token-value",
                Instant.now(),
                Instant.now().plus(15, ChronoUnit.DAYS),
                Map.of("alg", "RS256", "typ", "JWT"),
                Map.of(
                        "sub", email,
                        "userId", userId,
                        "role", role,
                        "iat", Instant.now().getEpochSecond(),
                        "exp", Instant.now().plus(15, ChronoUnit.DAYS).getEpochSecond()
                )
        );

        when(this.jwtEncoder.encode(any())).thenReturn(refreshJwt);

        String refreshToken = this.jwtTokenProvider.generateRefreshTokenFromAccess(accessToken);

        assertEquals("refresh-token-value", refreshToken);

        ArgumentCaptor<JwtEncoderParameters> captor = ArgumentCaptor.forClass(JwtEncoderParameters.class);
        verify(jwtEncoder).encode(captor.capture());

        JwtClaimsSet claimsSet = captor.getValue().getClaims();

        assertEquals(email, claimsSet.getSubject());
        assertEquals(userId, claimsSet.getClaim("userId"));
        assertEquals(role, claimsSet.getClaim("role"));
        assertNotNull(claimsSet.getIssuedAt());
        assertNotNull(claimsSet.getExpiresAt());
    }

    private Jwt mockJwt(Map<String, Object> claims){
        return new Jwt(token,
                Instant.now(),
                Instant.now().plusSeconds(3600),
                Map.of("alg", "RS256", "typ", "JWT"),
                claims);
    }
}
