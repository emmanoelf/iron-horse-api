package com.ironhorse.integration.internal.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration")
@Tag("internal")
@Tag("slow")
@ActiveProfiles("test")
@SpringBootTest
public class CacheConfigIntegrationTest {
    @Autowired
    private CacheManager cacheManager;

    @Test
    @DisplayName("Should load caffeine cache manager")
    public void shouldLoadCaffeineCacheManager(){
        Cache cache = this.cacheManager.getCache("oneTimePassword");
        String generatedOtp = "123456";
        Long rentalId = 1L;

        cache.put(generatedOtp, rentalId);
        Long value = cache.get("123456", Long.class);

        assertNotNull(cache);
        assertEquals(rentalId, value);
    }
}
