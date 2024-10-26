package com.ironhorse.controller.impl;

import com.ironhorse.controller.FileStorageController;
import com.ironhorse.dto.FileStorageDto;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/users/upload")
public class FileStorageControllerImpl implements FileStorageController {
    private final FileStorageService fileStorageService;
    private final AuthenticatedService authenticatedService;

    public FileStorageControllerImpl(FileStorageService fileStorageService, AuthenticatedService authenticatedService) {
        this.fileStorageService = fileStorageService;
        this.authenticatedService = authenticatedService;
    }

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> uploadFile(MultipartFile file) {
        Long userId = this.authenticatedService.getCurrentUserId();
        this.fileStorageService.uploadFile(file, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUserProfileFile() {
        Long userId = this.authenticatedService.getCurrentUserId();
        this.fileStorageService.deleteUserProfileFile(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FileStorageDto> getUserProfile() {
        Long userId = this.authenticatedService.getCurrentUserId();
        FileStorageDto fileStorageDto = this.fileStorageService.getUserProfile(userId);
        return ResponseEntity.status(HttpStatus.OK).body(fileStorageDto);
    }
}
