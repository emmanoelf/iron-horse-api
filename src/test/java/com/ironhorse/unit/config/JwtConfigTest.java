package com.ironhorse.unit.config;

import com.ironhorse.config.jwt.JwtConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class JwtConfigTest {

    @Test
    @DisplayName("Should be able to create beans successfully")
    public void shouldBeAbleToCreateBeansSuccessfully() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        JwtConfig jwtConfig = new JwtConfig();
        Field publicKey = JwtConfig.class.getDeclaredField("publicKey");
        publicKey.setAccessible(true);
        publicKey.set(jwtConfig, (RSAPublicKey) keyPair.getPublic());

        Field privateKey = JwtConfig.class.getDeclaredField("privateKey");
        privateKey.setAccessible(true);
        privateKey.set(jwtConfig, (RSAPrivateKey) keyPair.getPrivate());

        JwtEncoder encoder = jwtConfig.jwtEncoder();
        JwtDecoder decoder = jwtConfig.jwtDecoder();

        assertNotNull(encoder);
        assertNotNull(decoder);
    }
}
