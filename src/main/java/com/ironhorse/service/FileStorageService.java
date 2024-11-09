package com.ironhorse.service;

import com.ironhorse.dto.FileStorageDto;
import com.ironhorse.model.Car;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    void uploadFile(MultipartFile file);
    void uploadCarImagesFiles(List<MultipartFile> files, Long carId);
    void deleteUserProfileFile();
    void deleteCarImageFile(Long id);

    void deleteOnlyFromStorage(Car car);

    List<FileStorageDto> getCarImages(Long id);
    FileStorageDto getUserProfile();
}
