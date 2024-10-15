package com.ironhorse.config.storage;

import com.ironhorse.service.FileStorageService;
import com.ironhorse.service.impl.FileLocalStorageServiceImpl;
import com.ironhorse.service.impl.S3FileStorageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FileStorageConfig {

    @Value("${file.storage.type}")
    private String storageType;

    private final S3FileStorageImpl s3FileStorage;
    private final FileLocalStorageServiceImpl fileLocalStorage;

    @Bean
    public FileStorageService fileStorageService() {
        if("S3".equalsIgnoreCase(storageType)) {
            return s3FileStorage;
        }

        return fileLocalStorage;
    }
}