package com.ironhorse.unit.secutiry;

import com.ironhorse.config.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class SecurityConfigUnitTest {

    private SecurityConfig securityConfig;
    private CorsConfiguration corsConfiguration;

    @BeforeEach
    public void setup(){
        this.securityConfig = new SecurityConfig();

        this.corsConfiguration = new CorsConfiguration();
        this.corsConfiguration.setAllowedOrigins(List.of(
                "http://127.0.0.1:5500",
                "http://localhost:3000",
                "http://localhost:5173"
        ));
        this.corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        this.corsConfiguration.setAllowedHeaders(List.of("*"));
        this.corsConfiguration.setAllowCredentials(true);
    }

    @Test
    @DisplayName("It should contains all expected allowed origins")
    public void itShouldContainsAllExpectedAllowedOrigins(){
        List<String> allowedOrigins = List.of(
                "http://127.0.0.1:5500",
                "http://localhost:3000",
                "http://localhost:5173"
        );

        assertNotNull(this.corsConfiguration.getAllowedOrigins());
        assertTrue(this.corsConfiguration.getAllowedOrigins().containsAll(allowedOrigins));
    }

    @Test
    @DisplayName("It should contains all expected allowed methods")
    public void itShouldContainsAllExpectedAllowedMethods(){
        List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE");

        assertNotNull(this.corsConfiguration.getAllowedMethods());
        assertTrue(this.corsConfiguration.getAllowedMethods().containsAll(allowedMethods));
    }

    @Test
    @DisplayName("It should be true allow credentials")
    public void itShouldBeTrueAllowCredentials(){
        assertEquals(Boolean.TRUE, this.corsConfiguration.getAllowCredentials());
    }

    @Test
    @DisplayName("It should be allowed headers")
    public void itShouldBeAllowedHeaders(){
        assertNotNull(this.corsConfiguration.getAllowedHeaders());
        assertTrue(this.corsConfiguration.getAllowedHeaders().contains("*"));
    }

    @Test
    @DisplayName("It should be able to encode a password successfully")
    public void itShouldBeAbleToEncodeAPasswordSuccessfully(){
        PasswordEncoder passwordEncoder = this.securityConfig.passwordEncoder();
        String rawPassword = passwordEncoder.encode("123456");

        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder.matches("123456", rawPassword));
    }
}
