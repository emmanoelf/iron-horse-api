package com.ironhorse.service;

import com.ironhorse.dto.FileStorageDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void uploadFile(MultipartFile file);
    void deleteUserProfileFile();
    FileStorageDto getUserProfile();
}
