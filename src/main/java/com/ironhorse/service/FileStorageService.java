package com.ironhorse.service;

import com.ironhorse.dto.FileStorageDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void uploadFile(MultipartFile file, Long userId);
    void deleteUserProfileFile(Long userId);
    FileStorageDto getUserProfile(Long userId);
}
