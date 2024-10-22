package com.ironhorse.security;

import com.ironhorse.model.UserRole;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    private final JwtDecoder jwtDecoder;
    private final JwtEncoder jwtEncoder;

    public JwtTokenProvider(JwtDecoder jwtDecoder, JwtEncoder jwtEncoder) {
        this.jwtDecoder = jwtDecoder;
        this.jwtEncoder = jwtEncoder;
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("sub")).toString();
    }

    public <T> T extractClaim(String token, Function<Map<String, Object>, T> claimsResolver) {
        final Map<String, Object> claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Map<String, Object> extractAllClaims(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaims();
    }

    public String generateToken(String email, Long userId, UserRole role, Map<String, Object> extraClaims) {
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder();
        claimsBuilder.issuer("iron-horse");
        claimsBuilder.subject(email);
        claimsBuilder.claim("userId", userId);
        claimsBuilder.claim("role", role.getRole());
        claimsBuilder.issuedAt(Instant.now());
        claimsBuilder.expiresAt(Instant.now().plus(1, ChronoUnit.HOURS));
        extraClaims.forEach(claimsBuilder::claim);

        JwtClaimsSet claims = claimsBuilder.build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public boolean isTokenValid(String token, String email) {
        final String tokenEmail = extractEmail(token);
        return (tokenEmail.equals(email) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(Instant.now());
    }

    public Instant extractExpiration(String token) {
        return extractClaim(token, claims -> claims.get("exp") instanceof Instant
                ? (Instant) claims.get("exp")
                : Instant.ofEpochSecond((Long) claims.get("exp")));
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> (Long) claims.get("userId"));
    }

    public UserRole extractRole(String token) {
        String roleName = extractClaim(token, claims -> claims.get("role").toString());
        return UserRole.valueOf(roleName);
    }
}